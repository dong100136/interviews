import random

N  = 1000000
state = 0
count = 0

for _ in range(N):
    i = 0
    while True:
        i+=1
        if random.random() > 0.5:
            state += 1
            if state == 2:
                count += i
                break
        else:
            state = 0

print(count / N)