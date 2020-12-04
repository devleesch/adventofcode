import typing


class Document:
    def __init__(self, byr, iyr, eyr, hgt, hcl, ecl, pid, cid = None) -> None:
        self.byr = byr #(Birth Year)
        self.iyr = iyr #(Issue Year)
        self.eyr = eyr #(Expiration Year)
        self.hgt = hgt #(Height)
        self.hcl = hcl #(Hair Color)
        self.ecl = ecl #(Eye Color)
        self.pid = pid #(Passport ID)
        self.cid = cid #(Country ID)

    @staticmethod
    def from(document_str: str):
        return Document()

def run(lines: typing.List[str]):
    document_str = ""
    for line in lines:
        if not len(line):
        document_str += line