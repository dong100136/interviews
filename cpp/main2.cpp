#include <algorithm>
#include <bitset>
#include <iostream>
<<<<<<< HEAD
#include <map>
#include <set>
=======
>>>>>>> update
#include <vector>

using namespace std;

<<<<<<< HEAD
<<<<<<< HEAD
#define INF 1000000
struct Point {
    int target, len;
};

/*
3 5 1 6 
5 2 3 
1
1 3 10 
1 5 14 
3 5 1 
1 4 9 
4 5 5 
4 2 8 
*/

int main() {
    int n, m, k, g;
    cin >> n >> m >> k >> g;
    vector<int> target;
    set<int> rewards;
    map<int, vector<Point>> graph;

    int tmp;
    for (int i = 0; i < n; i++) {
        cin >> tmp;
        target.push_back(tmp - 1);
    }

    for (int i = 0; i < k; i++) {
        cin >> tmp;
        rewards.insert(tmp - 1);
    }

    int a, b, c;
    for (int i = 0; i < g; i++) {
        cin >> a >> b >> c;
        a--;
        b--;

        graph[a].push_back(Point{b, c});
        graph[b].push_back(Point{a, c});
    }

    vector<int> dist(m,INF);
    vector<bool> visited(m, false);
    vector<bool> getReward(m, false);
    for (int i = 0; i < graph[0].size(); i++) {
        dist[graph[0][i].target] = graph[0][i].len;

        getReward[graph[0][i].target] = rewards.count(0)>0?true:false;
    }

    for (int i = 0;i<m;i++){
      getReward[i] = rewards.count(i)>0?true:getReward[i];
    }



    for (int i = 0; i < m; i++) {
        int minDist = INF;
        int minIndex = -1;
        for (int j = 0; j < m; j++) {
            if (visited[j] == false && dist[j] < minDist) {
                minDist = dist[j];
                minIndex = j;
            }
        }
        if (minDist == INF) break;

        visited[minIndex] = true;

    //     cout<<minIndex<<endl;
    //         for (int j = 0; j < m; j++) {
    //     cout << "("<<getReward[j] << ","<<dist[j]<<"),";
    // }
    // cout << endl;

        for (int j = 0; j < graph[minIndex].size(); j++) {
            int next = graph[minIndex][j].target;
            if (dist[next] >= dist[minIndex] + graph[minIndex][j].len) {
                dist[next] = dist[minIndex] + graph[minIndex][j].len;
                getReward[next] = getReward[minIndex]||getReward[next];
            }
        }
    }

    // for (int i = 0; i < m; i++) {
    //     for (int j = 0; j < m; j++) {
    //         cout << graph[i][j] << ",";
    //     }
    //     cout << endl;
    // }

    // for (int i = 0; i < m; i++) {
    //     cout << getReward[i] << ",";
    // }
    // cout << endl;

    bool allReward = true;
    for (int i = 0; i < n; i++) {
        // cout << (target[i] - 1) << endl;
        if (dist[target[i]] == INF) {
            cout << "Terrible" << endl;
            return 0;
        }
        if (getReward[target[i]] == false) {
            allReward = false;
        }
    }
    if (allReward) {
        cout << "Wonderful" << endl;
    } else {
        cout << "Good" << endl;
    }
    return 0;
=======
struct Box {
  int x, y, z;
};

int search(vector<Box>& box, vector<bool>& flags, int heigh, int x, int y) {
  int maxH = heigh;
  for (int i = 0; i < box.size(); i++) {
    if (flags[i] == true) continue;
    if (box[i].x <= x && box[i].y <= y) {
      flags[i] = true;
      int h = search(box, flags, heigh + box[i].z, box[i].x, box[i].y);
      maxH = maxH > h ? maxH : h;

      flags[i] = false;
    }
  }

  return maxH;
}

=======
>>>>>>> update
int main() {
    int T;
    cin >> T;

    for (auto t = 0; t < T; t++) {
        int row, col;
        cin >> row >> col;
        vector<vector<int>> building;

        string line;
        for (int i = 0; i < row; i++) {
            cin >> line;
            for (int j = 0; j < col; j++) {
                if (line[j] == '1') {
                    vector<int> point;
                    point.push_back(i);
                    point.push_back(j);

                    building.push_back(point);
                }
            }
        }

        int rs = 1000000000;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                vector<int> point;
                point.push_back(i);
                point.push_back(j);

                building.push_back(point);

                int maxDist = 0;
                for (int ii = 0; ii < row; ii++) {
                    for (int jj = 0; jj < col; jj++) {
                        int minDist = 10000000;
                        for (auto k = building.begin(); k != building.end();
                             k++) {
                            minDist = min(abs(ii - (*k)[0]) + abs(jj - (*k)[1]),
                                          minDist);
                        }

                        maxDist = max(maxDist, minDist);
                    }
                }

                rs = min(rs, maxDist);
                building.pop_back();
            }
        }
        cout << "Case #" << (t + 1) << ": " << rs << endl;
    }

<<<<<<< HEAD
  return 0;
>>>>>>> a9be86b2c9fbf998d404ced90d21090f2a0eb23c
=======
    return 0;
>>>>>>> update
}