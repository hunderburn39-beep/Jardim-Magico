"""Experimental, independent-round book generator for Jardim Mágico / Stake Engine.

This is *not* an approved or production gambling math model. Base line wins and
cascades only: bonus activation, free spins and Hold & Win are NOT included yet.
"""
from __future__ import annotations

import argparse
import csv
import json
import random
import shutil
import subprocess
from pathlib import Path

SYMBOLS = ('potion', 'green', 'heart', 'teapot', 'wild', 'gem', 'mushroom', 'hat', 'rose', 'scatter', 'stone', 'coin')
WEIGHTS = (10, 13, 11, 10, 4, 11, 10, 9, 10, 4, 12, 7)
LINES = (
    (1, 1, 1, 1, 1), (0, 0, 0, 0, 0), (2, 2, 2, 2, 2),
    (0, 1, 2, 1, 0), (2, 1, 0, 1, 2), (0, 0, 1, 2, 2),
    (2, 2, 1, 0, 0), (1, 0, 0, 1, 2), (1, 2, 2, 1, 0),
    (0, 1, 1, 1, 2),
)
SYMBOL_COEFFICIENTS = (3, 4, 8, 5, 10, 7, 4, 4, 7, 0, 0, 0)
LENGTH_COEFFICIENTS = {3: 7, 4: 25, 5: 80}  # Hundredths of wager.
WILD, SCATTER, STONE, COIN = 4, 9, 10, 11


def sample_board(rng: random.Random) -> list[list[int]]:
    """[column][row], matching the demo's 5x3 representation."""
    return [rng.choices(range(len(SYMBOLS)), weights=WEIGHTS, k=3) for _ in range(5)]


def evaluate(board: list[list[int]]) -> tuple[list[dict], list[tuple[int, int]]]:
    """Line payout in integer 1/100 wager units; no floating point money."""
    lines: list[dict] = []
    removed: set[tuple[int, int]] = set()
    for index, path in enumerate(LINES, start=1):
        chain = [board[c][r] for c, r in enumerate(path)]
        first = next((s for s in chain if s != WILD), WILD)
        pair_wild = chain[0] == WILD and chain[1] == WILD
        amount = 55 if pair_wild else 0
        matched = 2 if pair_wild else 0
        if first not in (SCATTER, STONE, COIN):
            n = 0
            for symbol in chain:
                if symbol != first and symbol != WILD:
                    break
                n += 1
            if n >= 3:
                amount += SYMBOL_COEFFICIENTS[first] * LENGTH_COEFFICIENTS[n]
                matched = n
        if amount:
            cells = [[c, path[c]] for c in range(matched)]
            removed.update((c, r) for c, r in cells)
            lines.append({'line': index, 'symbol': SYMBOLS[first], 'count': matched,
                          'amount': amount, 'cells': cells})
    return lines, sorted(removed)


def cascade(board: list[list[int]], removed: list[tuple[int, int]], rng: random.Random) -> list[list[int]]:
    target = set(removed)
    out = []
    for c in range(5):
        survivors = [board[c][r] for r in range(3) if (c, r) not in target]
        fills = rng.choices(range(len(SYMBOLS)), weights=WEIGHTS, k=3-len(survivors))
        out.append(fills + survivors)
    return out


def round_book(round_id: int, seed: int) -> dict:
    # No previous outcome, session, stake level or reserve influences this draw.
    rng = random.Random((seed << 40) ^ round_id)
    board = sample_board(rng)
    events: list[dict] = []
    payout = 0
    wild_total = 0
    for stage in range(1000):
        wins, removed = evaluate(board)
        base_win = sum(x['amount'] for x in wins)
        eligible_wilds = sorted((c, r) for c, r in removed if board[c][r] == WILD)
        wild_awards = [{'cell': [c, r], 'value': rng.randint(1, 5)} for c, r in eligible_wilds]
        wild_total += sum(x['value'] for x in wild_awards)
        multiplier = max(1, wild_total)
        stage_win = base_win * multiplier
        payout += stage_win
        events.append({'type': 'reveal' if stage == 0 else 'cascade', 'stage': stage,
                       'board': [[SYMBOLS[s] for s in col] for col in board],
                       'lines': wins, 'wildAwards': wild_awards,
                       'multiplier': multiplier, 'winCentsPerWager': stage_win})
        if not removed:
            break
        board = cascade(board, removed, rng)
    else:
        raise RuntimeError('Exceeded 1000 cascades: investigate model, do not truncate wins')
    # Engine format: payoutMultiplier 100 = 1x.
    return {'id': round_id, 'events': events, 'payoutMultiplier': payout}


def compress_book(source: Path, target: Path) -> None:
    """zstd CLI or Python zstandard; fail without writing a false .zst."""
    executable = shutil.which('zstd')
    if executable:
        subprocess.run([executable, '-q', '-f', '-o', str(target), str(source)], check=True)
    else:
        try:
            import zstandard as zstd  # type: ignore
        except ImportError as exc:
            raise RuntimeError('Install the zstd command or pip install zstandard') from exc
        with source.open('rb') as read, target.open('wb') as write:
            zstd.ZstdCompressor(level=6).copy_stream(read, write)


def generate(out: Path, rounds: int, seed: int) -> dict:
    if rounds <= 0:
        raise ValueError('rounds must be positive')
    out.mkdir(parents=True, exist_ok=True)
    book_uncompressed = out / 'books_base.jsonl'
    weights_file = out / 'lookUpTable_base_0.csv'
    total_payout = 0
    with book_uncompressed.open('w', encoding='utf-8') as book, weights_file.open('w', newline='', encoding='ascii') as weights:
        writer = csv.writer(weights, lineterminator='\n')
        for i in range(1, rounds + 1):
            result = round_book(i, seed)
            total_payout += result['payoutMultiplier']
            book.write(json.dumps(result, separators=(',', ':')) + '\n')
            writer.writerow([i, 1, result['payoutMultiplier']])
    compressed = out / 'books_base.jsonl.zst'
    compress_book(book_uncompressed, compressed)
    book_uncompressed.unlink()
    (out / 'index.json').write_text(json.dumps({'modes': [{'name': 'base', 'cost': 1.0,
        'events': compressed.name, 'weights': weights_file.name}]}, indent=2) + '\n', encoding='utf-8')
    report = {'rounds': rounds, 'seed': seed, 'rtpSamplePercent': round(total_payout / rounds, 5),
              'note': 'Experimental sample, NOT approved RTP; excludes all bonus triggers.'}
    (out / 'sample-report.json').write_text(json.dumps(report, indent=2) + '\n', encoding='utf-8')
    return report


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--rounds', type=int, default=1000)
    parser.add_argument('--seed', type=int, default=20261111)
    parser.add_argument('--out', type=Path, default=Path('build/base-prototype'))
    args = parser.parse_args()
    print(json.dumps(generate(args.out, args.rounds, args.seed), indent=2))


if __name__ == '__main__':
    main()
