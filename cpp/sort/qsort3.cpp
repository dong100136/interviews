#include <algorithm>
#include <iostream>
#include <random>
#include <vector>

using namespace std;

void quick_sort3(vector<int> &nums, int begin, int end) {
    if (begin >= end) return;

    int pivot = nums[begin];
    int p = begin + 1, q = end;
    int i = begin + 1, j = end;

    while (i < j) {
        while (i < j && nums[j] >= pivot) {
            if (nums[j] == pivot) {
                swap(nums[j], nums[q]);
                q--;
            }
            j--;
        }

        while (i < j && nums[i] <= pivot) {
            if (nums[i] == pivot) {
                swap(nums[i], nums[p]);
                p++;
            }
            i++;
        }

        if (i < j) swap(nums[i], nums[j]);
    }

    p--;
    while (p >= begin) {
        swap(nums[p--], nums[i--]);
    }

    j++;
    q++;
    while (q <= end) {
        swap(nums[q++], nums[j++]);
    }

    quick_sort3(nums, begin, i);
    quick_sort3(nums, j, end);
}

int main() {
    int tmp[] = {3, 2, 1, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 7, 8, 8, 10};
    vector<int> nums;

    for (auto t : tmp) {
        nums.push_back(t);
    }

    shuffle(nums.begin(), nums.end(), default_random_engine(666));

    vector<int> nums2(nums);
    sort(nums2.begin(), nums2.end());

    for (auto t : nums2) {
        cout << t << ",";
    }
    cout << endl;

    quick_sort3(nums, 0, nums.size() - 1);
    for (auto t : nums) {
        cout << t << ",";
    }
    cout << endl;
}