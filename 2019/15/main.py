import sys
sys.path.append('2019/lib')
import file
import enum
import random

class Computer:
    def __init__(self, datas):
        self.datas = datas
        self.pointer = 0
        self.relative = 0
        self.inputs = []
        self.output = None
        self.interupt = None

    def modesAndOpcode(self, data):
        data = str(data)
        while(len(data) < 5):
            data = '0'+data
        modes = list(data[0:3])
        modes.reverse()
        return modes, data[3:]
    
    def getParameters(self, modes, opcode):
        parameters = []
        i = 1
        for p in range(Operation.size(opcode) - 1):
            parameters.append(int(self.address(modes[i - 1], self.pointer + i)))
            i += 1
        return parameters

    def address(self, mode, pointer):
        if mode == '0':
            return self.datas[pointer]
        elif mode == '1':
            return pointer
        elif mode == '2':
            return int(self.datas[pointer]) + self.relative

    def run(self):
        self.output = None
        self.interupt = None
        while True:
            modes, opcode = self.modesAndOpcode(self.datas[self.pointer])
            parameters = self.getParameters(modes, opcode)

            if opcode == "99":
                self.interupt = 99
                break

            input = None
            if opcode == "03":
                input = self.inputs.pop(0)

            operation = Operation(opcode, parameters)
            self.pointer, self.relative, self.output = operation.evaluate(self.datas, input, self.pointer, self.relative)

            if self.output != None:
                self.interupt = 4
                break

        return self.interupt, self.output

class Operation:
    def __init__(self, opcode, parameters):
        self.opcode = opcode
        self.parameters = parameters

    def evaluate(self, datas, input, pointer, relative):
        output = None
        updatePointer = True
        if self.opcode == "01":
            datas[self.parameters[2]] = str(int(datas[self.parameters[0]]) + int(datas[self.parameters[1]]))
        elif self.opcode == "02":
            datas[self.parameters[2]] = str(int(datas[self.parameters[0]]) * int(datas[self.parameters[1]]))
        elif self.opcode == "03":
            datas[self.parameters[0]] = input
        elif self.opcode == "04":
            output = int(datas[self.parameters[0]])
        elif self.opcode == "05":
            if int(datas[self.parameters[0]]) != 0:
                pointer = int(datas[self.parameters[1]])
                updatePointer = False
        elif self.opcode == "06":
            if int(datas[self.parameters[0]]) == 0:
                pointer = int(datas[self.parameters[1]])
                updatePointer = False
        elif self.opcode == "07":
            datas[self.parameters[2]] = 1 if int(datas[self.parameters[0]]) < int(datas[self.parameters[1]]) else 0
        elif self.opcode == "08":
            datas[self.parameters[2]] = 1 if int(datas[self.parameters[0]]) == int(datas[self.parameters[1]]) else 0
        elif self.opcode == "09":
            relative += int(datas[self.parameters[0]])

        if updatePointer:
            pointer += Operation.size(self.opcode)

        return pointer, relative, output

    @staticmethod
    def size(opcode):
        if opcode == "01":
            return 4
        elif opcode == "02":
            return 4
        elif opcode == "03":
            return 2
        elif opcode == "04":
            return 2
        elif opcode == "05":
            return 3
        elif opcode == "06":
            return 3
        elif opcode == "07":
            return 4
        elif opcode == "08":
            return 4
        elif opcode == "09":
            return 2
        elif opcode == "99":
            return 1

class Tile(enum.Enum):
    def __init__(self, code, display):
        self.code = code
        self.display = display

    @staticmethod
    def of(code):
        if code == 0:
            return Tile.WALL
        elif code == 1:
            return Tile.PATH
        elif code == 2:
            return Tile.OXYGEN

    WALL = (0, '#')
    PATH = (1, '.')
    OXYGEN = (2, '0')

class Heading(enum.Enum):
    def __init__(self, code, moveForward, turnRight):
        self.code = code
        self.moveForward = moveForward
        self.turnRight = turnRight
    
    NORTH = ('1', lambda position: Position(position.x, position.y - 1), lambda: Heading.EAST)
    EAST = ('4', lambda position: Position(position.x + 1, position.y), lambda: Heading.SOUTH)
    SOUTH = ('2', lambda position: Position(position.x, position.y + 1), lambda: Heading.WEST)
    WEST = ('3', lambda position: Position(position.x - 1, position.y), lambda: Heading.NORTH)

class Position:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __eq__(self, value):
        return self.x == value.x \
            and self.y == value.y

class Robot:
    def __init__(self, position, heading):
        self.position = position
        self.heading = heading

    def move(self):
        return self.heading.moveForward(self.position)


class Maze:
    def __init__(self, size):
        self.tiles = [[None for x in range(size)] for y in range(size)]

    def __str__(self):
        string = ""
        for row in self.tiles:
            for t in row:
                string += t.display if t != None else ' '
            string += '\n'
        return string

def main():
    intCode = file.read("2019/15/input.txt")[0].split(',')
    computer = Computer(intCode)

    size = 50
    robot = Robot(Position(round(size / 2), round(size / 2)), Heading.NORTH)
    maze = Maze(size)
    while(maze.tiles[robot.position.y][robot.position.x] != Tile.OXYGEN):
        newPosition = robot.move()
        computer.inputs.append(robot.heading.code)
        _, output = computer.run()

        tile = Tile.of(output)
        maze.tiles[newPosition.y][newPosition.x] = tile

        if tile != Tile.WALL:
            robot.position = newPosition

        print(maze)
        

main()