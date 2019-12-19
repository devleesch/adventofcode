import sys
sys.path.append('2019/lib')
import file
import enum

class Computer:
    def __init__(self, datas):
        self.datas = datas
        self.pointer = 0
        self.relative = 0
        self.inputs = None
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

def main():
    datas = [0]*500
    i = 0
    for d in file.read("2019/19/input.txt")[0].split(','):
        datas[i] = d
        i += 1
    
    picture = [[None for x in range(50)] for y in range(50)]

    for y in range(len(picture)):
        for x in range(len(picture[y])):
            computer = Computer(list(datas))
            computer.pointer = 0
            computer.inputs = [x, y]
            _, picture[y][x] = computer.run()
    
    count = 0
    for row in picture:
        for p in row:
            display = '.'
            if p == 1:
                display = '#'
                count += 1
            print(display, end='')
        print()
    print("count: {}".format(count))

main()