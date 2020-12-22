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
        winner = None
        played = set()
        jx = ix
        while self.deck_0.size() > 0 and self.deck_1.size() > 0:
            if str(self) in played:
                return 0, jx+1

            played.add(str(self))

            print()
            print(f"-- Round {round} (Game {ix}) --")

            print(f"Player 1's deck: {self.deck_0.cards}")
            print(f"Player 2's deck: {self.deck_1.cards}")

            c_0 = self.deck_0.pop()
            print(f"Player 1 plays: {c_0}")
            c_1 = self.deck_1.pop()
            print(f"Player 2 plays: {c_1}")

            if self.deck_0.size() >= c_0 and self.deck_1.size() >= c_1:
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

    def __str__(self) -> str:
        return str(self.deck_0) + "|" + str(self.deck_1)

class Deck:
    def __init__(self, cards) -> None:
        self.cards = cards

    def pop(self):
        return self.cards.pop(0)

    def append(self, card):
        self.cards.append(card)

    def size(self):
        return len(self.cards)

    def __str__(self) -> str:
        return str(self.cards)


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