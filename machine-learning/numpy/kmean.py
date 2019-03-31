import numpy as np
import time
from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
import seaborn as sns
np.random.seed(666)

sns.set()


class KMean:
    def __init__(self, train_x, k):
        self.x = train_x
        self.k = k

    def distance(self, x, p):
        n = x.shape[0]
        dist = np.tile(p, (n, 1)) - x
        dist = np.sum(dist ** 2, axis=1)
        dist = dist ** 0.5
        return dist

    def train(self, max_iter=20):
        self.centers = self.x[: self.k]
        n = self.x.shape[0]

        for iterater in range(max_iter):
            dist = np.zeros((n, self.k))
            for i, center in enumerate(self.centers):
                dist[:, i] = self.distance(self.x, center)

            predict = np.argmin(dist, axis=1)

            for i in range(self.k):
                self.centers[i] = np.mean(self.x[predict == i], axis=0)

            if iterater % 2 == 0:
                for i in range(self.k):
                    plt.scatter(self.x[predict == i][:, 0],
                                self.x[predict == i][:, 1])
                plt.scatter(self.centers[:, 0], self.centers[:, 1])
                plt.show()

    def get_centers(self):
        return self.centers


if __name__ == '__main__':
    n = 1000
    centers = np.array([[3, 3], [4, 3], [6, 5]])
    data_x = np.zeros([3*n, 2])

    for i, center in enumerate(centers):
        data_x[i*n:(i+1)*n] = center+np.random.randn(n, 2)

    np.random.shuffle(data_x)

    kmean = KMean(data_x, 10)
    kmean.train()
