from os import curdir
import typing
import enum
import unittest

class Direction(enum.Enum):
    E = 0
    SE = 1
    SW = 2
    W = 3
    NW = 4
    NE = 5

    def opposit(self):
        next = (self.value + 3) % 6
        for d in Direction:
            if d.value == next:
                return d

    def next(self):
        next = (self.value + 1) % 6
        for d in Direction:
            if d.value == next:
                return d

    @staticmethod
    def of(op: str):
        op = op.upper()
        for d in Direction:
            if d.name == op:
                return d

class Tile:

    ID = 0

    def __init__(self) -> None:
        self.id = Tile.ID
        Tile.ID += 1
        self.neighbors = {}
        self.white = True
    
    def get(self, direction):
        return self.neighbors[direction]

    def extend(self):
        extended = set()
        for d in Direction:
            if not d in self.neighbors:
                n = Tile()
                extended.add(n)
                self.neighbors[d] = n
                n.neighbors[d.opposit()] = self
        for d in Direction:
            c = self.neighbors[d]
            n = self.neighbors[d.next()]
            c.neighbors[d.next().next()] = n
            n.neighbors[d.next().next().opposit()] = c
        return extended

    def flip(self):
        #print(f"flip: {self.id} - white: {not self.white}")
        self.white = not self.white


def count(tile, visited):
    c = 0
    if not tile in visited:
        visited.add(tile)
        if tile.white == False:
            c += 1
        for n in tile.neighbors.values():
            c += count(n, visited)
    return c

def live(tiles):
    to_flip = set()
    for tile in tiles:
        nb_black = len(list(filter(lambda x: not x.white, tile.neighbors.values())))
        if not tile.white and (nb_black == 0 or nb_black > 2):
            to_flip.add(tile)
        elif tile.white and nb_black == 2:
            to_flip.add(tile)
    return to_flip


def run(lines: typing.List[str]):
    tiles = set()
    origin = Tile()
    tiles.add(origin)
    to_extend = origin.extend()
    tiles.update(to_extend)

    blacks = set()
    for line in lines:
        #print(line)
        current = origin
        i = 0
        while i < len(line):
            op = line[i]
            if op in ['n', 's']:
                i += 1
                op += line[i]
            i += 1
            
            d = Direction.of(op)
            current = current.get(d)

            if len(current.neighbors) < 6:
                new = set()
                for tile in to_extend:
                    new.update(tile.extend())
                to_extend = new
                tiles.update(to_extend)
        current.flip()
        if not current.white:
            blacks.add(current)
        else:
            blacks.remove(current)

    print(f"nb_black={len(blacks)}")

    for i in range(1, 101):
        to_flip = live(tiles)
        for tile in to_flip:
            tile.flip()
            if not tile.white:
                blacks.add(tile)
            else:
                blacks.remove(tile)

            if len(tile.neighbors) < 6:
                new = set()
                for tile in to_extend:
                    new.update(tile.extend())
                to_extend = new
                tiles.update(to_extend)
        print(f"Day {i}: {len(blacks)}")

class Test(unittest.TestCase):
    def test_opposit(self):
        self.assertEqual(Direction.E, Direction.W.opposit())
        self.assertEqual(Direction.SE, Direction.NW.opposit())
        self.assertEqual(Direction.SW, Direction.NE.opposit())
        self.assertEqual(Direction.W, Direction.E.opposit())
        self.assertEqual(Direction.NW, Direction.SE.opposit())
        self.assertEqual(Direction.NE, Direction.SW.opposit())

    def test_next(self):
        self.assertEqual(Direction.E, Direction.NE.next())
        self.assertEqual(Direction.SE, Direction.E.next())
        self.assertEqual(Direction.SW, Direction.SE.next())
        self.assertEqual(Direction.W, Direction.SW.next())
        self.assertEqual(Direction.NW, Direction.W.next())
        self.assertEqual(Direction.NE, Direction.NW.next())
        

if __name__ == "__main__":
    unittest.main()
