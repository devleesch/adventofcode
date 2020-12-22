import typing

class Game:
    def __init__(self, deck_0, deck_1) -> None:
        self.deck_0 = deck_0
        self.deck_1 = deck_1

    def play(self):
        while self.deck_0.size() > 0 and self.deck_1.size() > 0:
            c_0 = self.deck_0.pop()
            c_1 = self.deck_1.pop()

            if c_0 > c_1:
                self.deck_0.append(c_0)
                self.deck_0.append(c_1)
            else:
                self.deck_1.append(c_1)
                self. deck_1.append(c_0)
        return 0 if self.deck_1.size() == 0 else 1

    def play_recurse(self, ix):
        round = 1
        winner = 0
        played = set()
        jx = ix
        while self.deck_0.size() > 0 and self.deck_1.size() > 0:
            if self in played:
                return 0, jx

            played.add(self.copy())

            print()
            print(f"-- Round {round} (Game {ix}) --")

            print(f"Player 1's deck: {self.deck_0.cards}")
            print(f"Player 2's deck: {self.deck_1.cards}")

            c_0 = self.deck_0.pop()
            print(f"Player 1 plays: {c_0}")
            c_1 = self.deck_1.pop()
            print(f"Player 2 plays: {c_1}")

            winner = 0
            if c_0 <= self.deck_0.size() and c_1 <= self.deck_1.size():
                print("Playing a sub-game to determine the winner...")
                sub_game = Game(Deck(self.deck_0.cards[0:c_0]), Deck(self.deck_1.cards[0:c_1]))
                jx += 1
                winner, jx = sub_game.play_recurse(jx)
            else:
                winner = 0 if c_0 > c_1 else 1
                            
            if winner == 0:
                print(f"Player 1 wins round {round} of game {ix}!")
                self.deck_0.append(c_0)
                self.deck_0.append(c_1)
            else:
                print(f"Player 2 wins round {round} of game {ix}!")
                self.deck_1.append(c_1)
                self.deck_1.append(c_0)
            round += 1

        print(f"The winner of game {ix} is player {winner + 1}!")
        return winner, jx

    def copy(self):
        return Game(Deck(self.deck_0.cards.copy()), Deck(self.deck_1.cards.copy()))

    def __eq__(self, o: object) -> bool:
        if o == None:
            return False
        return self.deck_0 == o.deck_0 and self.deck_1 == o.deck_1

    def __hash__(self) -> int:
        hash = self.deck_0.__hash__()
        hash += self.deck_1.__hash__()
        return hash

class Deck:
    def __init__(self, cards) -> None:
        self.cards = cards

    def pop(self):
        return self.cards.pop(0)

    def append(self, card):
        self.cards.append(card)

    def size(self):
        return len(self.cards)

    def __eq__(self, o: object) -> bool:
        if o == None:
            return False
        
        if self.size() != o.size():
            return False

        for i in range(self.size()):
            if self.cards[i] != o.cards[i]:
                return False
        return True

    def __hash__(self) -> int:
        hash = 0
        for i in range(self.size()):
            hash += self.cards[i] * self.size() - i
        return hash


def read_cards(lines, i, cards):
    while i < len(lines) and lines[i] != '':
        cards.append(int(lines[i]))
        i += 1
    return i


def result(winner):
    sum = 0
    print(winner.cards)
    while(len(winner.cards)):
        l = len(winner.cards)
        v = winner.pop()
        sum += l * v
    print(f"sum={sum}")


def part_a(decks):
    g = Game(decks[0], decks[1])
    winner = g.play()
    result(decks[winner])


def part_b(decks):
    g = Game(decks[0], decks[1])
    winner = g.play_recurse(1)

    result(g.deck_0 if winner == 0 else g.deck_1)
    

def run(lines: typing.List[str]):
    decks = []

    i = 1 # skip player name
    cards = []
    i += read_cards(lines, i, cards)
    decks.append(Deck(cards))
    

    i += 1 # skip player name
    cards = []
    i += read_cards(lines, i, cards)
    decks.append(Deck(cards))

    #part_a(decks)
    part_b(decks)