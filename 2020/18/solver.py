import typing
import unittest
import re

def compute(t1, sign, t2):
    t1 = int(t1)
    t2 = int(t2)

    if sign == '+':
        return t1 + t2
    elif sign == '*':
        return t1 * t2


def evaluate(line: str):
    op = line.split()
    while(len(op) > 1):
        result = compute(op.pop(0), op.pop(0), op.pop(0))
        op.insert(0, result)
    return op[0]

def run(lines: typing.List[str]):
    sum = 0
    for l in lines:
        sum += evaluate(l)
    return sum


class Test(unittest.TestCase):
    def test_evaluate(self):
        self.assertEqual(71, evaluate("1 + 2 * 3 + 4 * 5 + 6"))
        self.assertEqual(71, evaluate("1 + (2 * 3) + (4 * (5 + 6))"))
        #self.assertEqual(26, evaluate("2 * 3 + (4 * 5)"))

if __name__ == "__main__":
    unittest.main()