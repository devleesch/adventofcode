import sys
sys.path.append('2019/lib')
import file
import math

class Asteroid:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __eq__(self, value):
        return self.x == value.x and self.y == value.y

    def __str__(self):
        return "{};{}".format(self.x, self.y)

    def distance(self, other):
        return math.sqrt(math.pow(other.x - self.x, 2) + math.pow(other.y - self.y, 2))

def slope(a: Asteroid, b: Asteroid):
    dX = b.x - a.x
    dY = b.y - a.y
    return normalDegrees(math.atan2(dY, dX))

def normalDegrees(atan):
    normal = math.degrees(atan + math.pi / 2)
    while(normal < 0):
        normal += 360
    return normal

def visibles(station: Asteroid, asteroids: list):
    slopes = {}
    asteroids.remove(station)
    for asteroid in asteroids:
        s = slope(station, asteroid)
        if not s in slopes:
            slopes[s] = [asteroid]
        else:
            slopes[s].append(asteroid)
    return slopes

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

    maxVisibles = 0
    bestStation = None
    bestRepartion = None
    for station in asteroids:
        repartition = visibles(station, asteroids.copy())
        nbVisible = len(repartition)
        if nbVisible > maxVisibles:
            maxVisibles = nbVisible
            bestStation = station
            bestRepartion = repartition
    print("station: {}, max: {}".format(station, maxVisibles))

    keys = list(bestRepartion.keys())
    keys.sort()
    for a in bestRepartion.values():
        a.sort(key = station.distance)

    maxSubLength = 0
    for k in keys:
        maxSubLength = max(maxSubLength, len(bestRepartion[k]))
    
    asteroid = 1
    for i in range(maxSubLength):
        for k in keys:
            if asteroid == 200:
                print(bestRepartion[k][i])
            asteroid += 1

    print("end")
    

main()