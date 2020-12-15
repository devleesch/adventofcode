import typing


def run(lines: typing.List[str]):
    game = {}
    i = 1
    last = None
    for x in lines[0].split(","):
        if last != None:
            game[last] = [i-1, i-1]
        last = int(x)
        i += 1
    nth = int(lines[1])

    while(i <= nth):
        if not last in game:
            game[last] = [i-1, i-1]
            last = 0
        else:
            game[last][0] = game[last][1]
            game[last][1] = i-1
            value = game[last][-1] - game[last][-2]
            last = value
        i += 1
    print(last)
