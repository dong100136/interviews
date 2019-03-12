#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Bank
{
    int d;
    int v;
    Bank() {}
    Bank(int dis, int val) : d(dis), v(val) {}
};

int main()
{
    int n, d;
    cin >> n >> d;

    vector<int> pos(n), money(n), index(n);
    for (int i = 0; i < n; i++)
    {
        cin >> pos[i];
        cin >> money[i];
        index[i] = i;
    }

    sort(index.begin(), index.end(),
         [&money](const int &a, const int &b) {
             return (money[a] < money[b]);
         });

    int maxinum = 0;

    for (int i = 0; i < n; i++)
    {
        cout << index[i] << endl;
        if (2 * money[index[i]] <= maxinum)
            break;
        for (int j = i + 1; j < n; j++)
        {
            if (abs(pos[index[i]] - pos[index[j]]) >= d)
            {
                maxinum = max(maxinum, money[index[i]] + money[index[j]]);
                break;
            }
        }
    }

    cout << maxinum << endl;
    return 0;
}
