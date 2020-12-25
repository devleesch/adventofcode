import typing
import enum

class Side(enum.Enum):
    LEFT = 0
    BOTTOM = 1
    RIGTH = 2
    TOP = 3

    def opposit(self):
        return Side((self.value + 2) % 4)


class Image:
    
        

class Tile:
    def __init__(self, id, pic) -> None:
        self.id = id
        self.pic = pic
        self.sides = {}
        self.hashes = None
        self.compute_hashes()

    def rotate(self):
        rotated = []
        for x in range(len(self.pic[0])):
            line = ""
            for y in range(len(self.pic) - 1, -1, -1):
                line += self.pic[y][x]
            rotated.append(line)
        self.pic = rotated
        self.compute_hashes()

    def get_side(self, side):
        if side in [Side.LEFT, Side.RIGTH]:
            x = 0 if side is Side.LEFT else len(self.pic[0]) - 1
            side = ""
            for y in range(len(self.pic)):
                side += self.pic[y][x]
            return side
        else:
            y = 0 if side is Side.TOP else len(self.pic) - 1
            return self.pic[y]

    def compute_hashes(self):
        self.hashes = {}
        for side in Side:
            self.hashes[side] = Tile.binary(self.get_side(side))

    @staticmethod
    def of(lines):
        _, id = lines[0].split()
        return Tile(id, lines[1:])

    @staticmethod
    def binary(line):
        value = 0
        i = 0
        for c in line:
            f = 1 if c == '#' else 0
            value += f * pow(2, i)
            i += 1
        return value

def run(lines: typing.List[str]):
    tiles = []

    tmp = []
    for line in lines:
        if line:
            tmp.append(line)
        else:
            tiles.append(Tile.of(tmp))
            tmp = []
            
    if len(tmp):
        tiles.append(Tile.of(tmp))
