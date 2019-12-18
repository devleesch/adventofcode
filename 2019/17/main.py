import sys
sys.path.append('2019/lib')
import file
import enum

class Computer:
    def __init__(self, datas):
        self.datas = datas
        self.pointer = 0
        self.relative = 0
        self.input = None
        self.output = None
        self.interupt = None

    def setInput(self, input):
        self.input = input

    def modesAndOpcode(self, data):
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
            elif opcode == "03":
                if self.input == None:
                    self.interupt = 3
                    break

            operation = Operation(opcode, parameters)
            self.pointer, self.relative, self.output = operation.evaluate(self.datas, self.input, self.pointer, self.relative)
            self.input = None

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
            print("input: {}", input)
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

class Position:
    def __init__(self, x, y, value = None):
        self.x = x
        self.y = y
        self.value = value

class Direction(enum.Enum):
    def __init__(self, v, goForward, goRight, goLeft):
        self.v = v
        self.goForward = goForward
        self.goRight = goRight
        self.goLeft = goLeft

    UP = (0, lambda pos: Position(pos.x, pos.y - 1), lambda pos: Position(pos.x + 1, pos.y), lambda pos : Position(pos.x - 1, pos.y))
    RIGHT = (1, lambda pos: Position(pos.x + 1, pos.y), lambda pos : Position(pos.x, pos.y + 1), lambda pos : Position(pos.x, pos.y - 1))
    DOWN = (2, lambda pos: Position(pos.x, pos.y + 1), lambda pos : Position(pos.x - 1, pos.y), lambda pos : Position(pos.x + 1, pos.y))
    LEFT = (3, lambda pos: Position(pos.x - 1, pos.y), lambda pos : Position(pos.x, pos.y - 1), lambda pos : Position(pos.x, pos.y + 1))

    def moveForward(self, position):
        return self.goForward(position)
    
    def moveRight(self, position):
        return list(Direction)[(self.v+1) % 4], self.goRight(position)

    def moveLeft(self, position):
        return list(Direction)[(self.v+3) % 4], self.goLeft(position)

class Board:
    def __init__(self, points):
        self.parse(points)
    
    def parse(self, points):
        rows = 0
        cols = 0
        for p in points:
            if p == 10:
                rows += 1
            if rows == 0:
                cols += 1

        self.positions = [[None for x in range(cols)] for y in range(rows-1)]

        x = 0
        y = 0
        for p in points:
            if not p == None:
                if p == 10:
                    y += 1
                    x = 0
                else:
                    self.positions[y][x] = Position(x, y, chr(p))
                    x += 1
    
    def display(self):
        for row in self.positions:
            for p in row:
                print(p.value, end='')
            print()

    def getIntersections(self):
        intersections = []
        for row in self.positions:
            for p in row:
                intersect = self.isPath(p.x, p.y)
                intersect &= self.isPath(p.x-1, p.y)
                intersect &= self.isPath(p.x+1, p.y)
                intersect &= self.isPath(p.x, p.y-1)
                intersect &= self.isPath(p.x, p.y+1)
                if intersect:
                    intersections.append(p)
        return intersections

    def isPath(self, x, y):
        if x >= 0 \
            and x < len(self.positions[0]) \
            and y >= 0 \
            and y < len(self.positions):
            return self.positions[y][x].value == '#'
        else:
            return False

    def findPath(self):
        start = None
        for row in self.positions:
            for p in row:
                if p.value == '^':
                    start = p

        result = []
        direction = Direction.UP
        steps = 0
        while True:
            forward = direction.moveForward(start)
            if self.isPath(forward.x, forward.y):
                steps += 1
                start = forward
            else:
                directionLeft, left = direction.moveLeft(start)
                if self.isPath(left.x, left.y):
                    result.append(steps)
                    result.append('L')
                    steps = 0
                    direction = directionLeft
                    start = left
                else:
                    directionRight, right = direction.moveRight(start)
                    if self.isPath(right.x, right.y):
                        result.append(steps)
                        result.append('R')
                        steps = 0
                        direction = directionRight
                        start = right
                    else:
                        result.append(steps)
                        break
        return result

    @staticmethod
    def computeAlignment(p):
        return p.x * p.y

def main():
    datas = [0]*10000
    i = 0
    for d in file.read("2019/17/input.txt")[0].split(','):
        datas[i] = d
        i += 1

    computer = Computer(datas)
    points = []
    while not computer.interupt == 99:
        _, output = computer.run()
        points.append(output)
    
    board = Board(points)
    board.display()
    intersections = board.getIntersections()

    sum = 0
    for i in intersections:
        sum += Board.computeAlignment(i)
    print(sum)

    print(board.findPath())

def main2():
    datas = [0]*10000
    i = 0
    for d in file.read("2019/17/input.txt")[0].split(','):
        datas[i] = d
        i += 1

    datas[0] = '2'
    computer = Computer(datas)
    inputs = list("A,B,B,A,C,A,A,C,B,C\n")
    inputs += list("R,8,L,12,R,8\n")
    inputs += list("R,12,L,8,R,10\n")
    inputs += list("R,8,L,8,L,8,R,8,R,10\n")
    inputs += list("n\n")

    result = None
    while not computer.interupt == 99:
        interup, output = computer.run()
        if interup == 3:
            computer.setInput(ord(inputs.pop(0)))
        elif interup == 4:            
            print(chr(output), end='')
            result = output

    print("output: {}".format(str(result)))
    print("end")

main2()