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
    line = line.replace('(', '( ')
    line = line.replace(')', ' )')
    op = line.split()
    while(len(op) > 1):
        i = 0
        p = 0
        args = []
        while(True):
            if op[i] == '(':
                p += 1
                args = []
            elif op[i] == ')':
                p -= 1
            else:
                args.append(i)

            if p == 0 and len(args) == 3:
                break

            i += 1

        result = compute(op.pop(args[0]), op.pop(args[1]-1), op.pop(args[2]-2))
        op.insert(0, str(result))
    return int(op[0])

def run(lines: typing.List[str]):
    sum = 0
    for l in lines:
        sum += evaluate(l)
    return sum


class Test(unittest.TestCase):
    def test_evaluate(self):
        self.assertEqual(71, evaluate("1 + 2 * 3 + 4 * 5 + 6"))
    
    def test_evaluate_with_parentheses(self):
        self.assertEqual(71, evaluate("1 + (2 * 3) + (4 * (5 + 6))"))
        #self.assertEqual(26, evaluate("2 * 3 + (4 * 5)"))
        pass

if __name__ == "__main__":
    unittest.main()