import sys
sys.path.append('2019/lib')
import file

class Object:
    def __init__(self, name):
        self.name = name
        self.parent = None
        self.children = []
    
    def __str__(self):
        return "parent: {}; name: {}".format(self.parent.name, self.name)

    def addChild(self, child):
        child.parent = self
        self.children.append(child)

def getOrCreateObject(objects, name):
    obj = objects.get(name)
    if obj == None:
        obj = Object(name)
        objects[name] = obj
    return obj

def countOrbits(obj):
    count = 0
    while(obj.parent != None):
        count += 1
        obj = obj.parent
    return count

def getParents(obj):
    parents = []
    while(obj.parent != None):
        parents.append(obj.parent)
        obj = obj.parent
    return set(parents)

def main():
    objects = {}
    datas = file.read("2019/06/input.txt")
    for o in datas:
        pName, name = o.split(')')
        getOrCreateObject(objects, pName)
        getOrCreateObject(objects, name)

    for o in datas:
        pName, name = o.split(')')
        parent = getOrCreateObject(objects, pName)
        parent.addChild(getOrCreateObject(objects, name))
    
    count = 0
    for o in objects:
        count += countOrbits(objects.get(o))

    print("count: {}".format(count))

    you = getOrCreateObject(objects, "YOU")
    youParents = getParents(you)
    san = getOrCreateObject(objects, "SAN")
    sanParents = getParents(san)
    print(len(youParents ^ sanParents))

main()