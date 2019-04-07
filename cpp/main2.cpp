#include <algorithm>
#include <iostream>
#include <queue>
#include <stack>
#include <vector>

using namespace std;

int search(int num, int k) {
    if (num <= 1) return num;
    if (k > 0) {
        return search((num + 1) / 2, k - 1) + 1;
    } else {
        return num;
    }
}

int main() {
    int n, k;
    cin >> n >> k;
    cout << search(n, k) << endl;
}