#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

void quick_sort(vector<int> &nums, int begin, int end) {
    if (begin >= end)
        return;

    int i = begin, j = end, mid = (i + j) / 2;
    int pivot = nums[mid];

    while (i < j) {
        while (i < j && nums[j] >= pivot) j++;
        while (i<j && nums[i]<=pivot) i++;
        swap(nums[i],nums[j]);
    }
    swap(nums[i],nums[begin]);
    quick_sort(nums,begin,i-1);
    quick_sort(nums,i+1,end);
}

int main() { 
    vector<int> nums;
    for (int j = 100;j>=0;j--){
        nums.push_back(j);
    }

    quick_sort(nums,0,nums.size()-1);

    for (auto num:nums){
        cout<<num<<",";
    }
    cout<<endl;

    return 0;
}