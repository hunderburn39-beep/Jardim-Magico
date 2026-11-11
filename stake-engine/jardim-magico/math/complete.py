"""Jardim Mágico — feature-complete *experimental* stateless math books.

Every book contains the entire result of exactly one bet, including any triggered
Free Spins or Hold & Win. No wallet, bank, previous round or user state is read.
Not an RTP-certified, RGS-validated or production-real-money implementation.
"""
from __future__ import annotations

import argparse
import csv
import json
import random
from pathlib import Path

from prototype import SYMBOLS, WEIGHTS, LINES, WILD, SCATTER, COIN,     evaluate, sample_board, compress_book

MAX_FREE_SPINS = 60
MAX_CASCADES = 1000
MODES = {"base": 1.0, "free_buy": 20.0, "hold_buy": 30.0}


def new_board(rng: random.Random, free: bool = False) -> list[list[int]]:
    weights = [2 if free and i == SCATTER else w for i, w in enumerate(WEIGHTS)]
    return [rng.choices(range(len(SYMBOLS)), weights=weights, k=3) for _ in range(5)]


def drop(board: list[list[int]], cells: list[tuple[int, int]],
         rng: random.Random, free: bool) -> list[list[int]]:
    gone = set(cells)
    weights = [2 if free and i == SCATTER else w for i, w in enumerate(WEIGHTS)]
    next_board = []
    for col in range(5):
        kept = [board[col][row] for row in range(3) if (col, row) not in gone]
        fresh = rng.choices(range(len(SYMBOLS)), weights=weights, k=3 - len(kept))
        next_board.append(fresh + kept)
    return next_board


def stages(rng: random.Random, game_type: str, spin: int) -> dict:
    free = game_type == "free"
    board = new_board(rng, free=free)
    raw_total = 0
    paid_total = 0
    wild_total = 0
    max_scatter = 0
    max_coins = 0
    events = []
    for index in range(MAX_CASCADES):
        lines, removed = evaluate(board)
        scatter = sum(s == SCATTER for column in board for s in column)
        coins = sum(s == COIN for column in board for s in column)
        max_scatter = max(scatter, max_scatter)
        max_coins = max(coins, max_coins)
        awards = [{"cell": [col, row], "value": rng.randint(1, 5)}
                  for col, row in removed if board[col][row] == WILD]
        wild_total += sum(a["value"] for a in awards)
        raw = sum(line["amount"] for line in lines)
        paid = raw * max(1, wild_total)
        raw_total += raw
        paid_total += paid
        events.append({
            "type": "reveal" if index == 0 else "cascade",
            "gameType": game_type, "spin": spin, "stage": index,
            "board": [[SYMBOLS[s] for s in col] for col in board],
            "lines": lines, "wildAwards": awards,
            "multiplier": max(1, wild_total),
            "rawCentsPerWager": raw, "stageWinCentsPerWager": paid,
            "scatterCount": scatter, "coinCount": coins,
        })
        if not removed:
            break
        board = drop(board, removed, rng, free=free)
    else:
        raise RuntimeError("Unbounded cascades: do not silently cap payable wins")
    return {"events": events, "raw": raw_total, "paid": paid_total,
            "wild": wild_total, "scatter": max_scatter, "coins": max_coins}


def free_feature(rng: random.Random, trigger: str = "scatter") -> dict:
    events = [{"type": "freeStart", "trigger": trigger, "awarded": 15}]
    left, played, raw, multiplier = 15, 0, 0, 1
    while left > 0:
        left -= 1
        played += 1
        spin = stages(rng, "free", played)
        raw += spin["raw"]
        if spin["wild"]:
            multiplier = min(1000, multiplier + max(0, spin["wild"] - 1))
        events.extend(spin["events"])
        retrigger = 15 if spin["scatter"] >= 3 else 8 if spin["scatter"] >= 2 else 0
        award = min(retrigger, max(0, MAX_FREE_SPINS - played - left))
        left += award
        events.append({"type": "freeProgress", "played": played,
                       "remaining": left, "retrigger": award,
                       "rawAccumulated": raw, "multiplier": multiplier})
        if played >= MAX_FREE_SPINS and left:
            raise AssertionError("Free Spin bound violated")
    prize = raw * multiplier
    events.append({"type": "freeEnd", "spinsPlayed": played, "rawTotal": raw,
                   "finalMultiplier": multiplier, "amount": prize})
    return {"events": events, "amount": prize, "spins": played}


