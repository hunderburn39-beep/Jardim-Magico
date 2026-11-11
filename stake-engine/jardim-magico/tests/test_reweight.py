"""Validate transparent, reproducible weighting of static result libraries."""
import csv
import json
import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "math"))
from complete import generate
from reweight import expected_payout, read_rows, tilt_weights, tune


class WeightingTests(unittest.TestCase):
    def test_positive_weights_hit_synthetic_target(self):
        values = [0.0, .2, .7, 1.4, 4.0, 12.0]
        weights = tilt_weights(values, .96)
        self.assertEqual(len(weights), len(values))
        self.assertTrue(all(isinstance(w, int) and w > 0 for w in weights))
        self.assertLess(abs(expected_payout(values, weights) - .96), .001)

    def test_unachievable_target_rejected(self):
        with self.assertRaises(ValueError):
            tilt_weights([3.0, 4.0], .96)
        with self.assertRaises(ValueError):
            tilt_weights([0.0, 0.0], .96)

    def test_full_modes_tune_without_touching_original(self):
        with tempfile.TemporaryDirectory() as tmp:
            src = Path(tmp) / "source"
            dst = Path(tmp) / "tuned"
            generate(src, 100, 20261111)
            original_index = (src / "index.json").read_bytes()
            report = tune(src, dst, 96.0)
            self.assertEqual((src / "index.json").read_bytes(), original_index)
            self.assertFalse((dst / "index.json").exists())
            modes = json.loads((dst / "index.tuned.json").read_text())["modes"]
            self.assertEqual(len(modes), 3)
            for mode in modes:
                initial = read_rows(src / f"lookUpTable_{mode['name']}_0.csv")
                with (dst / mode["weights"]).open(newline="") as fh:
                    optimized = [tuple(map(int, row)) for row in csv.reader(fh)]
                self.assertEqual([(id, payout) for id, _, payout in optimized], initial)
                self.assertTrue(all(weight >= 1 for _, weight, _ in optimized))
                self.assertLess(abs(report["modes"][mode["name"]]["tunedSampleRtpPercent"] - 96), .1)


if __name__ == "__main__":
    unittest.main()
