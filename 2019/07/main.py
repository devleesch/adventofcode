import sys
sys.path.append('2019/lib')
import file

class Amplifier:
    def __init__(self, datas, phase):
        self.datas = datas
        self.inputs = []
        self.inputs.append(phase)
        self.i = 0

    def modesAndOpcode(self, data):
        modes = []
        opcode = None
        
        while(len(data) < 5):
            data = "0" + data
        
        for i in range(3):
            modes.append(int(data[i]))

        opcode = data[3:]

        return modes, opcode
    
    def getParameters(self, start, opcode):
        parameters = []
        for i in range(1, Operation.getSize(opcode)):
            parameters.append(int(self.datas[start+i]))

        return parameters

    def evaluate(self, signal):
        halt = False
        self.inputs.append(signal)

        input = None
        while(self.i < len(self.datas)):
            modes, opcode = self.modesAndOpcode(self.datas[self.i])
            
            if opcode == "99": #halt
                halt = True
                break
            elif opcode == "03": #input
                input = self.inputs.pop(0)
            
            parameters = self.getParameters(self.i, opcode)
            operation = Operation(modes, opcode, parameters)
            currentSignal = operation.evaluate(self.datas, input)

            input = None
            self.i = operation.getNext(self.i)

            if opcode == "04": #output
                signal = currentSignal
                break

        return halt, signal
        
class Operation:
    def __init__(self, modes, opcode, parameters):
        self.modes = modes
        self.opcode = opcode
        self.parameters = parameters
        self.values = []
    
    def __str__(self):
        return "modes: {} \
                opcode: {}; \
                p1: {}; \
                p2: {}; \
                p3: {}".format(self.modes, self.opcode, self.p1, self.p2, self.p3)

    @staticmethod
    def getSize(opcode):
        if(opcode == "01"):
            return 4
        elif(opcode == "02"):
            return 4
        elif(opcode == "03"):
            return 2
        elif(opcode == "04"):
            return 2
        elif(opcode == "05"):
            return 3
        elif(opcode == "06"):
            return 3
        elif(opcode == "07"):
            return 4
        elif(opcode == "08"):
            return 4

    def getNbValues(self):
        if self.opcode == "01":
            return 2
        elif self.opcode == "02":
            return 2
        elif self.opcode == "03":
            return 0
        elif self.opcode == "04":
            return 0
        elif self.opcode == "05":
            return 2
        elif self.opcode == "06":
            return 2
        elif self.opcode == "07":
            return 2
        elif self.opcode == "08":
            return 2

    def getNext(self, i):
        if self.opcode == "05":
            if self.values[0] != 0:
                return self.values[1]
        elif self.opcode == "06":
            if self.values[0] == 0:
                return self.values[1]
        
        return i + Operation.getSize(self.opcode)

    def evaluate(self, datas, input):
        nbValues = self.getNbValues()
        for i in range(nbValues):
            self.values.append(self.value(self.modes[nbValues-i], self.parameters[i], datas))

        if self.opcode == "01":
            datas[self.parameters[2]] = str(self.values[0] + self.values[1])
        elif self.opcode == "02":
            datas[self.parameters[2]] = str(self.values[0] * self.values[1])
        elif self.opcode == "03":
            datas[self.parameters[0]] = input
        elif self.opcode == "04":
            return int(datas[self.parameters[0]])
        # nothing to do for 05 and 06, they are just address changes
        elif self.opcode == "07":
            datas[self.parameters[2]] = 1 if self.values[0] < self.values[1] else 0
        elif self.opcode == "08":
            datas[self.parameters[2]] = 1 if self.values[0] == self.values[1] else 0

    def value(self, mode, p, data):
        if(mode == 0):
            return int(data[p])
        elif(mode == 1):
            return p

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
    permutations = []
    #a = [0,1,2,3,4]
    a = [9,8,7,6,5]
    n = len(a)
    heapPermutation(a, n, n, permutations)

    maxThuster = -sys.maxsize
    for phases in permutations:
        print(phases)
        data = file.read("2019/07/input1.txt")[0].split(',')
        amplifiers = []
        for i in range(5):
            amplifiers.append(Amplifier(data.copy(), phases[i]))

        signal = 0
        i = 0
        while(True):
            amplifier = amplifiers[i%5]
            halt, signal = amplifier.evaluate(signal)
            if halt:
                break
            i += 1
        maxThuster = max(signal, maxThuster)
    print("max: {}".format(maxThuster))

main()