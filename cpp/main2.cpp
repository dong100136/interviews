#include <stdio.h>
#include <string.h>
#include <iostream>
#include <vector>
using namespace std;

int search(int *nums, int i, int j) {
    int cnt1 = 0;
    while (i < j) {
        if (nums[i] != nums[j]) {
            cnt1++;
            j--;
        } else {
            i++;
            j--;
        }
    }

    cnt1++;

    return cnt1;
}
// Read only region start
int points(int input1, int input2[]) {
    // Read only region end
    // Write code and remove the below exception.
    int n = input1;
    vector<vector<int>> dp(n + 1, vector<int>(n + 1));
    for (int i = 0; i < input1; i++) {
        dp[i][i] = 1;
    }

    for (int k = 1; k < n; k++) {
        for (int i = 0; i < n - k; i++) {
            dp[i][i + k] = search(input2, i, i + k);

            for (int j = i; j < i + k; j++) {
                dp[i][i + k] = min(dp[i][j] + dp[j + 1][i + k], dp[i][i + k]);
            }
        }
    }
    return dp[0][n - 1];
}

int main() {
    int nums[] = {1, 1, 2, 3, 1, 3};
    cout << points(6, nums) << endl;

    int nums2[] = {1, 2, 1, 1, 3, 1};
    cout << points(6, nums2) << endl;

    int nums3[] = {1, 2, 3, 4, 9, 3, 2, 1};
    cout << points(8, nums3) << endl;
    return 0;
}