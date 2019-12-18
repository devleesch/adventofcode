import sys
sys.path.append('2019/lib')
import file
import enum

class Position:
    def __init__(self, x, y):
        self.x = x
        self.y = y

class Tile:
    def __init__(self, position, itemType, value = None):
        self.position = position
        self.itemType = itemType
        self.value = value

class ItemType(enum.Enum):
    WALL = 0
    DOOR = 1
    KEY = 2
    PASSAGE = 3

    @staticmethod
    def of(item):
        if item == '#':
            return ItemType.WALL
        elif ord('A') <= ord(item) and ord('Z') >= ord(item):
            return ItemType.DOOR
        elif ord('a') <= ord(item) and ord('z') >= ord(item):
            return ItemType.KEY
        elif item == '.':
            return ItemType.PASSAGE

class Tunnel:
    def __init__(self, tiles):
        self.tiles = tiles

    @staticmethod
    def parse(datas):
        tiles = [[None for x in range(len(datas[0]))] for y in range(len(datas))]
        for y in range(len(datas)):
            for x in range(len(datas[0])):
                tiles[y][x] = Tile(Position(x, y), ItemType.of(datas[y][x]), datas[y][x])
        return Tunnel(tiles)

def parse(file):
    datas = [[None for x in range(len(file[0]))] for y in range(len(file))]
    y = 0
    for l in file:
        datas[y] = list(l)
        y += 1
    return datas

def main():
    datas = parse(file.read("2019/18/test1.txt"))
    tunnel = Tunnel.parse(datas)
    print(tunnel)

main()