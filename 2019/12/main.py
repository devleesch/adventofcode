import sys
sys.path.append('2019/lib')
import file

class Position:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z

class Velocity:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z