
// package backpack;

import java.util.*;

/*
--test--
3 3
2 1
2 5
3 8
2 0
1 0
2 1
3 2
4 3
2 1
1 1

3 4
2 1
2 5
3 8
2 0
1 1
2 8
3 2
4 4
2 1
1 1

1 1
1 0
2 1

5 3
2 0
1 0
2 1
2 0
2 2
1 1
2 0
3 2
2 1
2 1
1 5
2 8
3 2
3 8
4 9
5 10
----
5
13
-1
-1
--test--
*/

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] nums = line.split("\\s+");
            if (nums.length<2) continue;
            int N = Integer.valueOf(nums[0]);
            int T = Integer.valueOf(nums[1]);

            int[][] dp = new int[N + 1][T + 1];

            for (int i = 1; i <= N; i++) {
                int M = sc.nextInt();
                int flag = sc.nextInt();

                int[] ww = new int[M];
                int[] vv = new int[M];

                for (int j = 0; j < M; j++) {
                    ww[j] = sc.nextInt();
                    vv[j] = sc.nextInt();
                }

                dp[i] = new int[T + 1];
                if (flag == 0) {
                    for (int j = 0; j <= T; j++) {
                        dp[i][j] = -1000000;
                    }

                    for (int j = 0; j < M; j++) {
                        for (int k = T; k >= ww[j]; k--) {
                            dp[i][k] = Math.max(Math.max(dp[i][k], dp[i - 1][k - ww[j]] + vv[j]),
                                    dp[i][k - ww[j]] + vv[j]);
                        }
                    }
                } else if (flag == 1) {
                    for (int j = 0; j <= T; j++) {
                        dp[i][j] = dp[i - 1][j];
                    }

                    for (int j = 0; j < M; j++) {
                        for (int k = T; k >= ww[j]; k--) {
                            dp[i][k] = Math.max(dp[i][k], dp[i - 1][k - ww[j]] + vv[j]);
                        }
                    }
                } else if (flag == 2) {
                    for (int j = 0; j <= T; j++) {
                        dp[i][j] = dp[i - 1][j];
                    }

                    for (int j = 0; j < M; j++) {
                        for (int k = T; k >= ww[j]; k--) {
                            dp[i][k] = Math.max(dp[i][k], dp[i][k - ww[j]] + vv[j]);
                        }
                    }
                }
            }

            int max_t = -1;
            for (int i = 0; i <= T; i++) {
                max_t = Math.max(dp[N][T], max_t);
            }

            System.out.println(max_t);
        }
    }
}