import math

def calculateFuel(weight):
    return math.floor(weight / 3) - 2

total = 0
with open('2019/1/input.txt', 'r') as f:
    line = f.readline()
    while (line):
        fuel = calculateFuel(int(line))
        total += fuel
        print("weight: {}; fuel: {}; total: {}".format(int(line), fuel, total))

        fuel = calculateFuel(fuel)
        while (fuel > 0):
            total += fuel
            print("weight: {}; fuel: {}; total: {}".format(int(line), fuel, total))
            fuel = calculateFuel(fuel)

        line = f.readline()

print(total)