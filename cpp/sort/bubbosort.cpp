#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void bubbo_sort(vector<int> &nums) {
    int n = nums.size();

    for (int i = n - 1; i >= 0; i--) {
        for (int j = 0; j < i; j++) {
            if (nums[j] > nums[j + 1]) {
                swap(nums[j], nums[j + 1]);
            }
        }
    }
}

int main() {
    int tmp[] = {7, 4, 8, 21, 21, 3, 4, 5};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    bubbo_sort(nums);
    for (auto t : nums) {
        cout << t << ",";
    }
    cout << endl;

    return 0;
}