import typing
import re
from typing import Container


class Bag:
    def __init__(self, color) -> None:
        self.color = color
        self.contains = None
    
    def __eq__(self, o: object) -> bool:
        return self.color == o.color

    def __str__(self) -> str:
        return self.color

    def __repr__(self) -> str:
        return str(self)

    @staticmethod
    def parse(line: str):
        matches = re.match("(?P<color>.+) bags contain (?P<bags>.+)*", line)
        bag = Bag(matches.group('color'))
        bag.contains = []
        for b in re.findall("(?P<number>\d) (?P<color>.+?) bags?", matches.group('bags')):
            bag.contains.append(Bag(b[1]))
        return bag

def run(lines: typing.List[str]):
    bags = []
    for l in lines:
        bags.append(Bag.parse(l))
    
    count = deep_search(Bag("shiny gold"), bags)
    print(count)

def deep_search(search, bags):
    count = 0
    for b in bags:
        if search in b.contains:
            count += 1
            count += deep_search(b, bags)
    return count