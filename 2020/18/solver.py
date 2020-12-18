import typing
import unittest

def find_first_op(line):
    t1 = None
    op = None
    t2 = None
    
    i = 0

def evaluate(line):
    return 0

def run(lines: typing.List[str]):
    sum = 0
    for l in lines:
        sum += evaluate(l)
    return sum


class Test(unittest.TestCase):
    def test_evaluate(self):
        self.assertEqual(71, evaluate("1 + 2 * 3 + 4 * 5 + 6"))
        self.assertEqual(26, evaluate("2 * 3 + (4 * 5)"))

if __name__ == "__main__":
    unittest.main()