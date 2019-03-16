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
    if (i < j) nums[i] = nums[j];
    while (i < j && nums[i] <= pivot) i++;
    if (i < j) nums[j] = nums[i];
  }
  nums[i] = pivot;

  quick_sort(nums, begin, i - 1);
  quick_sort(nums, i + 1, end);
}

int main() {
  int tmp[] = {7, 4, 8, 21, 21, 3, 4, 3, 3, 4, 5};
  vector<int> nums;

  for (auto t : tmp) {
    nums.push_back(t);
  }

  vector<int> nums2(nums);
  sort(nums2.begin(), nums2.end());
  for (auto t : nums2) {
    cout << t << ",";
  }
  cout << endl;

  quick_sort(nums, 0, nums.size() - 1);
  for (auto t : nums) {
    cout << t << ",";
  }
  cout << endl;

  return 0;
}