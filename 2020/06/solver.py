import typing
import unittest


def run(lines: typing.List[str]):
    for l in lines:
        print(l)

class Test(unittest.TestCase):
    def test_a(self):
        self.assertEqual('a', 'a')

if __name__ == "__main__":
    unittest.main()