def hold_feature(rng: random.Random, trigger: str = "coins") -> dict:
    """Stateless 4x4 Hold: independent 8% chance per EMPTY cell per try.

    Fixed weights: same at every bet size; no relation to history, bankroll,
    session or player. Any newly landed coins reset attempts to three.
    """
    cells = [0] * 16
    places = rng.sample(range(16), 6)
    for place in places:
        cells[place] = rng.choices((1, 2, 3, 5, 10),
                                  weights=(46, 28, 16, 8, 2))[0]
    events = [{"type": "holdStart", "trigger": trigger, "grid": cells.copy(),
               "tries": 3}]
    tries, turns = 3, 0
    while tries > 0 and any(value == 0 for value in cells):
        turns += 1
        new_coins = []
        for place in range(16):
            if cells[place] == 0 and rng.random() < 0.08:
                value = rng.choices((1, 2, 3, 5, 10, 20),
                                    weights=(42, 30, 16, 8, 3, 1))[0]
                cells[place] = value
                new_coins.append({"cell": place, "value": value})
        tries = 3 if new_coins else tries - 1
        events.append({"type": "holdTurn", "turn": turns,
                       "newCoins": new_coins, "grid": cells.copy(), "tries": tries})
    filled = all(value != 0 for value in cells)
    # The 250x completion award remains an experimental fixed prize;
    # approval requires explicit signoff and RTP auditing.
    completion = 25000 if filled else 0
    amount = 100 * sum(cells) + completion
    events.append({"type": "holdEnd", "grid": cells.copy(), "filled": filled,
                   "coinTotal": 100 * sum(cells),
                   "completionAward": completion, "amount": amount})
    return {"events": events, "amount": amount, "filled": filled, "turns": turns}

def round_book(round_id: int, seed: int, mode: str = "base") -> dict:
    if mode not in MODES:
        raise ValueError(f"Unknown mode: {mode}")
    if round_id <= 0:
        raise ValueError("Round ID must be positive")
    mode_index = list(MODES).index(mode)
    rng = random.Random((seed << 40) ^ (round_id * 0x9E3779B1) ^ (mode_index << 24))
    events = []
    amount = 0
    if mode == "base":
        base = stages(rng, "base", 1)
        events.extend(base["events"])
        amount += base["paid"]
        if base["scatter"] >= 3:
            feature = free_feature(rng)
        elif base["coins"] >= 6:
            feature = hold_feature(rng)
        else:
            feature = None
        if feature is not None:
            events.extend(feature["events"])
            amount += feature["amount"]
    else:
        feature = free_feature(rng, "purchase") if mode == "free_buy" else hold_feature(rng, "purchase")
        events.extend(feature["events"])
        amount = feature["amount"]
    events.append({"type": "finalWin", "amount": amount, "mode": mode})
    return {"id": round_id, "events": events, "payoutMultiplier": amount}


def generate(out: Path, rounds: int, seed: int) -> dict:
    if rounds < 1:
        raise ValueError("rounds must be positive")
    out.mkdir(parents=True, exist_ok=True)
    modes = []
    report = {"roundsPerMode": rounds, "seed": seed, "models": {},
              "caveat": "UNOPTIMIZED empirical sample, not theoretical/certified RTP."}
    for mode, cost in MODES.items():
        events_name = f"books_{mode}.jsonl.zst"
        csv_name = f"lookUpTable_{mode}_0.csv"
        plain = out / f"books_{mode}.jsonl"
        payout_sum = 0
        hit_count = 0
        max_payout = 0
        with plain.open("w", encoding="utf-8") as books,                 (out / csv_name).open("w", newline="", encoding="ascii") as lookup:
            writer = csv.writer(lookup, lineterminator="\n")
            for sim_id in range(1, rounds + 1):
                book = round_book(sim_id, seed, mode)
                payout = book["payoutMultiplier"]
                payout_sum += payout
                hit_count += bool(payout)
                max_payout = max(max_payout, payout)
                books.write(json.dumps(book, separators=(",", ":")) + "\n")
                writer.writerow([sim_id, 1, payout])
        compress_book(plain, out / events_name)
        plain.unlink()
        modes.append({"name": mode, "cost": cost, "events": events_name, "weights": csv_name})
        report["models"][mode] = {
            "costMultiplier": cost,
            "rtpSamplePercent": round(payout_sum / (rounds * cost), 6),
            "hitRateSamplePercent": round(100 * hit_count / rounds, 4),
            "maxSamplePayoutXBaseWager": max_payout / 100,
        }
    (out / "index.json").write_text(json.dumps({"modes": modes}, indent=2) + "\n")
    (out / "sample-report.json").write_text(json.dumps(report, indent=2) + "\n")
    return report


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--rounds", type=int, default=1000)
    parser.add_argument("--seed", type=int, default=20261111)
    parser.add_argument("--out", type=Path, default=Path("build/features-prototype"))
    args = parser.parse_args()
    print(json.dumps(generate(args.out, args.rounds, args.seed), indent=2))
