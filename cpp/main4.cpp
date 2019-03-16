#include <algorithm>
#include <iomanip>
#include <iostream>
#include <queue>
#include <vector>
using namespace std;

const double eps = 1e-5;

int main() {
    int n, m;
    cin >> n >> m;

    int maxLen = 0;
    vector<int> len(n);
    for (int i = 0; i < n; i++) {
        cin >> len[i];
        maxLen = maxLen < len[i] ? len[i] : maxLen;
    }

    double l = 0, r = maxLen;
    while (r - l > eps) {
        double mid = (l + r) / 2;

        int count = 0;
        for (int i = 0; i < n; i++) {
            count += len[i] / mid;
        }
        // cout << "(" << l << "," << r << ")" << count << endl;

        if (count >= m)
            l = mid;
        else
            r = mid;
    }

    cout << setprecision(2) << setiosflags(ios::fixed);
    cout << l << endl;

    return 0;
}