"""Experimental CSV weight optimizer for precomputed Stake Engine math libraries.

Weights are *static* selection probabilities, never selected by bankroll, player,
history, stake or any live state. Not a replacement for full math verification.
Do not submit the tuned index without RGS and independent statistical validation.
"""
from __future__ import annotations
import argparse
import csv
import json
import math
from pathlib import Path

MAX_WEIGHT = 1_000_000_000


def read_rows(path: Path) -> list[tuple[int, int]]:
    with path.open(newline="", encoding="ascii") as stream:
        rows = list(csv.reader(stream))
    output = []
    ids = set()
    for row in rows:
        if len(row) != 3:
            raise ValueError(f"Malformed weight row in {path}: {row!r}")
        sim_id, weight, payout = (int(value) for value in row)
        if sim_id < 1 or weight <= 0 or payout < 0 or sim_id in ids:
            raise ValueError(f"Invalid row in {path}: {row!r}")
        output.append((sim_id, payout))
        ids.add(sim_id)
    if not output:
        raise ValueError("No simulation rows")
    return output


def expected_payout(returns: list[float], weights: list[int]) -> float:
    total_weight = sum(weights)
    if total_weight <= 0:
        raise ValueError("Weights must be positive")
    return math.fsum(y * w for y, w in zip(returns, weights)) / total_weight


def tilt_weights(returns: list[float], target: float) -> list[int]:
    """Exponential tilting with positive integer weights of at most 1e9.

    Solve for a uniform library-weighting such that the *existing sample* has
    expected payout close to target. No event books or payouts are modified.
    """
    if not (0 < target < 10):
        raise ValueError("target RTP must be between 0 and 1000%")
    if not returns or min(returns) >= target or max(returns) <= target:
        raise ValueError("Target outside achievable payout range of sample")
    minimum, maximum = min(returns), max(returns)

    def weights_for(lam: float) -> list[int]:
        anchor = minimum if lam >= 0 else maximum
        return [max(1, min(MAX_WEIGHT, round(MAX_WEIGHT * math.exp(
            max(-750.0, -lam * (x - anchor))
        )))) for x in returns]

    lo, hi = -64.0, 64.0
    for _ in range(100):
        middle = (lo + hi) / 2
        mean = expected_payout(returns, weights_for(middle))
        if mean > target:
            lo = middle
        else:
            hi = middle
    weights = weights_for((lo + hi) / 2)
    if abs(expected_payout(returns, weights) - target) > 0.001:
        raise ValueError("Target not resolved precisely with 1e9 integer weights")
    return weights


def tune(source: Path, dest: Path, target_percent: float = 96.0) -> dict:
    meta = json.loads((source / "index.json").read_text(encoding="utf-8"))
    if not isinstance(meta.get("modes"), list):
        raise ValueError("Invalid source index.json")
    dest.mkdir(parents=True, exist_ok=True)
    output_modes = []
    report = {"targetPercent": target_percent, "note": (
        "In-sample reweighting only. No out-of-sample validation, guarantee or certification."),
        "modes": {}}
    for mode in meta["modes"]:
        name, cost = mode["name"], float(mode["cost"])
        if cost <= 0:
            raise ValueError(f"Invalid cost: {name}")
        data = read_rows(source / mode["weights"])
        returns = [payout / (100.0 * cost) for _, payout in data]
        target = target_percent / 100.0
        weights = tilt_weights(returns, target)
        outfile = f"lookUpTable_{name}_tuned_0.csv"
        with (dest / outfile).open("w", newline="", encoding="ascii") as stream:
            writer = csv.writer(stream, lineterminator="\n")
            for (sim_id, payout), weight in zip(data, weights):
                writer.writerow([sim_id, weight, payout])
        output_modes.append({**mode, "weights": outfile})
        report["modes"][name] = {
            "cost": cost,
            "unweightedSampleRtpPercent": round(100 * sum(returns) / len(returns), 6),
            "tunedSampleRtpPercent": round(100 * expected_payout(returns, weights), 6),
            "positiveWeightCount": len(weights),
            "minWeight": min(weights), "maxWeight": max(weights),
            "effectiveSampleSize": round(sum(weights) ** 2 / sum(w * w for w in weights), 2),
        }
    # Deliberately named index.tuned.json; original index.json is not modified.
    (dest / "index.tuned.json").write_text(
        json.dumps({"modes": output_modes}, indent=2) + "\n", encoding="utf-8")
    (dest / "tuning-report.json").write_text(
        json.dumps(report, indent=2) + "\n", encoding="utf-8")
    return report


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source", type=Path, default=Path("build/features-prototype"))
    parser.add_argument("--out", type=Path, default=Path("build/tuned-prototype"))
    parser.add_argument("--target-percent", type=float, default=96.0)
    args = parser.parse_args()
    print(json.dumps(tune(args.source, args.out, args.target_percent), indent=2))
