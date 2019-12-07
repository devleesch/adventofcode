import sys
sys.path.append('2019/lib')
import file

class Operation:
    def __init__(self, modes, opcode, p1, p2, p3):
        self.modes = modes
        self.opcode = opcode
        self.p1 = p1
        self.p2 = p2
        self.p3 = p3
        self.value1 = None
        self.value2 = None
    
    def __str__(self):
        return "modes: {} \
                opcode: {}; \
                p1: {}; \
                p2: {}; \
                p3: {}".format(self.modes, self.opcode, self.p1, self.p2, self.p3)

    def getNext(self, i):
        if(self.opcode == "01"):
            return i + 4
        elif(self.opcode == "02"):
            return i + 4
        elif(self.opcode == "03"):
            return i + 2
        elif(self.opcode == "04"):
            return i + 2
        elif(self.opcode == "05"):
            if(self.value1 != 0):
                return self.value2
            else:
                return i + 3
        elif(self.opcode == "06"):
            if(self.value1 == 0):
                return self.value2
            else:
                return i + 3
        elif(self.opcode == "07"):
            return i + 4
        elif(self.opcode == "08"):
            return i + 4

    def evaluate(self, input):
        self.value1 = self.value(self.modes[2], self.p1, input)
        if self.opcode == "01":
            self.value2 = self.value(self.modes[1], self.p2, input)
            input[self.p3] = str(self.value1 + self.value2)
        elif self.opcode == "02":
            self.value2 = self.value(self.modes[1], self.p2, input)
            input[self.p3] = str(self.value1 * self.value2)
        elif self.opcode == "03":
            input[self.p1] = 5
        elif self.opcode == "04":
            print(input[self.p1])
        elif self.opcode == "05":
            self.value2 = self.value(self.modes[1], self.p2, input)
        elif self.opcode == "06":
            self.value2 = self.value(self.modes[1], self.p2, input)
        elif self.opcode == "07":
            self.value2 = self.value(self.modes[1], self.p2, input)
            if(self.value1 < self.value2):
                input[self.p3] = 1
            else:
                input[self.p3] = 0
        elif self.opcode == "08":
            self.value2 = self.value(self.modes[1], self.p2, input)
            if(self.value1 == self.value2):
                input[self.p3] = 1
            else:
                input[self.p3] = 0

    
    def value(self, mode, p, input):
        if(mode == 0):
            return int(input[p])
        elif(mode == 1):
            return p

def modeAndOpcode(input):
    modes = []
    opcode = None
    
    while(len(input) < 5):
        input = "0" + input
    
    for i in range(3):
        modes.append(int(input[i]))

    opcode = input[3:]

    return modes, opcode

def parameters(input, start, opcode):
    p1 = None
    p2 = None
    p3 = None
    if(opcode == "01"):
        p1 = int(input[start+1])
        p2 = int(input[start+2])
        p3 = int(input[start+3])
    elif(opcode == "02"):
        p1 = int(input[start+1])
        p2 = int(input[start+2])
        p3 = int(input[start+3])
    elif(opcode == "03"):
        p1 = int(input[start+1])
    elif(opcode == "04"):
        p1 = int(input[start+1])
    elif(opcode == "05"):
        p1 = int(input[start+1])
        p2 = int(input[start+2])
    elif(opcode == "06"):
        p1 = int(input[start+1])
        p2 = int(input[start+2])
    elif(opcode == "07"):
        p1 = int(input[start+1])
        p2 = int(input[start+2])
        p3 = int(input[start+3])
    elif(opcode == "08"):
        p1 = int(input[start+1])
        p2 = int(input[start+2])
        p3 = int(input[start+3])

    return p1, p2, p3

def main():
    input = file.read("2019/05/test1.txt")[0].split(',')

    i = 0
    while(i < len(input)):
        modes, opcode = modeAndOpcode(input[i])
        if(opcode != "99"):
            p1, p2, p3 = parameters(input, i, opcode)
            operation = Operation(modes, opcode, p1, p2, p3)
            operation.evaluate(input)
            i = operation.getNext(i)
        else:
            break

main()