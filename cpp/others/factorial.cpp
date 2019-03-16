#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

vector<int> add(vector<int> &nums1, vector<int> &nums2) {
  int i = 0;
  int j = 0;

  vector<int> rs;
  int add = 0;
  while (i < nums1.size() || j < nums2.size() || add > 0) {
    add += i < nums1.size() ? nums1[i++] : 0;
    add += j < nums2.size() ? nums2[j++] : 0;

    rs.push_back(add % 10);
    add = add / 10;
  }
  return rs;
}

vector<int> multiply(vector<int> &nums1, vector<int> &nums2) {
  vector<int> rs(nums1.size() + nums2.size());

  for (int i = 0; i < nums1.size(); i++) {
    for (int j = 0; j < nums2.size(); j++) {
      rs[i + j] += nums1[i] * nums2[j];
    }
  }

  int add = 0;
  for (int i = 0; i < rs.size(); i++) {
    add += rs[i];
    rs[i] = add % 10;
    add = add / 10;
  }

  return rs;
}

void print(vector<int> num) {
  int i = num.size() - 1;
  while (i > 0 && num[i] == 0) i--;
  while (i >= 0) {
    cout << num[i];
    i--;
  }
  cout << endl;
}

vector<int> factorial(int n) {
  vector<int> num(1, 1);
  vector<int> one(1, 1);

  vector<int> rs(1, 1);
  for (int i = 0; i < n; i++) {
    rs = multiply(rs, num);
    num = add(num, one);
  }

  return rs;
}

int main() {
  vector<int> nums1;

  nums1.push_back(9);
  nums1.push_back(2);
  nums1.push_back(1);

  vector<int> nums2(nums1);

  vector<int> rs = multiply(nums1, nums2);
  print(rs);

  vector<int> rs2 = add(nums1, nums2);
  print(rs2);

  vector<int> rs3 = factorial(101);
  print(rs3);

  return 0;
}