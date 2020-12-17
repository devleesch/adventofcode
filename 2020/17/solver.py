import typing
import itertools

class Cube:
    def __init__(self, x, y, z, t, active) -> None:
        self.t = t
        self.x = x
        self.y = y
        self.z = z
        self.active = active

    def neighbors(self, cubes):
        n = []
        for dx, dy, dz, dt in itertools.product([-1, 0, 1], repeat=4):
            if (dx, dy, dz, dt) != (0, 0, 0, 0):
                z = self.z + dz
                y = self.y + dy
                x = self.x + dx
                t = self.t + dt
                if t in cubes:
                    if z in cubes[t]:
                        if y in cubes[t][z]:
                            if x in cubes[t][z][y]:
                                n.append(cubes[t][z][y][x])
        return n

    # def __eq__(self, o: object) -> bool:
    #     return self.x == self.o and self.y == o.y and self.z == o.z

    def __str__(self) -> str:
        return f"[x={self.x}, y={self.y}, z={self.z}, active={self.active}]"

    def __repr__(self) -> str:
        return self.__str__()


def extend(values):
    values = sorted(list(values))
    values.insert(0, values[0]-1)
    values.append(values[len(values)-1]+1)
    return values


def count(cubes):
    sum = 0
    for t in cubes:
        for z in cubes[t]:
            for y in cubes[t][z]:
                for x in cubes[t][z][y]:
                    if cubes[t][z][y][x].active:
                        sum += 1
    return sum


def run(lines: typing.List[str]):
    cubes = {}
    cubes[0] = {}
    cubes[0][0] = {}
    for y, line in enumerate(lines):
        cubes[0][0][y] = {}
        for x, c in enumerate(line):
            cubes[0][0][y][x] = Cube(x, y, 0, 0, c == '#')

    print(count(cubes))

    for _ in range(6):
        # extend the world
        et = extend(cubes.keys())
        ez = extend(cubes[0].keys())
        ey = extend(cubes[0][0].keys())
        ex = extend(cubes[0][0][0].keys())
        for t in et:
            if not t in cubes:
                cubes[t] = {}
            for z in ez:
                if not z in cubes[t]:
                    cubes[t][z] = {}
                for y in ey:
                    if not y in cubes[t][z]:
                        cubes[t][z][y] = {}
                    for x in ex:
                        if not x in cubes[t][z][y]:
                            cubes[t][z][y][x] = Cube(x, y, z, t, False)
        
        # change the world
        changes = set()
        for t in cubes.values():
            for z in t.values():
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
            cubes[change.t][change.z][change.y][change.x].active = not cubes[change.t][change.z][change.y][change.x].active

        # count active
        print(count(cubes))
