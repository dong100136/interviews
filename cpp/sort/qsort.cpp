#include <algorithm>
#include <iostream>
#include <random>
#include <vector>

using namespace std;

void quick_sort(vector<int> &nums, int begin, int end) {
    if (begin >= end) return;

    int i = begin, j = end;
    int pivot = nums[i];
    while (i < j) {
        while (i < j && nums[j] >= pivot) j--;
        while (i < j && nums[i] <= pivot) i++;

        if (i < j) swap(nums[i], nums[j]);
    }

    for (int i = begin; i <= end; i++) {
        cout << nums[i] << ",";
    }
    cout << endl;

    swap(nums[begin], nums[i]);

    quick_sort(nums, begin, i - 1);
    quick_sort(nums, i + 1, end);
}

int main() {
    int tmp[] = {3, 2, 1, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 7, 8, 8, 10};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    shuffle(nums.begin(), nums.end(), default_random_engine(666));

    quick_sort(nums, 0, nums.size() - 1);
    for (auto t : nums) {
        cout << t << ",";
    }
    cout << endl;

    return 0;
}