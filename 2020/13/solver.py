import typing
import sys
import unittest


def run(lines: typing.List[str]):
    #part1(lines)
    part2(lines)

def pgcd(values):
    values = sorted(values)
    max = -1
    for i in range(1, values[0]):
        candidate = True
        for v in values:
            if v % i != 0:
                candidate = False
                break
        if candidate:
            max = i
    return max

def ppcm(values):
    d = pgcd(values)
    product = 1
    for v in values:
        product *= v
    return product // d

def part2(lines: typing.List[str]):
    ix = 0
    ids = {}
    for id in lines[1].split(','):
        if id != 'x':
            ids[ix] = int(id)
        ix += 1
    print(ids)

    t_align = ppcm(list(ids.values()))
    #print(t_align)

    t = 0
    for i in range(1, len(ids)):
        l = list(ids.values())[0:i]
        step = ppcm(l)
        for t in range(t, sys.maxsize, step):
            valid = True
            for j in range(i+1):
                ix, id = list(ids.items())[j]
                if t == 0 or t % id != ix % id:
                    valid = False
                    break

            if valid:
                print(t)
                break

    print(t_align - t)

def part1(lines: typing.List[str]):
    min_timestamp = int(lines[0])
    ids = []
    for id in lines[1].split(','):
        if id != 'x':
            ids.append(int(id))
    print(min_timestamp)
    print(ids)

    min_wait = sys.maxsize
    min_id = None
    for id in ids:
        wait = id - (min_timestamp % id)
        if wait < min_wait:
            min_wait = wait
            min_id = id
    print(f"{min_id} * {min_wait} = {min_id * min_wait}")


class Test(unittest.TestCase):
    def test_pgcd(self):
        self.assertEqual(12, pgcd([36, 60, 72]))

    def test_ppcm(self):
        self.assertEqual(4199, ppcm([17, 13, 19]))
        self.assertEqual(2992, ppcm([17, 11, 16]))

if __name__ == '__main__':
    unittest.main()