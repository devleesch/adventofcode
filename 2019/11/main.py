import sys
sys.path.append('2019/lib')
import file

class Computer:
    def __init__(self, datas):
        self.datas = datas
        self.pointer = 0
        self.relative = 0
        self.inputs = []
        self.outputs = []
        self.terminated = False

    def addInput(self, input):
        self.inputs.append(input)

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
        self.outputs = []
        while not self.terminated:
            modes, opcode = self.modesAndOpcode(self.datas[self.pointer])
            parameters = self.getParameters(modes, opcode)

            if opcode == "99":
                self.terminated = True
                break

            operation = Operation(opcode, parameters)
            self.pointer, self.relative, output = operation.evaluate(self.datas, self.inputs, self.pointer, self.relative)
            if output != None:
                self.outputs.append(output)
                if len(self.outputs) == 2:
                    break

class Operation:
    def __init__(self, opcode, parameters):
        self.opcode = opcode
        self.parameters = parameters

    def evaluate(self, datas, inputs, pointer, relative):
        output = None
        updatePointer = True
        if self.opcode == "01":
            datas[self.parameters[2]] = str(int(datas[self.parameters[0]]) + int(datas[self.parameters[1]]))
        elif self.opcode == "02":
            datas[self.parameters[2]] = str(int(datas[self.parameters[0]]) * int(datas[self.parameters[1]]))
        elif self.opcode == "03":
            datas[self.parameters[0]] = inputs.pop()
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

def move(turn, x, y, heading):
    angle = 90
    if turn == 0:
        angle *= -1

    heading += angle
    while(heading < 0):
        heading += 360

    while(heading >= 360):
        heading -= 360

    if heading == 0:
        y -= 1
    elif heading == 90:
        x += 1
    elif heading == 180:
        y += 1
    elif heading == 270:
        x -= 1
    return x, y, heading

def main():
    datas = [0]*10000
    i = 0
    for d in file.read("2019/11/input.txt")[0].split(','):
        datas[i] = d
        i += 1

    rows, cols = (100, 150)
    image = [[None for i in range(rows)] for j in range(cols)]
    x = int(rows / 2)
    y = int(cols /2)
    image[y][x] = 1 # start on white for 11.2
    heading = 0
    computer = Computer(datas)
    computer.addInput(image[y][x])
    while not computer.terminated:
        computer.run()
        #print(computer.outputs)
        if not computer.terminated:
            image[y][x] = computer.outputs[0]
            x, y, heading = move(computer.outputs[1], x, y, heading)
            input = 0 if image[y][x] == None else image[y][x]
            computer.addInput(input)
            #print("{};{};{}".format(x, y, heading))

    count = 0
    for y in range(len(image)):
        for x in range(len(image[y])):
            if image[y][x] != None:
                count += 1
            pixel = ' ' if image[y][x] == 0 or image[y][x] == None else '#'
            print(pixel, end='')
        print()

    print(count)
    print("end")

main()