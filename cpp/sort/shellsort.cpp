#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void shell_sort(vector<int> &nums) {
    int n = nums.size();
    for (int d = n / 2; d > 0; d = d / 2) {
        for (int i = d ; i < n; i++) {
            int tmp = nums[i];
            int j = i;
            while (j >= d && nums[j - d] > nums[j]) {
                nums[j] = nums[j - d];
                j = j - d;
            }
            nums[j] = tmp;
        }
    }
}

int main() {
    int tmp[] = {7, 4, 8, 21, 21, 3, 4, 5};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    shell_sort(nums);
    for (auto t : nums) {
        cout << t << ",";
    }
    cout << endl;
}