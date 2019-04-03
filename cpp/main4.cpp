#include <stdio.h>
#include <string.h>
#include <cmath>
#include <iostream>
#include <vector>

using namespace std;

double calcDist(int *p1, int *p2) {
    double d1 = p1[0] - p2[0];
    double d2 = p1[1] - p2[1];
    return sqrt(d1 * d1 + d2 * d2);
}

// Read only region start
double honey(int input1, int input2, int **input3, int **input4, int input5[],
             int input6) {
    // Read only region end
    // Write code and remove the below exception.
    double d = 0;
    int cnt = 0;
    vector<bool> f(input1, false);

    int home[]{input5[0], input5[1]};

    while (d < input6) {
        double minDist1 = 1000000.0;
        int index = -1;
        for (int i = 0; i < input1; i++) {
            if (f[i] == true) continue;
            double dd = calcDist(input3[i], input5);
            if (dd < minDist1) {
                index = i;
                minDist1 = dd;
            }
        }

        if (index == -1) break;

        f[index] = true;
        input5[0] = input3[index][0];
        input5[1] = input3[index][1];

        double minDist2 = 1000000.0;
        index = -1;

        for (int i = 0; i < input2; i++) {
            double dd = calcDist(input4[i], input5);
            if (dd < minDist2) {
                index = i;
                minDist2 = dd;
            }
        }

        double homeDist = calcDist(home, input5);

        d += minDist1 + minDist2;
        if (d + homeDist > input6) break;

        input5[0] = input4[index][0];
        input5[1] = input4[index][1];
        cnt++;

        if (cnt == input1) break;
    }
    return cnt;
}