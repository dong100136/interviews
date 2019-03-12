#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void merge_array(vector<int> &nums, int begin, int mid, int end,
                 vector<int> &tmp) {
  int i = begin, j = mid + 1;
  int index = begin;
  while (i <= mid && j <= end) {
    if (nums[i] < nums[j]) {
      tmp[index++] = nums[i++];
    } else {
      tmp[index++] = nums[j++];
    }
  }

  while (i <= mid) tmp[index++] = nums[i++];
  while (j <= end) tmp[index++] = nums[j++];

  for (int i = begin; i <= end; i++) {
    nums[i] = tmp[i];
  }
}

void merge_sort(vector<int> &nums, int begin, int end, vector<int> &tmp) {
  if (begin >= end) return;

  int mid = (begin + end) / 2;
  merge_sort(nums, begin, mid, tmp);
  merge_sort(nums, mid + 1, end, tmp);
  merge_array(nums, begin, mid, end, tmp);
}

void merge_sort(vector<int> &nums) {
  vector<int> tmp(nums.size());
  merge_sort(nums, 0, nums.size() - 1, tmp);
}

int main() {
  int tmp[] = {7, 4, 8, 21, 21, 3, 4, 5};
  vector<int> nums;

  for (auto t : tmp) {
    nums.push_back(t);
  }

  merge_sort(nums);

  for (auto t : nums) {
    cout << t << ",";
  }
  cout << endl;

  return 0;
}