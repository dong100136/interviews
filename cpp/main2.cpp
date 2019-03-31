#include <algorithm>
#include <iostream>
#include <map>
#include <set>
#include <vector>

using namespace std;

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

int main() {
  int n = 0;
  cin >> n;
  vector<Box> boxList;
  vector<bool> flags;

  for (int i = 0; i < n; i++) {
    Box box;
    cin >> box.z >> box.x >> box.y;
    boxList.push_back(box);
    flags.push_back(false);
  }

  int maxH = search(boxList, flags, 0, MAX_INT, MAX_INT);

  cout << maxH << endl;

  return 0;
>>>>>>> a9be86b2c9fbf998d404ced90d21090f2a0eb23c
}