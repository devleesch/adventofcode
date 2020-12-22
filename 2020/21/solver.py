import typing
import re

class Dish:
    def __init__(self, ingredients: set, allergens: set) -> None:
        self.ingredients = ingredients
        self.allergens = allergens


def run(lines: typing.List[str]):
    RE = re.compile("(.+?) \(contains (.+?)\)")

    dishes = []
    by_allergens = {}
    by_ingredients = {}
    for line in lines:
        match = RE.match(line)
        d = Dish(set(match.group(1).split()), set(match.group(2).replace(" ", "").split(',')))
        dishes.append(d)

        for i in d.ingredients:
            if not i in by_ingredients:
                by_ingredients[i] = []
            by_ingredients[i].append(d)

        for a in d.allergens:
            if not a in by_allergens:
                by_allergens[a] = []
            by_allergens[a].append(d)

    can_contains = set()
    for allergen, dish in by_allergens.items():
        r = dish[0].ingredients
        for d in dish:
            r = d.ingredients & r
        can_contains.update(r)
        print(allergen, r)
    
    count = 0
    for d in dishes:
        for ingredient in d.ingredients:
            if not ingredient in can_contains:
                count += 1
    print(f"count={count}")