#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void quick_sort(vector<int> &nums, int begin, int end) {
    if (begin >= end) return;

    int i = begin, j = end;
    int pivot = nums[i];
    while (i < j) {
        while (i < j && nums[j] >= pivot) j++;
        while (i < j && nums[i] <= pivot) i++;

        if (i < j) swap(nums[i], nums[j]);
    }
    nums[begin] = nums[i];
    nums[i] = pivot;

    quick_sort(nums, begin, i - 1);
    quick_sort(nums, i + 1, end);
}

int main() {
    int tmp[] = {7, 1, 2, 3, 4, 5, 6};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    quick_sort(nums, 0, nums.size());
    for (auto t : nums) {
        cout << *t << ",";
    }
    cout << endl;

    return 0;
}