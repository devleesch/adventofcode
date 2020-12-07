import typing
import re


class Bag:
    def __init__(self, color) -> None:
        self.color = color
        self.contains = []

def run(lines: typing.List[str]):
    for l in lines:
        parse(l)

def parse(line: str):
    matches = re.match("(?P<bag>.+) bags contain (?P<contain>.+)*", line)
    contains = re.findall("(\d) (.+?) bags?", matches.group('contain'))
    print(matches.group('bag'), contains)