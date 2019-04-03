#include <algorithm>
#include <iostream>
#include <map>
#include <queue>
#include <stack>
#include <string>
#include <vector>

using namespace std;

class A {
    char a[10];
    int b;

   public:
    A(char *aa, int bb) {
        strcpy(a, aa);
        b = bb;
    }

    char *Geta() { return a; }
    int Getb() { return b; }
    A &operator>(A &c) {
        if (strcmp(a, c.a) > 0 && (b > c.b))
            return *this;
        else
            return c;
    }

    void show() { cout << a << ":" << b; }
};

int main() {
    char *p1 = "abcd", *p2 = "efgh";
    int d1 = 6, d2 = 8;
    A x(p1, d1), y(p2, d2);
    (x > y).show();
    return 0;
}