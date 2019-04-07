#include <algorithm>
#include <iostream>
#include <queue>
#include <stack>
#include <vector>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;

    vector<int> nums(n);
    for (int i = 0; i < n; i++) {
        cin>>nums[i];
    }
    
    sort(nums.begin(),nums.end());

    int i = 0;
    int sum = 0;
    while (i<nums.size() && k>0){
        while (nums[i]==0 || nums[i]-sum==0) i++;
        cout<<nums[i]-sum<<endl;
        sum=nums[i];
        i++;
        k--;
    }

    while (k-->0) cout<<"0"<<endl;

    return 0;
}