"""Verify base-game trigger rules; the original demo prefers SCATTER over coins."""
import sys
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "math"))
from complete import round_book
from prototype import COIN, SCATTER, STONE


class TriggerPriorityTests(unittest.TestCase):
    def test_scatter_precedes_coin_when_both_visible(self):
        board = [
            [SCATTER, COIN, STONE],
            [SCATTER, COIN, STONE],
            [SCATTER, COIN, STONE],
            [COIN, STONE, STONE],
            [COIN, COIN, STONE],
        ]
        with patch("complete.new_board", return_value=board):
            book = round_book(1, 1203, "base")
        kinds = [e["type"] for e in book["events"]]
        self.assertIn("freeStart", kinds)
        self.assertNotIn("holdStart", kinds)
        self.assertEqual(kinds.count("finalWin"), 1)

    def test_six_coins_start_hold_when_no_scatter(self):
        board = [
            [STONE, COIN, STONE],
            [STONE, COIN, STONE],
            [STONE, COIN, STONE],
            [COIN, STONE, STONE],
            [COIN, COIN, STONE],
        ]
        with patch("complete.new_board", return_value=board):
            book = round_book(2, 903, "base")
        kinds = [e["type"] for e in book["events"]]
        self.assertIn("holdStart", kinds)
        self.assertNotIn("freeStart", kinds)
        self.assertEqual(kinds.count("finalWin"), 1)


if __name__ == "__main__":
    unittest.main()
