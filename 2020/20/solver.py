import typing
import enum


SEA_MONSTER = [
    '                  # ', 
    '#    ##    ##    ###', 
    ' #  #  #  #  #  #   '
]


class Side(enum.Enum):
    LEFT = 0
    BOTTOM = 1
    RIGHT = 2
    TOP = 3

    def opposit(self):
        return Side((self.value + 2) % 4)
        

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
            for y in range(len(self.pic)):
                line += self.pic[len(self.pic) - 1 - y][x]
            rotated.append(line)
        self.pic = rotated
        self.compute_hashes()

    def flip_left_right(self):
        flipped = []
        for y in range(len(self.pic)):
            line = ""
            for x in range(len(self.pic[0])):
                line += self.pic[y][len(self.pic[0]) - 1 - x]
            flipped.append(line)
        self.pic = flipped
        self.compute_hashes()

    def flip_top_bottom(self):
        flipped = []
        for y in range(len(self.pic)):
            flipped.append(self.pic[len(self.pic) - 1 - y])
        self.pic = flipped
        self.compute_hashes()

    def get_side(self, side):
        if side in [Side.LEFT, Side.RIGHT]:
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
            self.hashes[side] = Tile.to_int(self.get_side(side))

    def link(self, tiles, placed):
        next = set()
        for t in tiles:
            if self != t:
                skip = False
                for f in range(2): # flip
                    for _ in range(4): # rotate
                        for side in Side:
                            if not side in self.sides and self.hashes[side] == t.hashes[side.opposit()]:
                                self.sides[side] = t
                                t.sides[side.opposit()] = self
                                next.add(t)
                                placed.add(t)
                                skip = True
                                break
                        if skip:
                            break
                        if not t in placed:
                            t.rotate()
                    if skip:
                        break
                    if not t in placed:
                        if f == 0:
                            t.flip_left_right()
                        else:
                            t.flip_left_right()
                            t.flip_top_bottom()
        return next

    def __eq__(self, o: object) -> bool:
        if o is None:
            return False
        return self.id == o.id

    def __hash__(self) -> int:
        return int(self.id)

    @staticmethod
    def of(lines):
        _, id = lines[0].replace(":", "").split()
        return Tile(id, lines[1:])

    @staticmethod
    def to_int(line):
        value = 0
        i = 0
        for c in line:
            f = 1 if c == '#' else 0
            value += f * pow(2, i)
            i += 1
        return value

def run(lines: typing.List[str]):
    # create tiles from lines
    tiles = []
    tile = []
    for line in lines:
        if line:
            tile.append(line)
        else:
            tiles.append(Tile.of(tile))
            tile = []
    # add last tile
    if len(tile):
        tiles.append(Tile.of(tile))

    # attach tiles together
    next = set([tiles[0]])
    placed = set()
    while(len(next)):
        for tile in next:
            placed.add(tile)
        after = set()
        for current in next:
            #print(f"matching {current.id}")
            after.update(current.link(tiles, placed))
        next = after

    # looking for corners
    corners = {}
    corners[(Side.TOP, Side.LEFT)] = None
    corners[(Side.TOP, Side.RIGHT)] = None
    corners[(Side.BOTTOM, Side.LEFT)] = None
    corners[(Side.BOTTOM, Side.RIGHT)] = None
    result = 1
    for corner in corners.keys():
        for tile in placed:
            if not corner[0] in tile.sides and not corner[1] in tile.sides:
                corners[corner] = tile
                result *= int(tile.id)
                print(f"{corner} - {tile.id}")
    print(f"result = {result}")

    # merging tiles to make a picture
    current_y = corners[(Side.TOP, Side.LEFT)]
    raw_picture = []
    f = 0
    while current_y != None:
        current_x = current_y
        while current_x != None:
            for y, line in enumerate(current_x.pic):
                if y > 0 and y < len(current_x.pic) - 1:
                    index = (y - 1) + f * (len(current_x.pic) - 2)
                    if len(raw_picture) <= index:
                        raw_picture.append('')
                    raw_picture[index] += line[1:-1]
            current_x = current_x.sides[Side.RIGHT] if Side.RIGHT in current_x.sides else None
        current_y = current_y.sides[Side.BOTTOM] if Side.BOTTOM in current_y.sides else None
        f += 1
    
    picture = Tile(0, raw_picture)
    pass