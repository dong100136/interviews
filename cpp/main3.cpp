#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;
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
    }
    return 0;
}