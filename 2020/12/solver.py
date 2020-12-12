import typing
from enum import Enum


class Ship:
    def __init__(self, heading, waypoint) -> None:
        self.x = 0
        self.y = 0
        self.heading = heading
        self.waypoint = waypoint

    def move(self, move, value):
        move.apply(self, value)
        print(f"{move} -> {self}")

    def __str__(self) -> str:
        return f"[{self.x}, {self.y}], {self.heading}, {self.waypoint}"

class Move(Enum):
    def __init__(self, type, apply) -> None:
        self.type = type
        self.apply = apply

    @staticmethod
    def north(ship, value):
        ship.waypoint.y += value

    @staticmethod
    def south(ship, value):
        ship.waypoint.y -= value
    
    @staticmethod
    def west(ship, value):
        ship.waypoint.x -= value
    
    @staticmethod
    def east(ship, value):
        ship.waypoint.x += value

    @staticmethod
    def forward(ship, value):
        ship.x += ship.waypoint.x * value
        ship.y += ship.waypoint.y * value

    @staticmethod
    def left(ship, value):
        for i in range(value // 90):
            ship.heading = ship.heading.left()
            temp = ship.waypoint.x
            ship.waypoint.x = -ship.waypoint.y
            ship.waypoint.y = temp

    @staticmethod
    def right(ship, value):
        for i in range(value // 90):
            ship.heading = ship.heading.right()
            temp = ship.waypoint.x
            ship.waypoint.x = ship.waypoint.y
            ship.waypoint.y = -temp

    @staticmethod
    def of(type):
        for t in list(Move):
            if t.type == type:
                return t

    NORTH = ('N', lambda ship, distance: Move.north(ship, distance))
    SOUTH = ('S', lambda ship, distance: Move.south(ship, distance))
    WEST = ('W', lambda ship, distance: Move.west(ship, distance))
    EAST = ('E', lambda ship, distance: Move.east(ship, distance))
    FORWARD = ('F', lambda ship, distance: Move.forward(ship, distance))
    LEFT = ('L', lambda ship, deg: Move.left(ship, deg))
    RIGHT = ('R', lambda ship, deg: Move.right(ship, deg))


class Heading(Enum):
    def __init__(self, forward, left, right) -> None:
        self.forward = forward
        self.left = left
        self.right = right

    NORTH = (lambda ship, distance: (ship.x, ship.y + distance), lambda: Heading.WEST, lambda: Heading.EAST)
    SOUTH = (lambda ship, distance: (ship.x, ship.y - distance), lambda: Heading.EAST, lambda: Heading.WEST)
    WEST = (lambda ship, distance: (ship.x - distance, ship.y), lambda: Heading.SOUTH, lambda: (Heading.NORTH))
    EAST = (lambda ship, distance: (ship.x + distance, ship.y), lambda: Heading.NORTH, lambda: Heading.SOUTH)


class Waypoint:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __str__(self) -> str:
        return f"[{self.x}, {self.y}]"


def run(lines: typing.List[str]):
    ship = Ship(Heading.EAST, Waypoint(10, 1))
    for l in lines:
        ship.move(Move.of(l[0]), int(l[1:]))
    print(f"distance = {abs(ship.x) + abs(ship.y)}")