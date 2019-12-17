import copy

class Position:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z
    
    def __str__(self):
        return "<x={}, y={}, z={}>".format(self.x, self.y, self.z)

    def __eq__(self, other):
        return self.x == other.x \
            and self.y == other.y \
            and self.z == other.z

    def copy(self):
        return copy.deepcopy(self)

class Velocity:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z
    
    def __str__(self):
        return "<x={}, y={}, z={}>".format(self.x, self.y, self.z)

    def __eq__(self, value):
        return self.x == value.x \
            and self.y == value.y \
            and self.z == value.z
    
    def copy(self):
        return copy.deepcopy(self)

class Moon:
    def __init__(self, position):
        self.position = position
        self.velocity = Velocity(0, 0, 0)
        self.startPosition = self.position.copy()
        self.startVelocity = self.velocity.copy()

    def __str__(self):
        return "<position={}, velocity={}>".format(self.position, self.velocity)

    def __eq__(self, other):
        return self.position == other.position

    def computeVelocity(self, other):
        if not self == other:
            self.velocity.x += Moon.comparePosition(self.position.x, other.position.x)
            self.velocity.y += Moon.comparePosition(self.position.y, other.position.y)
            self.velocity.z += Moon.comparePosition(self.position.z, other.position.z)
    
    def applyVelocity(self):
        self.position.x += self.velocity.x
        self.position.y += self.velocity.y
        self.position.z += self.velocity.z

    def potentialEnergy(self):
        return abs(self.position.x) \
            + abs(self.position.y) \
            + abs(self.position.z)

    def kineticEnergy(self):
        return abs(self.velocity.x) \
            + abs(self.velocity.y) \
            + abs(self.velocity.z)

    def totalEnergy(self):
        return self.potentialEnergy() * self.kineticEnergy()

    @staticmethod
    def comparePosition(a, b):
        return -1 if a > b else 1 if a < b else 0

    def revolutionDone(self):
        return self.position == self.startPosition

    def resetX(self):
        return self.position.x == self.startPosition.x

    def resetY(self):
        return self.position.y == self.startPosition.y

    def resetZ(self):
        return self.position.z == self.startPosition.z

def step(moons: list):
    for m1 in moons:
        for m2 in moons:
            m1.computeVelocity(m2)
    for m in moons:
        m.applyVelocity()

def display(moons: list):
    for m in moons:
        print(m)

def isSearch(xRotation, yRotation, zRotation):
    return xRotation == None \
        or yRotation == None \
        or zRotation == None

moons = []
# TEST1
#moons.append(Moon(Position(-1, 0, 2)))
#moons.append(Moon(Position(2, -10, -7)))
#moons.append(Moon(Position(4, -8, 8)))
#moons.append(Moon(Position(3, 5, -1)))

# TEST 2
#moons.append(Moon(Position(-8, -10, 0)))
#moons.append(Moon(Position(5, 5, 10)))
#moons.append(Moon(Position(2, -7, 3)))
#moons.append(Moon(Position(9, -8, -3)))

# INPUT
moons.append(Moon(Position(4, 12, 13)))
moons.append(Moon(Position(-9, 14, -3)))
moons.append(Moon(Position(-7, -1, 2)))
moons.append(Moon(Position(-11, 17, -1)))

xRotation = None
yRotation = None
zRotation = None
i = 0
while(isSearch(xRotation, yRotation, zRotation)):
    #print("step: {}".format(i+1))
    if i % 100000 == 0:
        print(i)
    step(moons)
    #display(moons)

    xReset = True
    yReset = True
    zReset = True
    for m in moons:
        xReset &= m.resetX()
        yReset &= m.resetY()
        zReset &= m.resetZ()

    if xRotation == None and xReset:
        xRotation = i + 1
        print("x reset in {}".format(i + 1))
    if yRotation == None and yReset:
        yRotation = i + 1
        print("y reset in {}".format(i + 1))
    if zRotation == None and zReset:
        zRotation = i + 1
        print("z reset in {}".format(i + 1))
    i += 1


totalEnergy = 0
for m in moons:
    totalEnergy += m.totalEnergy()

print("totalEnergy: {}".format(totalEnergy))