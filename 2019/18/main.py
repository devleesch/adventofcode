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
    def __init__(self, position, itemType, value = None, children = []):
        self.position = position
        self.itemType = itemType
        self.value = value
        self.children = children
    
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
    
    def getPath(self, x, y):
        if x >= 0 \
            and x <= len(self.tiles[0]) - 1 \
            and y >= 0 \
            and y <= len(self.tiles) - 1 \
            and not self.tiles[y][x].itemType == ItemType.WALL:
                return self.tiles[y][x]
        return None

    def getAdjacents(self, start):
        adjacents = set()
        position = start.position

        # left
        left = self.getPath(position.x - 1, position.y)
        if not left == None:
            adjacents.add(left)
        # right
        right = self.getPath(position.x + 1, position.y)
        if not right == None:
            adjacents.add(right)
        # top
        top = self.getPath(position.x, position.y - 1)
        if not top == None:
            adjacents.add(top)
        # down
        down = self.getPath(position.x, position.y + 1)
        if not down == None:
            adjacents.add(down)

        return adjacents

    def tree(self, start, visited):
        adjacents = self.getAdjacents(start)
        start.children = adjacents - visited
        visited |= start.children
        for c in start.children:
            self.tree(c, visited)

    def leafKeys(self, root, start, distance):
        keys = set()
        distance += 1
        for child in start.children:
            if child.itemType == ItemType.KEY:
                keys.add(Path(root, child, distance))
            elif child.itemType == ItemType.PASSAGE:
                keys |= self.leafKeys(root, child, distance)
        return keys

class Path:
    def __init__(self, start, end, distance, children = set()):
        self.start = start
        self.end = end
        self.distance = distance
        self.children = children

    def __str__(self):
        return "<end: {}; distance: {}>".format(self.end, self.distance)

def parse(file):
    datas = [[None for x in range(len(file[0]))] for y in range(len(file))]
    y = 0
    for l in file:
        datas[y] = list(l)
        y += 1
    return datas

def findPaths(datas):
    tunnel = Tunnel.parse(datas)
    me = tunnel.items[ItemType.ME][0]
    tunnel.tree(me, set([me]))
    return tunnel.leafKeys(me, me, 0)

def generate(generations, possibilities):
    for p in possibilities:
        for i in range(len(generations)):
            generations[i] += p
    return generations

def main():
    datas = parse(file.read("2019/18/test3.txt"))

    tunnel = Tunnel.parse(datas)
    me = tunnel.items[ItemType.ME][0]
    tunnel.tree(me, set([me]))

    possibilities = set()
    for k in tunnel.items[ItemType.KEY]:
        possibilities.add(k.value)

    possibilities = set(['a', 'b'])
    print(possibilities)
    for i in range(1):
        possibilities = generate(possibilities, possibilities)
        print(possibilities)

    print("end")

main()