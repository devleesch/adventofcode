import typing
import sys


def run(lines: typing.List[str]):
    values = [int(x) for x in lines]

    preamble_size = 25

    # part a
    error_i = -1
    error_value = -1
    for i in range(preamble_size, len(values)):
        #print(values[i])

        error = True
        for j in range(i-preamble_size, i):
            for k in range(i - preamble_size + 1, i):
                if values[j] + values[k] == values[i]:
                    error = False
                    break
            if not error:
                break

        if error:
            error_i = i
            error_value = values[i]
            break

    print(f"error: [{error_i}] -> {error_value}")

        
    # part b
    for j in range(error_i-1, -1, -1):
        k = j
        sum = 0
        while k >= 0 and sum < error_value:
            sum += values[k]
            k -= 1

        if sum == error_value:
            k = k+1
            min = sys.maxsize
            max = -1
            print(k, j+1)
            for k in range(k, j+1):
                if values[k] < min:
                    min = values[k]
                elif values[k] > max:
                    max = values[k]
            print(f"encryption weakness: {min + max}")
            break
            