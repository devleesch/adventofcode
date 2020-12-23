import typing


class CrabCups:
    def __init__(self, first, max) -> None:
        self.first = first
        self.max = max

    def move(self, current):
        #print("cups: " + str(self))
        pick_up = []
        next = current.next
        for _ in range(3):
            pick_up.append(next)
            current.next = next
            next = next.next
        current.next = next
        #print("pick up: " + str(pick_up))

        dest = self.destination(current, pick_up)
        #print("destination: " + str(dest))
        tmp = dest.next
        dest.next = pick_up[0]
        pick_up[2].next = tmp

        # print()
        return current.next

    def destination(self, start):
        max = None
        min = None

        current = start
        for cup in subset:
            if max == None or cup.label > max.label:
                max = cup
            if min == None or cup.label < min.label:
                min = cup

        target = start.label - 1
        while target >= min.label:
            current = start
            while current.label != start.label:
                if current.label == target:
                    return current
            target -= 1
        
        return max

    def __str__(self) -> str:
        r = ""
        for c in self.cups:
            if c.label == 1:
                next = c.next
                while next != c:
                    r += str(next.label) + " "
                    next = next.next
        return r

    @staticmethod
    def of(input):
        input = [int(x) for x in input]
        input.append(10)

        previous = None
        for c in input:
            cup = Cup(c)
            if previous:
                previous.next = cup
            previous = cup
        return CrabCups()

class Cup:
    def __init__(self, label) -> None:
        self.label = label
        self.next = None

    def get_next(self):
        

    def __eq__(self, o: object) -> bool:
        if o == None:
            return False
        return self.label == o.label

    def __hash__(self) -> int:
        return self.label

    def __str__(self) -> str:
        return str(self.label)

    def __repr__(self) -> str:
        return str(self)

def run(lines: typing.List[str]):
    a = "389125467"
    input = "789465123"
    cc = CrabCups.of(a)

    current = cc.cups[0]
    for i in range(10000000):
        if i % 100000 == 0:
            print(f"-- move {i+1} --")
        current = cc.move(current)

    for c in cc.cups:
        if c.label == 1:
            current = c.next
            while current != c:
                print(current.label, end='')
                current = current.next
            print()
            break