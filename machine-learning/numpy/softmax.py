import numpy as np

x = np.random.rand(10, 3)*1000
print(x)

print(
    """
----------------------------------------
""")


def softmax_simple(x):
    tmp = np.exp(x)
    return tmp / np.sum(tmp, axis=1, keepdims=True)


def softmax_improve(x):
    tmp = np.exp(x)-np.max(x, axis=1, keepdims=True)
    return tmp / np.sum(tmp, axis=1, keepdims=True)


print(softmax_simple(x))

print(
    """
----------------------------------------
""")

print(softmax_improve(x))
