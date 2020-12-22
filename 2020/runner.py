import helper
import importlib
import time

from helper import elapsed

day = 21
solver = 'solver'
file = 'input.txt'

# start time to track execution time
start = time.monotonic()

# import puzzle solver
mod = importlib.import_module(f'{day:02d}.{solver}')
# read input of the file
lines = helper.readlines(f'{day:02d}/{file}')
# run module solver with input file line by line
mod.run(lines)

# end time to compute elapsed time
end = time.monotonic()

helper.elapsed(end - start)