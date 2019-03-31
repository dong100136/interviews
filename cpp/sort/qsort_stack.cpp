#include <iostream>
#include <vector>
#include <algorithm>
#include <stack>

using namespace std;

void sort(vector<int> &nums){
    stack<pair<int,int>> st;
    st.push(pair<int,int>(0,nums.size()-1));

    while (!st.empty()){
        int begin = st.top().first;
        int end = st.top().second;

        st.pop();

        if (begin>=end) continue;

        int i = begin;
        int j = end;
        int pivot = nums[i];
        while (i<j){
            while (i<j && nums[j]>=pivot) j--;
            while (i<j && nums[i]<=pivot) i++;
            if (i<j) swap(nums[i],nums[j]);
        }
        swap(nums[begin],nums[i]);

        st.push(pair<int,int>(begin,i-1));
        st.push(pair<int,int>(i+1,end));
    }
}

int main()
{

    int tmp[] = {1,24,3,2,1,2,4,4,5,6,67,7,6,5,4};
    vector<int> nums;
    for (auto num:tmp){
        nums.push_back(num);
    }

    sort(nums);

    for (auto num:nums){
        cout<<num<<",";
    }
    cout<<endl;

    return 0;
}

