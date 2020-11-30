import helper
import importlib
import time

from helper import elapsed

ep = 1
part = 'a'

start = time.monotonic()

# import puzzle solver
mod = importlib.import_module(f'{ep:02d}.{part}')
# read input of the file
lines = helper.readlines(f'{ep:02d}/{part}.txt')
# run module solver with input file line by line 
mod.run(lines)

end = time.monotonic()

helper.elapsed(end - start)
helper.elapsed(65.9079785)