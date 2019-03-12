#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void select_sort(vector<int> &nums) {
    int n = nums.size();
    for (int i = 0; i < n; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (nums[minIndex] > nums[j]) minIndex = j;
        }
        swap(nums[i], nums[minIndex]);
    }
}

int main() {
    int tmp[] = {7, 4, 8, 21, 21, 3, 4, 5};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    select_sort(nums);
    for (auto t : nums) {
        cout << t << ",";
    }
    cout << endl;

    return 0;
}