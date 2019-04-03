#include <stdio.h>
#include <string.h>
#include <iostream>
#include <vector>
using namespace std;

// Read only region start

int findPosition(int input1, int input2, int input3[][2]) {
    // Read only region end
    // Write code and remove the below exception.

    vector<int> q;
    for (int i = 0; i < input1; i++) {
        q.push_back(i + 1);
    }
    int head = 0;
    int result = 0;

    for (int i = 0; i < input2; i++) {
        for (int i = 0; i < input1; i++) {
            cout << q[i] << ",";
        }
        cout << endl;

        if (input3[i][0] == 1) {
            q[head] = 0;
            head++;
            // while (q[head] == 0 && head <= input1) head++;

            for (int j = 0; j < input1; j++) {
                if (q[j] == 0) continue;
                q[j] = q[j] - 1;
            }
        } else if (input3[i][0] == 2) {
            q[input3[i][1] - 1] = 0;
            for (int j = input3[i][1]; j < input1; j++) {
                if (q[j] == 0) continue;
                q[j] = q[j] - 1;
            }
        } else {
            result += q[input3[i][1] - 1];
        }
    }
    return result;
}

int main() {
    int nums[][2] = {{1, 0}, {3, 3}, {2, 2}};
    cout << findPosition(5, 3, nums) << endl;
}