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

def run(lines: typing.List[str]):
    i = 0

    # rules
    rules = dict()
    while(i < len(lines)):
        line = lines[i]

        if not line:
            break
        r = Rule.of(line)
        rules[r.index] = r
        i += 1

    # skip spacer
    i += 1

    # messages
    nb_valid = 0
    while(i < len(lines)):
        line = lines[i]
        #print(line)
        valid, size = rules[0].is_valid(line, 0, rules)
        #print(f"{lines[i]} -> {valid} {size}")
        if valid and len(line) == size:
            nb_valid += 1 if valid else 0
    
        i += 1
    
    print(f"nb_valid={nb_valid}")