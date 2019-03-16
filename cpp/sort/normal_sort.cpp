#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Person {
  int height;
  int weight;
};

bool compare(Person a, Person b) { return a.height > b.height; }

int main() {
  vector<Person> nums;
  for (int i = 0; i < 10; i++) {
    Person p{i, 100 - i};
    nums.push_back(p);
  }

  sort(nums.begin(), nums.end(),
       [](Person a, Person b) { return a.height > b.height; });

  for (auto t : nums) {
    cout << "(" << t.height << "," << t.weight << ")"
         << ",";
  }
  cout << endl;

  int h[] = {1, 4, 1, 2, 9, 4, 3, 20, 1, 2, 3, 4};
  vector<int> height;
  vector<int> index;
  for (int i = 0; i < sizeof(h) / sizeof(h[0]); i++) {
    height.push_back(h[i]);
    index.push_back(i);
  }

  sort(index.begin(), index.end(),
       [&height](int a, int b) { return height[a] < height[b]; });

  for (auto t : index) {
    cout << "(" << t << "," << height[t] << ")"
         << ",";
  }
  cout << endl;

  return 0;
}