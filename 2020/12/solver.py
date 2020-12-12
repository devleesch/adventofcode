import typing
from enum import Enum


class Ship:
    def __init__(self, x, y, heading) -> None:
        self.x = x
        self.y = y
        self.heading = heading

    def move(self, move, value):
        (self.x, self.y, self.heading) = move.apply(self, value)
        print(f"{move} -> {self}")

    def __str__(self) -> str:
        return f"{self.x}, {self.y}, {self.heading}"

class Move(Enum):
    def __init__(self, type, apply) -> None:
        self.type = type
        self.apply = apply

    @staticmethod
    def left(ship, value):
        for i in range(value // 90):
            ship.heading = ship.heading.left()
        return ship.heading

    @staticmethod
    def right(ship, value):
        for i in range(value // 90):
            ship.heading = ship.heading.right()
        return ship.heading

    @staticmethod
    def of(type):
        for t in list(Move):
            if t.type == type:
                return t

    NORTH = ('N', lambda ship, distance: (ship.x, ship.y + distance, ship.heading))
    SOUTH = ('S', lambda ship, distance: (ship.x, ship.y - distance, ship.heading))
    WEST = ('W', lambda ship, distance: (ship.x - distance, ship.y, ship.heading))
    EAST = ('E', lambda ship, distance: (ship.x + distance, ship.y, ship.heading))
    FORWARD = ('F', lambda ship, distance: (*ship.heading.forward(ship, distance), ship.heading))
    LEFT = ('L', lambda ship, value: (ship.x, ship.y, Move.left(ship, value)))
    RIGHT = ('R', lambda ship, value: (ship.x, ship.y, Move.right(ship, value)))


class Heading(Enum):
    def __init__(self, forward, left, right) -> None:
        self.forward = forward
        self.left = left
        self.right = right

    NORTH = (lambda ship, distance: (ship.x, ship.y + distance), lambda: Heading.WEST, lambda: Heading.EAST)
    SOUTH = (lambda ship, distance: (ship.x, ship.y - distance), lambda: Heading.EAST, lambda: Heading.WEST)
    WEST = (lambda ship, distance: (ship.x - distance, ship.y), lambda: Heading.SOUTH, lambda: Heading.NORTH)
    EAST = (lambda ship, distance: (ship.x + distance, ship.y), lambda: Heading.NORTH, lambda: Heading.SOUTH)


def run(lines: typing.List[str]):
    ship = Ship(0, 0, Heading.EAST)
    for l in lines:
        ship.move(Move.of(l[0]), int(l[1:]))
    print(f"distance = {abs(ship.x) + abs(ship.y)}")