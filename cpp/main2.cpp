#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

void check(string &s) {
    int count1;
    int count2;
    int i = 0;
    while (i < s.size()) {
        if (s[i] == s[i - 1] && s[i - 1] == s[i - 2]) {
            s.erase(i, 1);
        } else if (i - 3 >= 0 && s[i] == s[i - 1] && s[i - 2] == s[i - 3]) {
            s.erase(i, 1);
        } else {
            i++;
        }
    }
    cout << s << endl;
}

int main() {
    int n;
    cin >> n;

    for (int i = 0; i < n; i++) {
        string s;
        cin >> s;
        check(s);
    }
}