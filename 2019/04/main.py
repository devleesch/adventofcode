import sys
import time

def splitDigit(digit):
    digits = []
    for char in str(digit):
        digits.append(int(char))
    return digits

def neverDecrease(digit):
    previous = -sys.maxsize
    for i in digit:
        if(i < previous):
            return False
        previous = i
    return True

def twoAdjacentDigitAreTheSame(digit):
    previous = None
    for i in digit:
        if(previous != None and i == previous):
            return True
        previous = i
    return False

def checkRepartition(digit):
    repartition = [0]*10
    for i in digit:
        repartition[i] += 1
    for r in repartition:
        if(r == 2):
            return True
    return False

def checkValidity(digit):
    return neverDecrease(digit) \
        and checkRepartition(digit) \
        and twoAdjacentDigitAreTheSame(digit)

def main():
    print("start")

    count = 0
    for i in range(240920,789858):
        digit = splitDigit(i)
        if checkValidity(digit):
            #print(i)
            count += 1

    print("count: {}".format(count))        
    print("end")

def test():
    digit = splitDigit(111110)
    print(neverDecrease(digit))

start = time.time()
main()
print("execution: {}".format(time.time() - start))
