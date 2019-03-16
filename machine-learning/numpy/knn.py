
import numpy as np
from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix


class KNN:
    def __init__(self, data_x, data_y, K=5):
        self.x = data_x
        self.y = data_y
        self.labels = np.unique(self.y)
        self.K = K

    def get_labels(self):
        return self.labels

    def classify(self, data):
        """
        data: a sample
        """
        n, _ = self.x.shape
        dist = np.tile(data, (n, 1)) - self.x
        dist = dist ** 2
        dist = np.sum(dist, axis=1)
        dist = dist ** 0.5
        distIndex = dist.argsort()

        selected = distIndex[: self.K]
        labels = self.get_labels()
        count = np.zeros(len(labels))
        for i, label in enumerate(labels):
            count[i] = np.sum(self.y[selected] == label)

        return np.argmax(count)


if __name__ == "__main__":
    x, y = load_iris(True)
    np.random.seed(666)
    train_x, test_x, train_y, test_y = train_test_split(x, y)
    print("train data : %d, test data : %d" % (len(train_x), len(test_x)))
    knn = KNN(train_x, train_y, 5)

    test_pred = np.zeros(test_y.shape[0])
    for i, (data, label) in enumerate(zip(test_x, test_y)):
        test_pred[i] = knn.classify(data)

    print(accuracy_score(test_y, test_pred))
    print(confusion_matrix(test_y, test_pred))
