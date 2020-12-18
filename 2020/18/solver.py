import typing
import unittest
import re

def split(line: str):
    line = line.replace('(', '( ')
    line = line.replace(')', ' )')
    return line.split()

def compute(t1, sign, t2):
    t1 = int(t1)
    t2 = int(t2)

    if sign == '+':
        return t1 + t2
    elif sign == '*':
        return t1 * t2


def evaluate(line: str):
    while(line.find('(') >= 0):
        start = line.find('(') + 1
        end = start
        p = 1
        while(end < len(line)):
            if line[end] == '(':
                p += 1
            elif line[end] == ')':
                p -= 1
            if p == 0:
                break
            end += 1
        sub = line[start:end]
        line = line.replace('('+sub+')', str(evaluate(sub)))

    op = split(line)
    i = 0
    while(len(op) > 1):
        op.insert(0, str(compute(op.pop(0), op.pop(0), op.pop(0))))
    return int(op[0])

def evaluate2(line: str):
    while(line.find('(') >= 0):
        start = line.find('(') + 1
        end = start
        parenthese = 1
        while(end < len(line)):
            if line[end] == '(':
                parenthese += 1
            elif line[end] == ')':
                parenthese -= 1
            if parenthese == 0:
                break
            end += 1
        sub = line[start:end]
        line = line.replace('('+sub+')', str(evaluate2(sub)))
    
    while(line.find('+') >= 0):
        plus = line.find('+')
        start = plus - 2
        while(start >= 0):
            if line[start] == ' ':
                break
            start -= 1
        end = plus + 2
        while(end < len(line)):
            if line[end] == ' ':
                break
            end += 1
        start += 1
        sub = line[start:end]
        t1, sign, t2 = sub.split()
        line = line.replace(sub, str(compute(t1, sign, t2)))

    op = split(line)
    i = 0
    while(len(op) > 1):
        op.insert(0, str(compute(op.pop(0), op.pop(0), op.pop(0))))
    return int(op[0])

def run(lines: typing.List[str]):
    sum = 0
    for l in lines:
        sum += evaluate2(l)
    print(sum)


class Test(unittest.TestCase):
    def test_evaluate(self):
        self.assertEqual(71, evaluate("1 + 2 * 3 + 4 * 5 + 6"))
    
    def test_evaluate_with_parentheses(self):
        self.assertEqual(51, evaluate("1 + (2 * 3) + (4 * (5 + 6))"))
        self.assertEqual(26, evaluate("2 * 3 + (4 * 5)"))
        self.assertEqual(437, evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        self.assertEqual(12240, evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        self.assertEqual(13632, evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))

    def test_evaluate2(self):
        self.assertEqual(231, evaluate2("1 + 2 * 3 + 4 * 5 + 6"))

    def test_evaluate2_with_parentheses(self):
        self.assertEqual(51, evaluate2("1 + (2 * 3) + (4 * (5 + 6))"))
        self.assertEqual(46, evaluate2("2 * 3 + (4 * 5)"))
        self.assertEqual(1445, evaluate2("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        self.assertEqual(669060, evaluate2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        self.assertEqual(23340, evaluate2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))


if __name__ == "__main__":
    unittest.main()