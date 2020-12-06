import typing
import unittest

def run(lines: typing.List[str]):
    sum = 0
    group_yes = ""
    for l in lines:
        if not l:
            sum += len(group_yes)
            print(group_yes)
            group_yes = ""
            continue
        for c in l:
            if c not in group_yes:
                group_yes += c
                group_yes = sorted(group_yes)
    sum += len(group_yes)
    print(sum)
        

class Test(unittest.TestCase):
    def test_a(self):
        self.assertEqual('a', 'a')

if __name__ == "__main__":
    unittest.main()