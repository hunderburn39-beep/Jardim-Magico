"""Statistical regression: Hold & Win 4x4 should NOT fill every purchase.

This tests mechanical behavior only, NOT RTP accreditation or compliance.
"""
import random
import sys
import unittest
from pathlib import Path
sys.path.insert(0,str(Path(__file__).resolve().parents[1] / "math"))
from complete import hold_feature

class HoldFrequencyTests(unittest.TestCase):
    def test_3000_hold_features_do_not_fill_every_time(self):
        rng=random.Random(750202611)
        trials=3000
        full=0
        total=0
        counts=[]
        for _ in range(trials):
            result=hold_feature(rng,"purchase")
            end=result["events"][-1]
            self.assertEqual(end["type"],"holdEnd")
            self.assertEqual(result["amount"],end["amount"])
            self.assertEqual(result["amount"],100*sum(end["grid"])+(25000 if end["filled"] else 0))
            self.assertLessEqual(result["turns"],35)
            full+=int(end["filled"])
            total+=result["amount"]/100
            counts.append(sum(bool(x) for x in end["grid"]))
        rate=full/trials
        avg=total/trials
        self.assertGreater(rate,0)
        self.assertLess(rate,.06,f"Full grid too frequent: {rate:.1%}")
        self.assertGreater(avg,15)
        self.assertLess(avg,45)
        self.assertLess(sum(counts)/trials,14)
        print(f"Hold 4x4: {trials} rounds, full={rate:.2%}, average payout={avg:.2f}x")

if __name__=="__main__":
    unittest.main()
