import typing
import re
import unittest
import itertools

class Range:
    def __init__(self, start, end) -> None:
        self.start = start
        self.end = end

    def contains(self, value):
        return value >= self.start and value <= self.end

class Rule:

    RE = re.compile("(.+?): (\d+)-(\d+) or (\d+)-(\d+)")

    def __init__(self, name, ranges) -> None:
        self.name = name
        self.ranges = ranges

    def valid(self, value):
        for range in self.ranges:
            if range.contains(value):
                return True
        return False

    def __eq__(self, o: object) -> bool:
        return o != None and self.name == o.name

    def __str__(self) -> str:
        return f"{self.name}"

    def __repr__(self) -> str:
        return str(self)

    @staticmethod
    def of(line):
        match = Rule.RE.match(line)
        name = match.group(1)
        f = Rule(name, [])
        for i in range(2, 6, 2):
            f.ranges.append(Range(int(match.group(i)), int(match.group(i+1))))
        return f

class Ticket:
    def __init__(self, fields) -> None:
        self.fields = fields

    def is_possible(self, rules):
        errors = []
        for field in self.fields:
            v = False
            for rule in rules:
                v |= rule.valid(field)
            if not v:
                errors.append(field)
        return errors

    @staticmethod
    def of(line):
        return Ticket([int(x) for x in line.split(',')])


def run(lines: typing.List[str]):
    il = 0
    
    # rules
    rules = []
    while(lines[il] != ''):
        line = lines[il]
        rules.append(Rule.of(line))
        il += 1

    # my ticket
    il += 2
    my_ticket = Ticket.of(lines[il])

    # tickets
    tickets = []
    il += 3 # skip blank line
    while(il < len(lines)):
        tickets.append(Ticket.of(lines[il]))
        il += 1

    errors = []
    valids = []
    for t in tickets:
        error = t.is_possible(rules)
        errors.append(error)
        if not len(error):
            valids.append(t)

    rate = 0
    for error in errors:
        rate += sum(error)
    print(rate)

    possibilities = []
    for i in range(len(rules)):
        possibility = []
        for j, r in enumerate(rules):
            valid = True
            for v in valids:
                valid &= r.valid(v.fields[i])
            if valid:
                possibility.append(r)
        possibilities.append(possibility)

    order = {}
    for j in range(len(rules)):
        to_remove = None
        for i, p in enumerate(possibilities):
            if len(p) == 1:
                to_remove = p[0]
                order[i] = to_remove
                break
        
        for p in possibilities:
            if to_remove in p:
                p.remove(to_remove)

    product = 1
    for i, r in order.items():
        if r.name.startswith("departure"):
            product *= my_ticket.fields[i]
    print(product)

class Test(unittest.TestCase):
    def test_rules(self):
        r = Rule.of("test: 0-5 or 12-63")
        self.assertTrue(r.valid(5))
        self.assertTrue(r.valid(52))
        self.assertFalse(r.valid(7))
        self.assertFalse(r.valid(102))

if __name__ == "__main__":
    unittest.main()