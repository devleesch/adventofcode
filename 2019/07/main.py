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

    def evaluate(self, data, input):
        self.value1 = self.value(self.modes[2], self.p1, data)
        if self.opcode == "01":
            self.value2 = self.value(self.modes[1], self.p2, data)
            data[self.p3] = str(self.value1 + self.value2)
        elif self.opcode == "02":
            self.value2 = self.value(self.modes[1], self.p2, data)
            data[self.p3] = str(self.value1 * self.value2)
        elif self.opcode == "03":
            data[self.p1] = input
        elif self.opcode == "04":
            return int(data[self.p1])
        elif self.opcode == "05":
            self.value2 = self.value(self.modes[1], self.p2, data)
        elif self.opcode == "06":
            self.value2 = self.value(self.modes[1], self.p2, data)
        elif self.opcode == "07":
            self.value2 = self.value(self.modes[1], self.p2, data)
            if(self.value1 < self.value2):
                data[self.p3] = 1
            else:
                data[self.p3] = 0
        elif self.opcode == "08":
            self.value2 = self.value(self.modes[1], self.p2, data)
            if(self.value1 == self.value2):
                data[self.p3] = 1
            else:
                data[self.p3] = 0

    
    def value(self, mode, p, data):
        if(mode == 0):
            return int(data[p])
        elif(mode == 1):
            return p

def modeAndOpcode(data):
    modes = []
    opcode = None
    
    while(len(data) < 5):
        data = "0" + data
    
    for i in range(3):
        modes.append(int(data[i]))

    opcode = data[3:]

    return modes, opcode

def parameters(data, start, opcode):
    p1 = None
    p2 = None
    p3 = None
    if(opcode == "01"):
        p1 = int(data[start+1])
        p2 = int(data[start+2])
        p3 = int(data[start+3])
    elif(opcode == "02"):
        p1 = int(data[start+1])
        p2 = int(data[start+2])
        p3 = int(data[start+3])
    elif(opcode == "03"):
        p1 = int(data[start+1])
    elif(opcode == "04"):
        p1 = int(data[start+1])
    elif(opcode == "05"):
        p1 = int(data[start+1])
        p2 = int(data[start+2])
    elif(opcode == "06"):
        p1 = int(data[start+1])
        p2 = int(data[start+2])
    elif(opcode == "07"):
        p1 = int(data[start+1])
        p2 = int(data[start+2])
        p3 = int(data[start+3])
    elif(opcode == "08"):
        p1 = int(data[start+1])
        p2 = int(data[start+2])
        p3 = int(data[start+3])

    return p1, p2, p3

def getInput(phase, signal, nbInput):
    if nbInput % 2 == 0:
        return signal
    else:
        return phase

def tryPhases(phases, data):
    signal = 0
    nbInput = 0

    for p in phases:
        i = 0
        while(i < len(data)):
            modes, opcode = modeAndOpcode(data[i])
            if(opcode != "99"):
                if opcode == "03":
                    nbInput += 1
                p1, p2, p3 = parameters(data, i, opcode)
                operation = Operation(modes, opcode, p1, p2, p3)
                s = operation.evaluate(data, getInput(p, signal, nbInput))
                if s != None:
                    signal = s
                i = operation.getNext(i)
            else:
                break
    return signal

def heapPermutation(a, size, n, phases):
    # if size becomes 1 then prints the obtained 
    # permutation 
    if (size == 1):
        phases.append(a.copy())
  
    for i in range(size): 
        heapPermutation(a,size-1,n, phases)
  
        # if size is odd, swap first and last 
        # element 
        # else If size is even, swap ith and last element 
        if size&1: 
            a[0], a[size-1] = a[size-1],a[0] 
        else: 
            a[i], a[size-1] = a[size-1],a[i]

def main():
    data = file.read("2019/07/input1.txt")[0].split(',')
    a = [0,1,2,3,4]
    n = len(a)
    phases = []
    heapPermutation(a, n, n, phases)

    maxPhase = phases[0]
    maxSignal = -sys.maxsize
    for p in phases:
        signal = tryPhases(p, data)
        if(signal > maxSignal):
            maxSignal = signal
            maxPhase = p

    print("maxSignal: {}".format(maxSignal))

main()