#include <iostream>
#include <queue>
#include <vector>

using namespace std;

struct cmp {
  bool operator()(int &a, int &b) const { return a > b; }
};

int main() {
  int nums[] = {2, 3, 4, 13, 44, 5, 6, 7};
  //   priority_queue<int, vector<int>, greater<int>> q;
  priority_queue<int, vector<int>, cmp> q;

  for (auto num : nums) {
    q.push(num);
  }

  while (!q.empty()) {
    cout << q.top() << ",";
    q.pop();
  }
  cout << endl;
}