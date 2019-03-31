#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void heapify(vector<int> &nums, int begin, int end) {
  int parent = begin;
  int child = 2 * parent + 1;

  while (child < end) {
    if (child + 1 < end && nums[child] < nums[child + 1]) child++;

    if (nums[parent] < nums[child]) {
      swap(nums[parent], nums[child]);
      parent = child;
      child = 2 * parent + 1;
    } else
      break;
  }
}

void heap_sort(vector<int> &nums) {
  int n = nums.size();
  for (int i = (n + 1) / 2; i >= 0; i--) {
    heapify(nums, i, n);
  }

  for (int i = n - 1; i > 0; i--) {
    swap(nums[0], nums[i]);
    heapify(nums, 0, i);
  }
}

int main() {
  int tmp[] = {7, 4, 8, 21, 21, 3, 4, 3, 3, 4, 5, 0, 0, 0};
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

  heap_sort(nums);
  for (auto t : nums) {
    cout << t << ",";
  }
  cout << endl;
}