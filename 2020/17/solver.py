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
        for dx, dy, dz in itertools.product([-1, 0, 1], repeat=3):
            if (dx, dy, dz) != (0, 0, 0):
                z = self.z + dz
                y = self.y + dy
                x = self.x + dx
                if z in cubes:
                    if y in cubes[z]:
                        if x in cubes[z][y]:
                            n.append(cubes[z][y][x])
        return n

    # def __eq__(self, o: object) -> bool:
    #     return self.x == self.o and self.y == o.y and self.z == o.z

    def __str__(self) -> str:
        return f"[x={self.x}, y={self.y}, z={self.z}, active={self.active}]"

    def __repr__(self) -> str:
        return self.__str__()


def display(cubes):
    for z, dim in cubes.items():
        print(f"z={z}")
        for y, line in cubes[z].items():
            for x, cube in cubes[z][y].items():
                print("#" if cube.active else ".", end='')
            print()
    print()


def extend(values):
    values = sorted(list(values))
    values.insert(0, values[0]-1)
    values.append(values[len(values)-1]+1)
    return values


def count(cubes):
    sum = 0
    for z in cubes:
        for y in cubes[z]:
            for x in cubes[z][y]:
                if cubes[z][y][x].active:
                    sum += 1
    return sum


def run(lines: typing.List[str]):
    cubes = {}
    cubes[0] = {}
    for y, line in enumerate(lines):
        cubes[0][y] = {}
        for x, c in enumerate(line):
            cubes[0][y][x] = Cube(x, y, 0, c == '#')

    print(count(cubes))

    for _ in range(6):
        # extend the world
        ez = extend(cubes.keys())
        ey = extend(cubes[0].keys())
        ex = extend(cubes[0][0].keys())
        for z in ez:
            if not z in cubes:
                cubes[z] = {}
            for y in ey:
                if not y in cubes[z]:
                    cubes[z][y] = {}
                for x in ex:
                    if not x in cubes[z][y]:
                        cubes[z][y][x] = Cube(x, y, z, False)
        
        # change the world
        changes = set()
        for z in cubes.values():
            for y in z.values():
                for x in y.values():
                    neighbors = x.neighbors(cubes)
                    actives = list(filter(lambda c: c.active, neighbors))
                    if x.active and (len(actives) < 2 or len(actives) > 3):
                        changes.add(x)
                    elif not x.active and len(actives) == 3:
                        changes.add(x)
        
        # apply change
        for change in changes:
            cubes[change.z][change.y][change.x].active = not cubes[change.z][change.y][change.x].active

        # count active
        print(count(cubes))
