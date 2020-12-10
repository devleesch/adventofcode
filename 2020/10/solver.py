import typing


def run(lines: typing.List[str]):
    values = [int(x) for x in lines]
    values = sorted(values)
    values.append(values[len(values)-1]+3)

    previous = 0
    diff = {1: 0, 3: 0}
    for v in values:
        diff[v - previous] += 1
        previous = v
    print(f"{diff[1]} * {diff[3]} = {diff[1] * diff[3]}")