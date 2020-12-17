import typing
import itertools

class Cube:
    def __init__(self, x, y, z, active) -> None:
        self.x = x
        self.y = y
        self.z = z
        self.active = active

    def neighbors(self, cubes):
        for i in itertools.product([-1, 0, 1], repeat=3):
            print(i)

    def __str__(self) -> str:
        return f"x={self.x}, y={self.y}, z={self.z}, active={self.active}"

    def __repr__(self) -> str:
        return self.__str__()


def run(lines: typing.List[str]):
    cubes = []
    for y, line in enumerate(lines):
        cubes[0] = []
        for x, c in enumerate(line):
            cubes.append(Cube(x, y, 0, c == '#'))
    print(cubes)