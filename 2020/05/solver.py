import typing
import unittest

class Interval:
    def __init__(self, min, max) -> None:
        self.min = min
        self.max = max

    def apply(self, value):
        mid = max(round((self.max - self.min) / 2), 1)
        if value == 'F':
            self.max -= mid
        elif value == 'B':
            self.min += mid
        elif value == 'L':
            self.max -= mid
        elif value == 'R':
            self.min += mid


def run(lines: typing.List[str]):
    max = 0
    seats = [0 for i in range(128 * 8)]
    for l in lines:
        v = process(l)
        seats[v] = 1
        if v > max:
            max = v
    print(max)
    for i in range(max):
        if seats[i] == 0:
            print(i)


def process(line):
    row_def = line[0:7]
    col_def = line[7:10]

    row = Interval(0, 127)
    for v in row_def:
        row.apply(v)

    col = Interval(0, 7)
    for v in col_def:
        col.apply(v)

    return row.min * 8 + col.min


class Test(unittest.TestCase):
    def test_a(self):
        self.assertEqual(357, process("FBFBBFFRLR"))
        self.assertEqual(567, process("BFFFBBFRRR"))
        self.assertEqual(119, process("FFFBBBFRRR"))
        self.assertEqual(820, process("BBFFBBFRLL"))

if __name__ == "__main__":
    unittest.main()