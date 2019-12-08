import sys
sys.path.append('2019/lib')
import file

class Layer:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.pixels = []

    def append(self, pixel):
        self.pixels.append(pixel)

    def getRepartition(self):
        repartition = {}
        repartition[0] = 0
        repartition[1] = 0
        repartition[2] = 0

        for p in self.pixels:
            repartition[p] += 1

        return repartition

class Image:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.layers = []
        self.result = [2] * width * height

    def decode(self, encoded):
        i = 0
        layer = None
        for p in encoded:
            if i % (self.width * self.height) == 0:
                layer = Layer(self.width, self.height)
                self.layers.append(layer)
            layer.append(p)
            i += 1
    
    def checkSum(self):
        min0 = self.layers[0].getRepartition()
        for l in self.layers:
            r = l.getRepartition()
            if(r[0] < min0[0]):
                min0 = r
        return min0[1]*min0[2]

    def flat(self):
        i = 0
        for layer in self.layers:
            for pixel in layer.pixels:
                index = i % (self.width * self.height)
                if self.result[index] == 2:
                    self.result[index] = pixel
                i += 1

    def display(self):
        i = 0
        for p in self.result:
            if i % self.width == 0:
                print()
            if p == 1:
                print('#', end='')
            else:
                print(' ', end='')
            i += 1
        print()

def split(chars):
    return [int(char) for char in chars]

def main():
    datas = split(file.read("2019/08/input1.txt")[0])
    image = Image(25, 6)
    image.decode(datas)
    print(image.checkSum())

    image.flat()
    image.display()

main()