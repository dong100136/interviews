#include <algorithm>
#include <iomanip>
#include <iostream>
#include <queue>
#include <stack>
#include <vector>

using namespace std;

int main() {
    int n;
    cin >> n;

    vector<double> nums;
    double tmp;
    for (int i = 0; i < n; i++) {
        cin >> tmp;
        nums.push_back(tmp);
    }

    int k = 0;
    double p1 = 0;
    double p2 = 0;
    double fail = 1;
    while (k < 100) {
        p1 = p1 + (1 - p2) * nums[k % n];
        k++;
        p2 = p2 + (1 - p2) * nums[k % n];
        k++;
    }
    cout << setiosflags(ios::fixed) << setprecision(4);
    cout << p1 << endl;
    return 0;
}