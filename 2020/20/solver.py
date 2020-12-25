import typing

class Tile:
    def __init__(self, id, pic) -> None:
        self.id = id
        self.pic = pic

    @staticmethod
    def of(lines):
        _, id = lines[0].split()
        return Tile(id, lines[1:])

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

    print(len(tiles))