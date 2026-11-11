"""Run: python -m unittest discover -s tests -v"""
import csv
import json
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path
import sys
sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'math'))
from prototype import WILD, STONE, cascade, evaluate, generate, round_book


class MathPrototypeTests(unittest.TestCase):
    def test_3_symbols_center(self):
        board = [[STONE, STONE, STONE] for _ in range(5)]
        for c in range(3):
            board[c][1] = 0
        wins, removed = evaluate(board)
        self.assertEqual(wins[0]['line'], 1)
        self.assertEqual(wins[0]['amount'], 21)
        self.assertEqual(len(removed), 3)

    def test_double_wild(self):
        board = [[STONE] * 3 for _ in range(5)]
        board[0][1] = board[1][1] = WILD
        wins, removed = evaluate(board)
        self.assertEqual(wins[0]['amount'], 55)
        self.assertEqual(removed, [(0, 1), (1, 1)])

    def test_cascade_keeps_survivor_order(self):
        import random
        board = [[0, 1, 2] for _ in range(5)]
        new = cascade(board, [(0, 0), (0, 2)], random.Random(1))
        self.assertEqual(new[0][-1], 1)
        self.assertEqual(new[1], [0, 1, 2])

    def test_round_is_reproducible_and_independent(self):
        result = round_book(17, 42)
        self.assertEqual(result, round_book(17, 42))
        self.assertEqual(result['id'], 17)
        self.assertEqual(result['payoutMultiplier'], sum(e['winCentsPerWager'] for e in result['events']))
        self.assertGreaterEqual(result['payoutMultiplier'], 0)

    @unittest.skipUnless(shutil.which('zstd'), 'zstd command missing')
    def test_output_format_and_cross_file_consistency(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp)
            report = generate(output, 80, 123)
            index = json.loads((output / 'index.json').read_text())
            self.assertEqual(index['modes'][0]['cost'], 1.0)
            self.assertEqual(index['modes'][0]['name'], 'base')
            with (output / 'lookUpTable_base_0.csv').open() as fh:
                rows = list(csv.reader(fh))
            self.assertEqual(len(rows), 80)
            text = subprocess.check_output(['zstd', '-q', '-d', '-c', str(output / 'books_base.jsonl.zst')], text=True)
            books = [json.loads(line) for line in text.splitlines()]
            self.assertEqual(len(books), 80)
            for csv_row, book in zip(rows, books):
                self.assertEqual(list(map(int, csv_row)), [book['id'], 1, book['payoutMultiplier']])
                self.assertIsInstance(book['events'], list)
            self.assertAlmostEqual(report['rtpSamplePercent'], sum(b['payoutMultiplier'] for b in books)/80)


if __name__ == '__main__':
    unittest.main()
