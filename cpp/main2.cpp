#include <iostream>
#include <limits>
#include <numeric>
#include <vector>

using namespace std;

/** 请完成下面这个函数，实现题目要求的功能 **/
/** 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^  **/

int apple(int m,int n) {
	if(m==0||n==1) return 1;
	if(n>m) return apple(m,m);
	else return apple(m,n-1)+apple(m-n,n);
} 

int ballAllocate(int m, int n, int k) {
    k = min(m+n,k);
    long long rs = 0;
    for (int kk = 1;kk<k;kk++){
        rs+=max(apple(m,kk),apple(n,k-kk));
    }
    return rs%10000;
}

int main() {
    int res;

    int _m;
    cin >> _m;
    cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    int _n;
    cin >> _n;
    cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    int _k;
    cin >> _k;
    cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    res = ballAllocate(_m, _n, _k);
    cout << res << endl;

    cout<<123456%10000<<endl;

    return 0;
}