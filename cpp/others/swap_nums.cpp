#include <iostream>
#include <algorithm>
using namespace std;

int main(){
    int a = 101;
    int b = 202;

    a = a^b;
    b = a^b;
    a = a^b;

    cout<<a<<","<<b<<endl;

    cout<<1e3<<","<<1e-3<<endl;
}
