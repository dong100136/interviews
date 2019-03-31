#include <algorithm>
#include <iostream>
#include <map>
#include <queue>
#include <sstream>
#include <stack>
#include <string>
#include <vector>
using namespace std;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
int main() { 
    vector<int> a, b;
    string line;
    getline(cin, line);
    
=======
struct Box {
  int x, y, z;
};

=======
>>>>>>> update
int main() {
    int T;
    cin >> T;
    for (int t = 0; t < T; t++) {
        int n, p;
        cin >> n >> p;

        map<int, int> skills;
        int tmp;
=======
class Solution {
   public:
    void split(const string& s, vector<string>& sv, const char delim = ' ') {
        sv.clear();
        std::istringstream iss(s);
        std::string temp;
>>>>>>> update

        while (getline(iss, temp, delim)) {
            sv.emplace_back(std::move(temp));
        }

        return;
    }
    bool wordPattern(string pattern, string str) {
        map<char, string> dict1;
        set<string> dict2;
        vector<string> l;

        split(str, l);

        if (l.size() != pattern.size()) return false;

        for (int i = 0; i < l.size(); i++) {
            char c = pattern[i];
            string s = l[i];
            if (dict1.count(c) > 0 && dict1[c] != s) {
                return false;
            } else if (dict2.count(s) > 0) {
                return false;
            } else {
                dict1[c] = s;
                dict2.insert(s);
            }
        }
        return true;
    }
<<<<<<< HEAD
<<<<<<< HEAD

    maxH = maxH < height[i] ? height[i] : maxH;
  }

  cout << maxH << endl;

  return 0;
>>>>>>> a9be86b2c9fbf998d404ced90d21090f2a0eb23c
=======
    return 0;
>>>>>>> update
}
=======
};
>>>>>>> update
