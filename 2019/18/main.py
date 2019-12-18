import sys
sys.path.append('2019/lib')
import file
import enum

class Position:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __str__(self):
        return "<x: {}, y: {}>".format(self.x, self.y)

class Tile:
    def __init__(self, position, itemType, value = None):
        self.position = position
        self.itemType = itemType
        self.value = value
    
    def __str__(self):
        return "<position: {}; itemType: {}; value: {}>".format(self.position, self.itemType, self.value)

class ItemType(enum.Enum):
    WALL = 0
    DOOR = 1
    KEY = 2
    PASSAGE = 3
    ME = 4

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
        elif item == '@':
            return ItemType.ME

class Heading(enum.Enum):
    UP = 0,
    LEFT = 1
    DOWN = 2
    RIGHT = 3

class Tunnel:
    def __init__(self, tiles, items):
        self.tiles = tiles
        self.items = items

    @staticmethod
    def parse(datas):
        tiles = [[None for x in range(len(datas[0]))] for y in range(len(datas))]
        items = {}
        for y in range(len(datas)):
            for x in range(len(datas[0])):
                position = Position(x, y)
                itemType = ItemType.of(datas[y][x])
                value = datas[y][x]
                tile = Tile(position, itemType, value)

                if not itemType in items.keys():
                    items[itemType] = []
                items.get(itemType).append(tile)

                tiles[y][x] = tile
        return Tunnel(tiles, items)
    
    def findKeys(self):
        me = self.items[ItemType.ME][0]
        

def parse(file):
    datas = [[None for x in range(len(file[0]))] for y in range(len(file))]
    y = 0
    for l in file:
        datas[y] = list(l)
        y += 1
    return datas

def main():
    datas = parse(file.read("2019/18/input.txt"))
    tunnel = Tunnel.parse(datas)
    tunnel.findKeys()

main()