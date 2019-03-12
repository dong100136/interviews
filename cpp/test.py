mod = 1e9+1

in1 = input()
n, s = in1.split()

n = int(n)
s = int(s)

print(n, s)
myNum = [0]*(s+1)

print(myNum)

myNum[0] = 2
myNum[1] = 1

for i in range(1, n):
    for j in range(s, 0, -1):
        myNum[j] = (myNum[j-1]+(2*myNum[j]) % mod) % mod
        print(i, j, myNum[j])
    myNum[0] = (2*myNum[0]) % mod

print(int(myNum[s]))
