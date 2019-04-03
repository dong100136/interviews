#include <stdio.h>
#include <string.h>
#include <iostream>
using namespace std;

void search(int n, int target, int maxStep, int i, int k, int &count) {
    if (k > maxStep) return;
    if (k != 0 && i == target) count++;

    cout << i << "," << k << endl;

    for (int j = 1; j <= n; ++j) {
        if ((i < j && j % i == 0) || (i > j && i % j == 0)) {
            search(n, target, maxStep, j, k + 1, count);
        }
    }
}
// Read only region start

int maxCircles(int input1, int input2, int input3) {
    // Read only region end
    // Write code and remove the below exception.
    int count = 0;
    search(input1, input2, input3, input2, 0, count);
    return count;
}

int main() {
    int rs = maxCircles(3, 2, 4);
    cout << rs << endl;
    return 0;
}
