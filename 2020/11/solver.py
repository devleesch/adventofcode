import typing


class Plane:
    def __init__(self) -> None:
        self.lines = []

    def play(self):
        changes = []
        for line in self.lines:
            for seat in line:
                if seat:
                    occupied = list(filter(lambda s: s.occupied, seat.adjacents(self)))
                    if not seat.occupied and len(occupied) == 0:
                        changes.append(seat)
                    elif seat.occupied and len(occupied) >= 4:
                        changes.append(seat)
        return changes

    def apply(self, changes):
        for s in changes:
            s.occupied = not s.occupied

    def __repr__(self) -> str:
        r = ""
        for line in self.lines:
            for seat in line:
                if not seat:
                    r += "."
                else:
                    r += "#" if seat.occupied else "L"
            r += "\n"
        return r


class Seat:
    def __init__(self, x, y, occupied) -> None:
        self.x = x
        self.y = y
        self.occupied = occupied

    def adjacents(self, plane):
        adjacents = []
        for y in range(-1, 2):
            for x in range(-1, 2):
                if x != 0 or y != 0:
                    ry = self.y + y
                    rx = self.x + x
                    if ry >= 0 and ry < len(plane.lines) and rx >= 0 and rx < len(plane.lines[0]):
                        if plane.lines[ry][rx]:
                            adjacents.append(plane.lines[ry][rx])
        return adjacents

    def __str__(self) -> str:
        return f"[{self.x}, {self.y}] -> {self.occupied}"

    def __repr__(self):
        return str(self)


def run(lines: typing.List[str]):
    plane = Plane()

    y = 0
    for l in lines:
        x = 0
        seats = []
        for c in l:
            if c != '.':
                s = Seat(x, y, c == "#")
                seats.append(s)
            else:
                seats.append(None)
            x += 1
        plane.lines.append(seats)
        y += 1
    
    changes = plane.play()
    while(len(changes)):
        plane.apply(changes)
        #print(plane)
        changes = plane.play()

    nb_occupied = 0
    for line in plane.lines:
        for seat in line:
            if seat and seat.occupied:
                nb_occupied += 1
    print(nb_occupied)