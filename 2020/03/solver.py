import typing


def run(lines: typing.List[str]):
    terms = []
    terms.append(nb_trees(lines, 1, 1))
    terms.append(nb_trees(lines, 3, 1))
    terms.append(nb_trees(lines, 5, 1))
    terms.append(nb_trees(lines, 7, 1))
    terms.append(nb_trees(lines, 1, 2))

    product = 1
    for t in terms:
        product *= t
    print(product)

def nb_trees(lines, step_x, step_y):
    width = len(lines[0])
    height = len(lines)

    x = 0
    y = 0

    nb_trees = 0
    while y < height:
        if lines[y][x] == '#':
            nb_trees += 1

        x += step_x
        x = x % width
        y += step_y

    return nb_trees