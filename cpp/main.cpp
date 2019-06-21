#include <algorithm>
#include <iostream>
#include <queue>
#include <stack>
#include <vector>

using namespace std;

int main() {
    int n;
    cin >> n;

    queue<int> buy_queue;
    queue<int> sell_queue;
    vector<int> nums(n);
    long cost = 0;

    for (int i = 0; i < n; i++) {
        cin >> nums[i];
        if (nums[i] < 0)
            sell_queue.push(i);
        else if (nums[i] > 0)
            buy_queue.push(i);
        nums[i] = abs(nums[i]);

        while (!sell_queue.empty() && !buy_queue.empty()) {
            int buy = buy_queue.front();
            int sell = sell_queue.front();
            int count = min(nums[buy], nums[sell]);

            nums[buy] = nums[buy]-count;
            nums[sell] =nums[sell]-count;

            cost =cost+ abs(buy - sell) * count;
            if (nums[buy] == 0) buy_queue.pop();
            if (nums[sell] == 0) sell_queue.pop();
        }
    }

    cout << cost << endl;

    return 0;
}
