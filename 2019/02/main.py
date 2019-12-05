import sys
sys.path.append('2019/lib')
import file

class Operation:
    def __init__(self, opcode, index1, index2, iresult):
        self.opcode = opcode
        self.index1 = index1
        self.index2 = index2
        self.iresult = iresult
    
    def print(self):
        print("opcode: {}; index1: {}; index2: {}; iresult: {}".format(self.opcode, self.index1, self.index2, self.iresult))

    def evaluate(self, input):
        value1 = int(input[self.index1])
        value2 = int(input[self.index2])
        if self.opcode == 1:
            input[self.iresult] = value1 + value2
        elif self.opcode == 2:
            input[self.iresult] = value1 * value2

def intCode(noun, verb):
    input = file.read("2019/02/input.txt")[0].split(',')

    input[1] = noun
    input[2] = verb

    i = 0
    while(i < len(input)):
        opcode = int(input[i])
        if(opcode != 99):
            index1 = int(input[i+1])
            index2 = int(input[i+2])
            iresult = int(input[i+3])
            operation = Operation(opcode, index1, index2, iresult)
            #operation.print()
            operation.evaluate(input)
            i += 4
        else:
            break
    #print(input[0])
    return input[0]

def main():
    for noun in range(0, 99):
        for verb in range(0,99):
            result = intCode(noun, verb)
            if(result == 19690720):
                print("noun: {}; verb: {}".format(noun, verb))
                sys.exit(0)

main()