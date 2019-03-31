import math


def evolution(n, k, min_delta=1e-10):
    def f(x): return (math.pow(x, k)-n)/(k*math.pow(x, k-1))

    x = n
    tmp = f(x)
    while abs(tmp) > min_delta:
        x = x - tmp
        tmp = f(x)

    return x


if __name__ == "__main__":
    print(evolution(3, 2))
    print(3 ** 0.5)
    print(evolution(10, 5))
    print(10**0.2)
