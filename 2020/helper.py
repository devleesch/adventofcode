import typing
import math


MINUTE = 60
HOUR = MINUTE * 60


def readlines(path) -> typing.List[str]:
    """return file line by line in a list"""
    f = open(path, 'r')
    lines = [x.strip() for x in f]
    f.close()
    return lines

def elapsed(seconds: float):
    if seconds >= MINUTE:
        minute = int(seconds / MINUTE)
        print(f'{minute}min ', end='')
        seconds = seconds - minute * MINUTE
        print(f'{seconds:06.3f}s')
    else:
        print(f'{seconds:5.3f}s')