import unittest
import re
import sys
sys.path.append('2019/lib')
import file
import enum

class Test(unittest.TestCase):
    def test_newStack(self):
        deck = Deck(10)
        deck.newStack()
        self.assertEqual(deck.cards, [9, 8, 7, 6, 5, 4, 3, 2, 1, 0])
    
    def test_cut(self):
        deck = Deck(10)
        deck.cut(3)
        self.assertEqual(deck.cards, [3, 4, 5, 6, 7, 8, 9, 0, 1, 2])

    def test_cut_negative(self):
        deck = Deck(10)
        deck.cut(-4)
        self.assertEqual(deck.cards, [6, 7, 8, 9, 0, 1, 2, 3, 4, 5])

    def test_cut_6(self):
        deck = Deck(10)
        deck.cut(6)
        self.assertEqual(deck.cards, [6, 7, 8, 9, 0, 1, 2, 3, 4, 5])

    def test_increment(self):
        deck = Deck(10)
        deck.increment(3)
        self.assertEqual(deck.cards, [0, 7, 4, 1, 8, 5, 2, 9, 6, 3])

    def test_increment_7(self):
        deck = Deck(10)
        deck.increment(7)
        self.assertEqual(deck.cards, [0, 3, 6, 9, 2, 5, 8, 1, 4, 7])

    def test_1(self):
        deck = Deck(10)
        deck.increment(7)
        deck.newStack()
        deck.newStack()
        self.assertEqual(deck.cards, [0, 3, 6, 9, 2, 5, 8, 1, 4, 7])

    def test_2(self):
        deck = Deck(10)
        deck.cut(6)
        deck.increment(7)
        deck.newStack()
        self.assertEqual(deck.cards, [3, 0, 7, 4, 1, 8, 5, 2, 9, 6])

    def test_3(self):
        deck = Deck(10)
        deck.increment(7)
        deck.increment(9)
        deck.cut(-2)
        self.assertEqual(deck.cards, [6, 3, 0, 7, 4, 1, 8, 5, 2, 9])

class Pattern(enum.Enum):
    def __init__(self, code, pattern, action):
        self.code = code
        self.pattern = pattern
        self.action = action

    CUT = 0, "cut ([\-0-9]*)", lambda deck, match: deck.cut(match)
    NEW_STACK = 1, "deal into new stack", lambda deck: deck.newStack()
    INCREMENT = 2, "deal with increment ([0-9]*)", lambda deck, match: deck.increment(match)

class Deck:
    def __init__(self, size):
        self.cards = [i for i in range(size)]
    
    def newStack(self):
        self.cards.reverse()

    def cut(self, n):
        if n < 0:
            n = len(self.cards) + n

        temp = list(self.cards)
        self.cards = temp[n:] + temp[:n]

    def increment(self, n):
        temp = list(self.cards)
        for i in range(len(temp)):
            index = i * n % len(self.cards)
            self.cards[index] = temp[i]

def main():
    f = file.read("2019/22/input.txt")
    deck = Deck(10007)
    start = Deck(10007)
    patterns = list(Pattern)

    steps = 0
    while(deck.cards != start.cards):
        for line in f:
            for pattern in patterns:
                match = re.search(pattern.pattern, line)
                if match:
                    print(pattern.pattern)
                    groups = match.groups()
                    if len(groups) > 0:
                        pattern.action(deck, int(groups[0]))
                    else:
                        pattern.action(deck)
        steps += 1
        print(steps)
main()
#unittest.main()