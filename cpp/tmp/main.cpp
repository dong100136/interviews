#include <cmath>
#include <iostream>
using namespace std;
int p[10];
int f(int *a, int n) {
    int x = *(a + 3);
    for (int *pa = a + 1; pa < a + n; pa++)
        if (*pa > x) x = *pa;
    return x;
}

int main() {
    int x[10] = {23, 46, 78, 99, 16, 24, 56, 90, 67, 44};
    cout << -3 % 10 << endl;
}