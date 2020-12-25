import typing


class Device:
    def __init__(self, public_key) -> None:
        self.public_key = public_key
        self.loop_size = 0
        self.subject_number = 7
        self.value = 1

    def loop(self):
        while self.value != self.public_key:
            self.loop_size += 1
            self.value *= self.subject_number
            self.value %= 20201227

    def __str__(self) -> str:
        return f"{self.loop_size} - {self.value}"

def run(lines: typing.List[str]):
    card = Device(5764801)
    card.loop()
    print(card)

    door = Device(17807724)
    door.loop()
    print(door)

    
    