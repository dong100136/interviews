#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    int N;
    cin >> N;
    for (int nn = 0; nn < N; nn++) {
        int n;
        cin >> n;
        vector<int> nums(n);
        vector<int> forward(n + 1, 0);
        vector<int> backward(n + 1, 0);

        int mininum = 10000100;
        int minIndex = 0;
        for (int i = 0; i < n; i++) {
            cin >> nums[i];
            if (nums[i] < mininum) {
                mininum = nums[i];
                minIndex = i;
            }
        }

        vector<int> scores(nums.begin() + minIndex, nums.end());
        scores.insert(scores.end(), nums.begin(), nums.begin() + minIndex + 1);

        // cout << endl << "----";
        // for (auto t : scores) {
        //     cout << t << ",";
        // }
        // cout << endl;

        forward[0] = 1;
        for (int i = 0; i < n; i++) {
            forward[i + 1] = scores[i] < scores[i + 1] ? forward[i] + 1 : 1;
        }

        backward[n] = 1;
        for (int i = n; i > 0; i--) {
            backward[i - 1] = scores[i] < scores[i - 1] ? backward[i] + 1 : 1;
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += max(forward[i], backward[i]);
        }
        cout << sum << endl;
    }
    return 0;
}