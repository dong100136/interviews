#include <algorithm>
#include <iostream>
#include <map>
#include <vector>

using namespace std;

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

        for (int i = 0; i < n; i++) {
            cin >> tmp;
            skills[tmp]++;
        }
        cout << endl << "--------------------" << endl;

        auto cur = skills.rbegin();
        int minCount = 1000000;
        for (auto cur = skills.rbegin(); cur != skills.rend(); cur++) {
            cout << cur->first << endl;
            auto pre = cur;
            int remaining = p;

            int count = 0;
            while (remaining != 0 && pre != skills.rend()) {
                int k = min(remaining, pre->second);
                count += k * (cur->first - pre->first);
                remaining -= k;
                pre++;
            }

            if (remaining == 0) {
                minCount = min(minCount, count);
                // cout << cur->first << "," << cur->second << "," << remaining
                //      << endl;
                if (minCount == 0)
                    break;
            } else {
                break;
            }
        }

        cout << "Case #" << (t + 1) << ": " << minCount << endl;
    }
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