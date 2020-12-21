import typing
import re


def run(lines: typing.List[str]):
    RE = re.compile("(.+?) \(contains (.+?)\)")

    allergens_map = {}
    for line in lines:
        match = RE.match(line)
        ingredients = match.group(1).split()
        allergens = match.group(2).replace(" ", "").split(',')

        for ingredient in ingredients:
            for allergen in allergens:
                if not allergen in allergens_map:
                    allergens_map[allergen] = set()
                allergens_map[allergen].add(ingredient)

    print(allergens_map)