import typing
import re
from typing import Container


class Bag:
    def __init__(self, color, number = None) -> None:
        self.color = color
        self.number = number
        self.contains = None
    
    def __eq__(self, o: object) -> bool:
        return self.color == o.color

    def __str__(self) -> str:
        str = f"color={self.color} number={self.number}"
        if self.contains:
            str += f" {self.contains}"
        return str

    def __repr__(self) -> str:
        return str(self)

    @staticmethod
    def parse(line: str):
        matches = re.match("(?P<color>.+) bags contain (?P<bags>.+)*", line)
        bag = Bag(matches.group('color'))
        bag.contains = []
        for b in re.findall("(?P<number>\d) (?P<color>.+?) bags?", matches.group('bags')):
            bag.contains.append(Bag(b[1], int(b[0])))
        return bag

def run(lines: typing.List[str]):
    bags = []
    for l in lines:
        bags.append(Bag.parse(l))
    
    founds = reverse_search(Bag("shiny gold"), bags, [])
    print("a={}".format(len(founds)))
    print("b={}".format(deep_search(Bag("shiny gold"), bags) - 1)) # -1 because "shiny gold" bag doesn't count

def reverse_search(search, bags, founds):
    for b in bags:
        if search in b.contains:
            if not b in founds:
                founds.append(b)
            reverse_search(b, bags, founds)
    return founds

def deep_search(search, bags):
    for b in bags:
        if b == search:
            if b.contains:
                count = 1
                for c in b.contains:
                    count += c.number * deep_search(c, bags)
                return count
            else:
                return 1