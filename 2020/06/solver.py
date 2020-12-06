import typing
import unittest

def run(lines: typing.List[str]):
    result = 0
    group_answers = [1 for x in range(26)]
    for l in lines:
        if not l:
            result += sum(group_answers)
            print(group_answers)
            group_answers = [1 for x in range(26)]
            continue

        answer = [0 for x in range(26)]
        for c in l:
            answer[ord(c)-ord('a')] = 1
        for i in range(len(group_answers)):
            group_answers[i] &= answer[i]
        
            
    result += sum(group_answers)
    print(result)
        

class Test(unittest.TestCase):
    def test_a(self):
        self.assertEqual(1, 1 & 1)

if __name__ == "__main__":
    unittest.main()