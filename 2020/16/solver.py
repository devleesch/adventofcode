import typing
import re
import unittest


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

    def valid(self, rules):
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


def permutations(values: set, current: set):
    diff = values - current
    while(len(diff)):
        for v in diff:
            current.add(v)
    return current


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

    print(my_ticket.valid(rules))

    # tickets
    tickets = []
    il += 3 # skip blank line
    while(il < len(lines)):
        tickets.append(Ticket.of(lines[il]))
        il += 1

    errors = []
    valids = []
    for t in tickets:
        error = t.valid(rules)
        errors.append(error)
        if not len(error):
            valids.append(t)

    rate = 0
    for error in errors:
        rate += sum(error)
    print(rate)

    values = set([x for x in range(len(valids[0].fields))])
    possibilities = permutations(values, set())
    pass


class Test(unittest.TestCase):
    def test_rules(self):
        r = Rule.of("test: 0-5 or 12-63")
        self.assertTrue(r.valid(5))
        self.assertTrue(r.valid(52))
        self.assertFalse(r.valid(7))
        self.assertFalse(r.valid(102))

if __name__ == "__main__":
    unittest.main()