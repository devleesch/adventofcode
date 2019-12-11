import sys
sys.path.append('2019/lib')
import file

class Asteroid:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __eq__(self, value):
        return self.x == value.x and self.y == value.y

    def __str__(self):
        return "{};{}".format(self.x, self.y)

class Slope:
    def __init__(self):
        self.value = None
        self.signX = None
        self.signY = None

    def __eq__(self, value):
        return self.value == value.value \
            and self.signX == value.signX \
            and self.signY == value.signY

def slope(a: Asteroid, b: Asteroid):
    s = Slope()
    dX = b.x - a.x
    s.signX = 1 if dX >=0 else -1

    dY = b.y - a.y
    s.signY = 1 if dY >= 0 else -1

    if dX == 0:
        s.value = sys.maxsize if dY >= 0 else -sys.maxsize
    else:
        s.value = dY / dX
    return s

def countVisible(station: Asteroid, asteroids: list):
    visibles = []
    slopes = []
    asteroids.remove(station)
    for asteroid in asteroids:
        s = slope(station, asteroid)
        if not s in slopes:
            slopes.append(s)
            visibles.append(asteroid)
    return len(visibles)

def main():
    lines = file.read("2019/10/input.txt")

    asteroids = []
    y = 0
    for line in lines:
        x = 0
        for o in line:
            if o == "#":
                asteroids.append(Asteroid(x, y))
            x +=1
        y += 1

    maxAsteroids = 0
    for station in asteroids:
        maxAsteroids = max(maxAsteroids, countVisible(station, asteroids.copy()))
    print("max: {}".format(maxAsteroids))

main()