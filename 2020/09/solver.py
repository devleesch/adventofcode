import typing


def run(lines: typing.List[str]):
    values = [int(x) for x in lines]

    preamble_size = 25

    for i in range(preamble_size, len(values)):
        print(values[i])

        error = True
        for j in range(i-preamble_size, i):
            for k in range(i - preamble_size + 1, i):
                if values[j] + values[k] == values[i]:
                    error = False
                    break
            if not error:
                break

        if error:
            print(f"Error: {values[i]}")
            break
    