import typing


def run(lines: typing.List[str]):
    for l1 in lines:
        t1 = int(l1)
        for l2 in lines:
            t2 = int(l1)
            print(f'{t1}+{t2}={t1+t2}')
            if t1+t2 == 2020:
                print(t1*t2)
        