#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

struct Box {
  int x, y, z;
};

int search(vector<Box>& box, vector<bool>& flags, int heigh, int x, int y) {
  int maxH = heigh;
  for (int i = 0; i < box.size(); i++) {
    if (flags[i] == true) continue;
    if (box[i].x <= x && box[i].y <= y) {
      flags[i] = true;
      int h = search(box, flags, heigh + box[i].z, box[i].x, box[i].y);
      maxH = maxH > h ? maxH : h;

      flags[i] = false;
    }
  }

  return maxH;
}

int main() {
  int n = 0;
  cin >> n;
  vector<Box> boxList;
  vector<bool> flags;

  for (int i = 0; i < n; i++) {
    Box box;
    cin >> box.z >> box.x >> box.y;
    boxList.push_back(box);
    flags.push_back(false);
  }

  int maxH = search(boxList, flags, 0, MAX_INT, MAX_INT);

  cout << maxH << endl;

  return 0;
}