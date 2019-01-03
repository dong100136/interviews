import java.util.*;

import java.*;

/*
1
50
5
10
1 2 3 2 1 1 2 3 2 1
50
2
11 5
4
10
3 10 10 10 11 1 3 4 5 6
7
0

-45
32
4
-5
*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n != 0) {
            int[] w = new int[n];
            int max_w = sc.nextInt();
            for (int i = 0; i < n - 1; i++) {
                w[i] = sc.nextInt();
                if (w[i] > max_w) {
                    int t = w[i];
                    w[i] = max_w;
                    max_w = t;
                }
            }

            int total_w = sc.nextInt() - 5;

            if (total_w < 0) {
                System.out.println(total_w + 5);
            } else {
                int[] rs = new int[total_w + 1];

                for (int i = 0; i < n - 1; i++) {
                    for (int j = total_w; j >= w[i]; j--) {
                        rs[j] = rs[j] > rs[j - w[i]] + w[i] ? rs[j] : rs[j - w[i]] + w[i];
                    }
                }

                System.out.println(total_w + 5 - rs[total_w] - max_w);
            }

            n = sc.nextInt();
        }
    }
}