import typing
import itertools

class Cube:
    def __init__(self, x, y, z, active) -> None:
        self.x = x
        self.y = y
        self.z = z
        self.active = active

    def neighbors(self, cubes):
        n = []
        for dz, dy, dx in itertools.product([-1, 0, 1], repeat=3):
            if (dz, dy, dx) != (0, 0, 0):
                z = self.z + dz
                y = self.y + dy
                x = self.x + dx
                if z in cubes:
                    if y in cubes[z]:
                        if x in cubes[z][y]:
                            n.append(cubes[z][y][x])
                        else:
                            n.append(Cube(x, y, z, False))
                    else:
                        n.append(Cube(x, y, z, False))
                else:
                    n.append(Cube(x, y, z, False))
        return n

    def __str__(self) -> str:
        return f"x={self.x}, y={self.y}, z={self.z}, active={self.active}"

    def __repr__(self) -> str:
        return self.__str__()


def run(lines: typing.List[str]):
    cubes = {}
    cubes[0] = {}
    for y, line in enumerate(lines):
        cubes[0][y] = {}
        for x, c in enumerate(line):
            cubes[0][y][x] = Cube(x, y, 0, c == '#')

    changes = []
    for z in cubes.values():
        for y in z.values():
            for x in y.values():
                actives = list(filter(lambda c: c.active, x.neighbors(cubes)))
                if x.active and (len(actives) >= 2 or len(actives) <= 3):
                    changes.append(x)
                elif not x.active and len(actives) == 3:
                    changes.append(x)
    print(changes)
