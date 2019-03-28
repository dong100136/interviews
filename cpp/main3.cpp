<<<<<<< HEAD
=======
#include <algorithm>
#include <bitset>
>>>>>>> update
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;
<<<<<<< HEAD
#define INF 10000;

struct Problem{
    int limit,cost;
};

int main(){
    int T;
    cin >> T;
    for (int t = 0; t < T;t++){
        int n, k;
        cin >> n >> k;
        vector<Problem> problems;

        int a,b;
        int sum=0,maxinum=0;
        for (int i = 0;i<k;i++){
            cin>>a>>b;
            problems.push_back(Problem{a,b});
            sum+=b;
        }

        sort(problems.begin(),problems.end(),[&](Problem a,Problem b){return a.cost<b.cost;});


        int rs = 0;
        for (auto p:problems){
            // cout<<p.limit<<","<<p.cost<<endl;
            if (n<p.limit) {
                rs+=(p.limit-n);
                n+=(p.limit-n);
            }
            n = n-p.cost;
        }

        cout<<max(max(sum-n,0),rs)<<endl;
=======

const long maxLen = 1000007;
// const int maxLen = 1;

bitset<maxLen> seats;

struct Range {
    int l, r, len;
};

int main() {
    int T;
    cin >> T;

    for (int t = 0; t < T; t++) {
        int n, q;
        cin >> n >> q;

        vector<Range> bookings;
        int l, r;
        for (int i = 0; i < q; i++) {
            cin >> l >> r;
            l = min(n, l);
            r = min(n, r);
            bookings.push_back(Range{l, r, r - l + 1});
        }

        stable_sort(bookings.begin(), bookings.end(),
                    [&](Range a, Range b) { return a.len < b.len; });

        // cout << endl << "---------------" << endl;
        int maxCount = 1000007;
        seats.reset();
        for (Range book : bookings) {
            int count = 0;
            for (int j = book.l; j <= book.r; j++) {
                count += seats.test(j) ? 0 : 1;
                seats.set(j);
            }
            // cout << count << endl;
            maxCount = min(maxCount, count);
            if (maxCount == 0)
                break;
        }

        cout << "Case #" << (t + 1) << ": " << maxCount << endl;
>>>>>>> update
    }

    return 0;
}