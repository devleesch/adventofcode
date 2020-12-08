import typing


class Instruction:
    def __init__(self, op, arg) -> None:
        self.op = op
        self.arg = arg

    def execute(self, accumulator, pointer):
        if self.op == 'acc':
            accumulator += self.arg
            pointer += 1
        elif self.op == 'jmp':
            pointer += self.arg
        elif self.op == 'nop':
            pointer += 1
        return (accumulator, pointer)

    def __str__(self) -> str:
        return f"{self.op} {self.arg}"

    @staticmethod
    def parse(line: str):
        (code, arg) = line.split()
        op = Instruction(code, int(arg))
        return op

def run(lines: typing.List[str]):
    i = 0
    while True:
        print(i)
        jmpNop = 0
        accumulator = 0
        pointer = 0
        executed = []
        while not pointer in executed:
            ins = Instruction.parse(lines[pointer])

            if ins.op in ['jmp', 'nop']:
                if jmpNop == i:
                    ins.op = 'jmp' if ins.op == 'nop' else 'nop'
                jmpNop += 1

            executed.append(pointer)
            (accumulator, pointer) = ins.execute(accumulator, pointer)

            if pointer == len(lines):
                break
        i += 1
        if pointer == len(lines):
            break
    print(accumulator)
