
// package backpack;
import java.util.*;

/*
--test--
5 6 10 1
1 1
2 1
3 4
4 5
1 2
1 2 5
1 3 4
2 4 4
3 4 5
1 5 1
4 5 1
----
0
--test--
*/

class Main {
    private static int MAX_VALUE = 1 << 30;

    public static void main(String[] args) {
        int N, M, W, X;
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        W = sc.nextInt();
        X = sc.nextInt();

        int[] tws = new int[N + 1];
        int[] tvs = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            tws[i] = sc.nextInt();
            tvs[i] = sc.nextInt();
        }

        int[][] road = new int[M][3];
        for (int i = 0; i < M; i++) {
            road[i] = new int[3];
            for (int j = 0; j < 3; j++) {
                road[i][j] = sc.nextInt();
            }
        }

        int[] queue = topsort(N, X, road);

        int[][] e = new int[N + 1][W + 1];
        int[][] w = new int[N + 1][W + 1];
        for (int i = 1; i < N + 1; i++) {
            w[i] = new int[W + 1];
            e[i] = new int[W + 1];
            for (int j = 1; j < W + 1; j++) {
                e[i][j] = i == X ? 0 : MAX_VALUE;
                w[i][j] = 0;
            }

            for (int j = 1; j * tws[i] <= W; j = j + 1) {
                w[i][j * tws[i]] = tvs[i] * j;
            }
        }

        // for (int i = 1; i <= N; i++) {
        // for (int j = 0; j <= W; j++) {
        // System.err.print("(" + j + "," + w[i][j] + "," + e[i][j] + ")" + ",");
        // }
        // System.out.println();
        // }
        // System.out.println("--------------");

        for (int i = 0; i < N; i++) {
            int p = queue[i];
            if (p == 0)
                break;

            for (int j = 0; j < M; j++) {
                if (road[j][1] != p)
                    continue;
                int q = road[j][0];
                int len = road[j][2];

                for (int k = 0; k <= W; k++) {
                    int nw = w[q][k];
                    int ne = e[q][k] + len * k;
                    if (w[p][k] > nw) {
                        continue;
                    } else if (w[p][k] < nw) {
                        w[p][k] = nw;
                        e[p][k] = ne;
                    } else if (e[p][k] > ne) {
                        e[p][k] = ne;
                    }
                }
            }

            for (int k = tws[p]; k <= W; k++) {
                if (w[p][k] > w[p][k - tws[p]] + tvs[p]) {
                    continue;
                } else if (w[p][k] < w[p][k - tws[p]] + tvs[p]) {
                    w[p][k] = w[p][k - tws[p]] + tvs[p];
                    e[p][k] = e[p][k - tws[p]];
                } else if (e[p][k] > e[p][k - tws[p]]) {
                    e[p][k] = e[p][k - tws[p]];
                }
            }

            // for (int k = 1; k <= N; k++) {
            // for (int j = 0; j <= W; j++) {
            // System.err.print("(" + j + "," + w[k][j] + "," + e[k][j] + ")" + ",");
            // }
            // System.out.println();
            // }
            // System.out.println("--------------");
        }

        int max_value = 0;
        int min_energy = Integer.MAX_VALUE;

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= W; j++) {
                if (w[i][j] > max_value) {
                    max_value = w[i][j];
                    min_energy = e[i][j];
                }

                if (w[i][j] == max_value && min_energy > e[i][j]) {
                    min_energy = e[i][j];
                }
            }
        }

        System.out.println(min_energy);
    }

    public static int[] topsort(int N, int X, int[][] road) {
        int[] queue = new int[N];
        int[] deg = new int[N + 1];

        queue[0] = X;
        int i = 0, j = 1;

        for (int k = 0; k < road.length; k++) {
            deg[road[k][1]]++;
        }

        while (i != j) {
            int p = queue[i++];

            for (int k = 0; k < road.length; k++) {
                if (road[k][0] == p) {
                    deg[road[k][1]]--;
                    if (deg[road[k][1]] == 0) {
                        queue[j++] = road[k][1];
                    }
                }
            }
        }

        return queue;
    }
}