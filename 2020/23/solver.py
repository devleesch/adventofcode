import typing


class CrabCups:

    MAX = 1_000_000

    def __init__(self, cups: dict) -> None:
        self.cups = cups

    def move(self, start):
        #print("cups: " + str(self))
        pick_up = []
        current = start.next
        for _ in range(3):
            pick_up.append(current.label)
            current = current.next

        start.next = current
        #print("pick up: " + str(pick_up))

        dest = self.cups[self.destination(start, pick_up)]
        #print("destination: " + str(dest))
        tmp = dest.next
        dest.next = self.cups[pick_up[0]]
        self.cups[pick_up[2]].next = tmp

        # print()
        return start.next

    def destination(self, start, pick_up):
        dest = start.label - 1
        while dest > 0:
            if not dest in pick_up:
                return dest
            dest -= 1

        max = CrabCups.MAX
        while max in pick_up:
            max = max.label - 1
        
        return max

    @staticmethod
    def of(input):
        print("initializing game")
        input = [int(x) for x in input]
        input.extend([x for x in range(10, CrabCups.MAX+1)])
        previous = None
        cups = {}
        for c in input:
            label = int(c)
            cup = Cup(label)
            if previous:
                previous.next = cup
            previous = cup
            cups[label] = cup
        previous.next = cups[input[0]]
        print("game initialized !")
        return CrabCups(cups)

class Cup:
    def __init__(self, label) -> None:
        self.label = label
        self.next = None

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
    #input = "389125467"
    input = "789465123"
    game = CrabCups.of(input)

    current = game.cups[int(input[0])]
    for i in range(10000000):
        if i % 100000 == 0:
            print(f"-- move {i+1} --")
        current = game.move(current)

    current = game.cups[1]
    product = 1
    for _ in range(2):
        current = current.next
        print(current.label)
        product *= current.label
    print(product)

    # for c in game.cups:
    #     if c.label == 1:
    #         current = c.next
    #         while current != c:
    #             print(current.label, end='')
    #             current = current.next
    #         print()
    #         break