#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    int n, m;
    cin >> n >> m;

    vector<int> len(n);
    for (int i = 0; i < n; i++) {
        cin >> len[i];
    }

    sort(len.begin(), len.end());
    cout << len[len.size() - 1] << endl;
    return 0;
}