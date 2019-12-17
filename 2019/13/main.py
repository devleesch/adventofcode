import sys
sys.path.append('2019/lib')
import file

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

class Game:
    def __init__(self, computer: Computer):
        self.computer = computer
        self.tiles = [[None for x in range(38)] for y in range(21)]
        self.score = 0

    def play(self):
        i = 0
        x = None
        y = None
        while not self.computer.interupt == 99:
            interupt, output = self.computer.run()
            if interupt == 3:
                self.move()
            elif interupt == 4:
                if i % 3 == 0:
                    x = output
                elif i % 3 == 1:
                    y = output
                elif i % 3 == 2:
                    if x >= 0 and y >= 0:
                        self.tiles[y][x] = output
                    else:
                        self.score = output
                i += 1
        print("score: {}".format(self.score))

    
    def getDisplayChar(self, t):
        if t == 0:
            return ' '
        elif t == 1:
            return '='
        elif t == 2:
            return '#'
        elif t == 3:
            return '_'
        elif t == 4:
            return '*'

    def display(self):
        for row in self.tiles:
            for tile in row:
                print(self.getDisplayChar(tile), end='')
            print()

    def move(self):
        ball = None
        paddle = None
        for y in range(len(self.tiles)):
            for x in range(len(self.tiles[y])):
                if self.tiles[y][x] == 3:
                    ball = {'x': x, 'y': y}
                elif self.tiles[y][x] == 4:
                    paddle = {'x': x, 'y': y}

        if paddle.get('x') < ball.get('x'):
            self.computer.setInput(-1)
        elif paddle.get('x') > ball.get('x'):
            self.computer.setInput(1)
        else:
            self.computer.setInput(0)

    def nbBlocks(self):
        blocks = 0
        for t in self.tiles:
            if t.type == 2:
                blocks += 1
        return blocks

    def terminated(self):
        return self.computer.terminated

def main():
    datas = [0]*10000
    i = 0
    for d in file.read("2019/13/input.txt")[0].split(','):
        datas[i] = d
        i += 1

    # start the game
    datas[0] = '2'

    computer = Computer(datas)
    game = Game(computer)
    game.play()
    print("end")

main()