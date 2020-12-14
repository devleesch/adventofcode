import typing
import re


def run(lines: typing.List[str]):
    mask = [0 for i in range(36)]
    for l in lines:
        mask, address, value = decode(l, mask)


def decode(line: str, mask):
    address = None
    value = None

    match = re.match("mask = ([01X]+)", line)
    if match:
        i = 0
        for c in match.group(1):
            if c != 'X':
                mask[i] = int(c)
            i += 1
        print(line, mask)


    return mask, address, value