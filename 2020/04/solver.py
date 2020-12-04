import typing
import unittest


class Document:
    PID = [c for c in "0123456789"]
    HCL = [c for c in "0123456789abcdef"]

    def __init__(self) -> None:
        self.byr = None #(Birth Year)
        self.iyr = None #(Issue Year)
        self.eyr = None #(Expiration Year)
        self.hgt = None #(Height)
        self.hcl = None #(Hair Color)
        self.ecl = None #(Eye Color)
        self.pid = None #(Passport ID)
        self.cid = None #(Country ID)

    def is_valid(self, VALIDATORS):
        valid = True
        for (key, value) in self.__dict__.items():
            if key in VALIDATORS:
                validator = VALIDATORS[key]
                if validator["method"] == Document.is_valid_int:
                    valid &= validator["method"](value, validator["min"], validator["max"])
                elif validator["method"] == Document.is_valid_hgt:
                    valid &= validator["method"](value)
                elif validator["method"] == Document.is_valid_ecl:
                    valid &= validator["method"](value, validator["colors"])
                else:
                    valid &= validator["method"](value)
        return valid

    @staticmethod
    def is_valid_int(input, min, max):
        if input is None:
            return False
        value = int(input)
        return value >= min and value <= max

    @staticmethod
    def is_valid_hgt(value):
        if value is None:
            return False
        
        try:
            size = int(value[0:len(value)-2])
            unit = value[-2:]
            if unit == "cm":
                return Document.is_valid_int(size, 150, 193)
            elif unit == "in":
                return Document.is_valid_int(size, 59, 76)
            else:
                return False
        except:
            return False
        
    @staticmethod
    def is_valid_hcl(value):
        if value is None:
            return False

        if len(value) != 7:
            return False

        for i in range(len(value)):
            if i == 0 and value[i] != '#':
                return False
            elif i != 0 and not value[i] in Document.HCL:
                return False
        return True

    @staticmethod
    def is_valid_ecl(value, colors):
        return value in colors

    @staticmethod
    def is_valid_pid(value):
        if value is None:
            return False

        if len(value) != 9:
            return False
        
        for c in value:
            if not c in Document.PID:
                return False

        return True

    @staticmethod
    def from_str(document_str: str):
        document = Document()
        document_str = document_str.strip()
        data = document_str.split()
        for d in data:
            key, value = d.split(":")
            document.__dict__[key] = value
        return document

VALIDATORS = {
    'byr': {
        'method': Document.is_valid_int,
        'min': 1920,
        'max': 2002
    },
    'iyr': {
        'method': Document.is_valid_int,
        'min': 2010,
        'max': 2020
    },
    'eyr': {
        'method': Document.is_valid_int,
        'min': 2020,
        'max': 2030
    },
    'hgt': {
        'method': Document.is_valid_hgt
    },
    'hcl': {
        'method': Document.is_valid_hcl
    },
    'ecl': {
        'method': Document.is_valid_ecl,
        'colors': ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']
    },
    'pid': {
        'method': Document.is_valid_pid
    }
}

def run(lines: typing.List[str]):
    document_str = ""
    documents = []
    for line in lines:
        if not len(line):
            documents.append(Document.from_str(document_str))
            document_str = ""
        else:
            document_str += line + " "
    documents.append(Document.from_str(document_str))

    nb_valid = 0
    for d in documents:
        nb_valid += 1 if d.is_valid(VALIDATORS) else 0
    print(nb_valid)


class Test(unittest.TestCase):
    def test_int(self):
        self.assertTrue(Document.is_valid_int("2002", 1920, 2002))
        self.assertFalse(Document.is_valid_int("2003", 1920, 2002))
        self.assertFalse(Document.is_valid_int("1919", 1920, 2002))
        self.assertFalse(Document.is_valid_int("1999", 2010, 2020))

    def test_hgt(self):
        self.assertTrue(Document.is_valid_hgt("60in"))
        self.assertTrue(Document.is_valid_hgt("190cm"))
        self.assertFalse(Document.is_valid_hgt("190in"))
        self.assertFalse(Document.is_valid_hgt("190"))

    def test_hcl(self):
        self.assertTrue(Document.is_valid_hcl("#123abc"))
        self.assertFalse(Document.is_valid_hcl("#123abz"))
        self.assertFalse(Document.is_valid_hcl("123abc"))
        self.assertFalse(Document.is_valid_hcl("1123abc"))
        self.assertFalse(Document.is_valid_hcl("#1123abc1"))

    def test_ecl(self):
        self.assertTrue(Document.is_valid_ecl("brn", VALIDATORS["ecl"]["colors"]))
        self.assertFalse(Document.is_valid_ecl("wat", VALIDATORS["ecl"]["colors"]))

    def test_pid(self):
        self.assertTrue(Document.is_valid_pid("012345678"))
        self.assertFalse(Document.is_valid_pid("0123456789"))

if __name__ == '__main__':
    unittest.main()