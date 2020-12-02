import typing


def run(lines: typing.List[str]):
    nb_valid = 0
    for line in lines:
        rest, password = (x.strip() for x in line.split(":"))
        rest, char = rest.split(" ")
        idx1, idx2 = (int(x) - 1 for x in rest.split("-"))

        if (password[idx1] == char or password[idx2] == char) and password[idx1] != password[idx2]:
            nb_valid += 1
    print(nb_valid)