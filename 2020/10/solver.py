import typing
import sys
import math


def run(lines: typing.List[str]):
    values = [int(x) for x in lines]
    values = sorted(values)
    values.append(values[len(values)-1]+3)

    previous = 0
    diffs = {1: 0, 3: 0}
    intervals = {}
    size = 0
    for v in values:
        diff = v - previous
        diffs[diff] += 1

        if diff == 1:
            size += 1
        else:
            if not size in intervals:
                intervals[size] = 0
            intervals[size] += 1
            size = 0

        previous = v
    print(values)
    print(f"{diffs[1]} * {diffs[3]} = {diffs[1] * diffs[3]}")
    print(intervals)

    coeff = {4:7, 3:4, 2:2, 1:1}
    nb = 1
    for (key, value) in intervals.items():
        if key > 0:
            nb *= math.pow(coeff[key], value)
    print(nb)