cur, i = map(int, input().split())
minleft = 10009
s = 0
for _ in range(i):
    a, b = map(int, input().split())
    s += b
    minleft = min(minleft, a - b)
print(max(s + minleft - cur, 0))
