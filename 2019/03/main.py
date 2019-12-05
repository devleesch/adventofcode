import sys
sys.path.append('2019/lib/')
import file

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __hash__(self):
        return hash((self.x, self.y))
    
    def __str__(self):
        return "({}, {})".format(self.x, self.y)

    def distance(self):
        return abs(self.x) + abs(self.y)

def getPoints(line):
    points = []
    previousPoint = Point(0, 0)
    for move in line.split(','):
        point = None

        direction = move[0]
        distance = int(move[1:])

        for i in range(1, distance+1):
            if(direction == 'U'):
                point = Point(previousPoint.x, previousPoint.y + i)
            elif(direction == 'D'):
                point = Point(previousPoint.x, previousPoint.y - i)
            elif(direction == 'L'):
                point = Point(previousPoint.x - i, previousPoint.y)
            elif(direction == 'R'):
                point = Point(previousPoint.x + i, previousPoint.y)

            #print("{}: {}".format(move, point.str()))
            points.append(point)
        previousPoint = point

    return points

def intersection(lst1, lst2): 
    return list(set(lst1) & set(lst2))

def main():
    print("start")
    lines = file.read("2019/03/input.txt")

    points = []
    for l in lines:
        points.append(getPoints(l))
    
    minDistance = sys.maxsize
    minSteps = sys.maxsize
    for cross in intersection(points[0], points[1]):
        setps1 = points[0].index(cross) + 1
        steps2 = points[1].index(cross) + 1
        print("intersection: {} steps: {} + {}".format(cross, setps1, steps2))
        minDistance = min(minDistance, cross.distance())
        minSteps = min(minSteps, setps1 + steps2)

    print("minDistance: {}".format(minDistance))
    print("minSteps: {}".format(minSteps))
    print("end")

main()