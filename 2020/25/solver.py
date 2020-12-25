import typing


class Device:
    def __init__(self, public_key) -> None:
        self.public_key = public_key
        self.loop_size = 0
        self.value = 1

    def find_loop_size(self):
        while self.value != self.public_key:
            self.loop_size += 1
            self.value = Device.loop(self.value, 7)

    def encryption_key(self, public_key):
        ek = 1
        for _ in range(self.loop_size):
            ek = Device.loop(ek, public_key)
        return ek

    def __str__(self) -> str:
        return f"{self.loop_size} - {self.value}"

    @staticmethod
    def loop(value, subject_number):
        value *= subject_number
        value %= 20201227
        return value


def run(lines: typing.List[str]):
    card = Device(9093927)
    card.find_loop_size()
    print(f"card = {card}")

    door = Device(11001876)
    door.find_loop_size()
    print(f"door = {door}")

    encryption_key = card.encryption_key(door.public_key)
    print(f"card encryption key = {encryption_key}")

    encryption_key = door.encryption_key(card.public_key)
    print(f"door encryption key = {encryption_key}")


    
    