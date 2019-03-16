#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

struct Box {
  int x, y, z;
};

int main() {
  int n = 0;
  cin >> n;
  vector<Box> boxList;

  for (int i = 0; i < n; i++) {
    Box box;
    cin >> box.z >> box.x >> box.y;
    boxList.push_back(box);
  }

  // sort(boxList.begin(), boxList.end(),
  //      [](Box a, Box b) { return a.x > b.x || (a.x == b.x && a.y > b.y); });
  sort(boxList.begin(), boxList.end(),
       [](Box a, Box b) { return (a.x * a.y) > (b.x * b.y); });

  vector<int> height(n);

  int maxH = 0;
  for (int i = 0; i < n; i++) {
    // cout << boxList[i].z << "," << boxList[i].y << "," << boxList[i].x <<
    // endl;
    height[i] = boxList[i].z;

    int j = i - 1;
    while (j >= 0) {
      if (boxList[j].x >= boxList[i].x && boxList[j].y >= boxList[i].y &&
          height[j] + boxList[i].z > height[i]) {
        height[i] = height[j] + boxList[i].z;
      }
      j--;
    }

    maxH = maxH < height[i] ? height[i] : maxH;
  }

  cout << maxH << endl;

  return 0;
}