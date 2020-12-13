import typing
import sys


def run(lines: typing.List[str]):
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