import typing
import re

class Rule:
    RE = re.compile("(\d+): (.+)")

    def __init__(self, index, text: str) -> None:
        self.index = index
        self.text = text
        self.resolved = False
        self.conditions = []

    def is_valid(self, message, i, rules):
        or_valid = False
        j = 0
        for or_c in self.conditions:
            j = 0
            and_valid = True
            for and_c in or_c:
                #print(f"rule={self.text}, c={and_c}, ix={i + j}")
                if and_c.isdigit():
                    r_valid, r_i = rules[int(and_c)].is_valid(message, i + j, rules)
                    and_valid &= r_valid
                    j += r_i
                else:
                    #print(f"c={and_c}, l={message[i + j]}, {and_c == message[i + j]}")
                    if i + j < len(message):
                        and_valid &= and_c == message[i + j]
                    else:
                        and_valid = False
                    j += 1
            or_valid |= and_valid
            #print()
        return or_valid, j

    @staticmethod
    def of(line: str):
        match = Rule.RE.match(line)
        r = Rule(int(match.group(1)), match.group(2))
        conditions = r.text.replace("\"", "").split("|")
        for o in conditions:
            r.conditions.append(o.split())
        return r

def check_messages(index, messages, rules, ignore_length):
    nb_valid = 0
    for line in messages:
        valid, size = rules[index].is_valid(line, 0, rules)

        if valid and (len(line) == size or ignore_length):
            nb_valid += 1 if valid else 0
            print(line, size, valid)
        
    print(f"nb_valid={nb_valid}")


def check_part2(messages, rules):
    nb_valid = 0
    for line in messages:
        print(line)
        i = 0

        valid = True
        nb_42 = 0
        while valid and len(line[i:]):
            valid, size = rules[42].is_valid(line[i:], 0, rules)
            if valid:
                nb_42 += 1
                i += size
                print(42)
                #print(line[:i], line[i:], line)

        if i < len(line):
            valid = True
            while valid and i < len(line) and nb_42 > 1:
                valid, size = rules[31].is_valid(line[i:], 0, rules)
                if valid:
                    nb_42 -= 1
                    i += size
                    print(31)
                    #print(line[:i], line[i:], line)

            valid &= len(line) == i and nb_42 >= 1
            print(valid, line)
            print()
            if valid:
                nb_valid += 1
                print(line)
    print(nb_valid)
        
        


def run(lines: typing.List[str]):
    i = 0

    # rules
    rules = dict()
    while i < len(lines):
        line = lines[i]

        if not line:
            break
        r = Rule.of(line)
        rules[r.index] = r
        i += 1

    # skip spacer
    i += 1

    # messages
    messages = []
    while i < len(lines):
        line = lines[i]
        messages.append(line)
        i += 1

    #check_messages(0, messages, rules, True)
    #check_messages(42, messages, rules, True)

    check_part2(messages, rules)