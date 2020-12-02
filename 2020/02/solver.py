import typing


def run(lines: typing.List[str]):
    nb_valid = 0
    for line in lines:
        rest, password = (x.strip() for x in line.split(":"))
        rest, char = rest.split(" ")
        min, max = (int(x) for x in rest.split("-"))

        print(f"char={char}, min={min}, max={max}, password={password}, line={line}")

        char_count = 0
        for c in password:
            if c == char:
                char_count += 1

        if char_count >= min and char_count <= max:
            nb_valid += 1

    print(nb_valid)
