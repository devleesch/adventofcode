import typing
import re

class Dish:
    def __init__(self, ingredients: set, allergens: set) -> None:
        self.ingredients = ingredients
        self.allergens = allergens


def all_found(map):
    for i in map:
        if len(map[i]) > 1:
            return False
    return True


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
    by_allergens_possibilities = {}
    for allergen, dish in by_allergens.items():
        r = dish[0].ingredients
        for d in dish:
            r = d.ingredients & r
        can_contains.update(r)
        by_allergens_possibilities[allergen] = r
    
    count = 0
    for d in dishes:
        for ingredient in d.ingredients:
            if not ingredient in can_contains:
                count += 1
    print(f"count={count}")

    while not all_found(by_allergens_possibilities):
        for a in by_allergens_possibilities:
            if len(by_allergens_possibilities[a]) == 1:
                to_remove = list(by_allergens_possibilities[a])[0]
                for o in by_allergens_possibilities:
                    if o != a:
                        if to_remove in by_allergens_possibilities[o]:
                            by_allergens_possibilities[o].remove(to_remove)
    
    print(by_allergens_possibilities)
    result = ""
    for k in sorted(by_allergens_possibilities.keys()):
        result += ","+list(by_allergens_possibilities[k])[0]
    print(result[1:])
