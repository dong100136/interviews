#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void insert_sort(vector<int> &nums) {
    int n = nums.size();
    for (int i = 1; i < n; i++) {
        int tmp = nums[i];
        int j = i;
        while (j > 0 && nums[j - 1] > tmp) {
            nums[j] = nums[j - 1];
            j--;
        };
        nums[j] = tmp;
    }
}

int main() {
    int tmp[] = {7, 4, 8, 21, 21, 3, 4, 5};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    insert_sort(nums);
    for (auto t : nums) {
        cout << t << ",";
    }
    cout << endl;
}