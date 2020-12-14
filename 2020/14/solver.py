import typing
import re
import unittest


RE_MASK = re.compile("mask = ([01X]+)")
RE_MEM = re.compile("mem\[(\d+)\] = (\d+)")

def run(lines: typing.List[str]):
    mask = ['0' for i in range(36)]
    memory = {}
    for l in lines:
        mask, address, value = decode(l, mask)
        print(mask, address, value)
        if address:
            memory[address] = apply(mask, to_bin(value))
    
    sum = 0
    for _, value in memory.items():
        sum += value
    print(sum)


def apply(mask, binary):
    result = [0 for _ in range(36)]
    for i in range(36):
        if mask[i] == '1':
            result[i] = 1
        elif mask[i] == '0':
            result[i] = 0
        else:
            result[i] = int(binary[i])
    return to_int(result)


def to_bin(value):
    i = 0
    binary = [0 for i in range(36)]
    while(value > 0):
        bit = value % 2
        binary[i] = bit
        i += 1
        value //= 2
    return binary


def to_int(binary):
    v = 0
    i = 0
    for b in binary:
        if b:
            v += pow(2, i)
        i += 1
    return v


def to_str(binary):
    s = ""
    for b in binary:
        s = str(b) + s
    return s


def decode(line: str, mask):
    match = RE_MEM.match(line)
    if match:
        #print(match[1], match[2])
        return mask, int(match[1]), int(match[2])
    else:
        match = RE_MASK.match(line)
        if match:
            i = 0
            for c in match.group(1):
                mask[35 - i] = c
                i += 1
            #print(line, mask)
            return mask, None, None


class Test(unittest.TestCase):
    def test_to_bin(self):
        self.assertEqual("000000000000000000000000000000000100", to_str(to_bin(4)))
        self.assertEqual("000000000000000000000000000000001011", to_str(to_bin(11)))

    def test_to_int(self):
        self.assertEqual(4, to_int([0, 0, 1]))

if __name__ == "__main__":
    unittest.main()