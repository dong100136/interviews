#include <algorithm>
#include <iostream>
#include <queue>
#include <sstream>
#include <stack>
#include <string>
#include <vector>

using namespace std;

void split(const string& s, vector<string>& sv, const char delim = ' ') {
    sv.clear();
    std::istringstream iss(s);
    std::string temp;

    while (getline(iss, temp, delim)) {
        sv.emplace_back(std::move(temp));
    }

    return;
}

int main() {
    string name = "dog dog cat cat you    a cat";
    vector<string> s;
    split(name, s, "\b+");
    for (auto ss : s) {
        cout << ss << endl;
    }

    return 0;
}