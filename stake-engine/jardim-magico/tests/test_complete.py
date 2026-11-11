"""Unit, event-accounting and archive tests for the full experimental feature model."""
import csv
import json
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "math"))
from complete import MAX_FREE_SPINS, MODES, free_feature, hold_feature, round_book, stages, generate
import random


class FeatureMathTests(unittest.TestCase):
    def test_base_stage_reconciliation(self):
        value = stages(random.Random(101), "base", 1)
        self.assertEqual(sum(e["rawCentsPerWager"] for e in value["events"]), value["raw"])
        self.assertEqual(sum(e["stageWinCentsPerWager"] for e in value["events"]), value["paid"])
        self.assertGreaterEqual(value["paid"], value["raw"])
        self.assertEqual(value["events"][-1]["lines"] == [], True)

    def test_free_spins_retrigger_and_accounting(self):
        game = free_feature(random.Random(999))
        self.assertEqual(game["events"][0]["type"], "freeStart")
        self.assertEqual(game["events"][-1]["type"], "freeEnd")
        self.assertGreaterEqual(game["spins"], 15)
        self.assertLessEqual(game["spins"], MAX_FREE_SPINS)
        last = game["events"][-1]
        self.assertEqual(last["amount"], last["rawTotal"] * last["finalMultiplier"])
        self.assertEqual(game["amount"], last["amount"])
        self.assertEqual(sum(e["retrigger"] for e in game["events"] if e["type"] == "freeProgress"),
                         game["spins"] - 15)

    def test_hold_payments_and_zero_termination(self):
        for seed in range(60):
            game = hold_feature(random.Random(seed))
            self.assertEqual(game["events"][0]["type"], "holdStart")
            final = game["events"][-1]
            self.assertEqual(game["amount"], final["coinTotal"] + final["completionAward"])
            self.assertEqual(final["coinTotal"], 100 * sum(final["grid"]))
            self.assertEqual(sum(bool(x) for x in final["grid"]) >= 6, True)
            self.assertLessEqual(game["turns"], 100)
            self.assertTrue(final["filled"] or game["events"][-2]["tries"] == 0)

    def test_modes_fully_stateless_and_reproducible(self):
        for mode in MODES:
            a = round_book(1, 2026, mode)
            b = round_book(1, 2026, mode)
            self.assertEqual(a, b)
            self.assertEqual(a["events"][-1]["type"], "finalWin")
            self.assertEqual(a["payoutMultiplier"], a["events"][-1]["amount"])
            self.assertEqual(sum(e["type"] == "finalWin" for e in a["events"]), 1)
            self.assertGreaterEqual(a["payoutMultiplier"], 0)
            self.assertIsInstance(a["payoutMultiplier"], int)

    def test_invalid_round_and_mode_rejected(self):
        with self.assertRaises(ValueError):
            round_book(0, 1)
        with self.assertRaises(ValueError):
            round_book(1, 1, "jackpot")

    @unittest.skipUnless(shutil.which("zstd"), "zstd binary absent")
    def test_three_complete_mode_archives(self):
        with tempfile.TemporaryDirectory() as d:
            target = Path(d)
            report = generate(target, 25, 87)
            metadata = json.loads((target / "index.json").read_text())
            self.assertEqual({m["name"] for m in metadata["modes"]}, set(MODES))
            for mode in metadata["modes"]:
                with (target / mode["weights"]).open() as f:
                    rows = list(csv.reader(f))
                raw = subprocess.check_output(
                    ["zstd", "-q", "-d", "-c", str(target / mode["events"])], text=True
                )
                books = [json.loads(line) for line in raw.splitlines()]
                self.assertEqual(len(rows), len(books))
                self.assertEqual(len(books), 25)
                self.assertEqual(mode["cost"], MODES[mode["name"]])
                for row, book in zip(rows, books):
                    self.assertEqual(list(map(int, row)), [book["id"], 1, book["payoutMultiplier"]])
                    self.assertEqual(book["events"][-1]["amount"], book["payoutMultiplier"])
            self.assertEqual(report["roundsPerMode"], 25)


if __name__ == "__main__":
    unittest.main()
