import typing


def run(lines: typing.List[str]):
    values = sorted([int(x) for x in lines])
    for t1 in values:
        for t2 in values:
            if t1+t2 >= 2020:
                break
            for t3 in values:
                if t1+t2+t3 == 2020:
                    print(t1*t2*t3)
                    return
                elif t1+t2+t3 > 2020:
                    break
        