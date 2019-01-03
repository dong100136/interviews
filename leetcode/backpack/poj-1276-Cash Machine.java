// package backpack;

import java.util.*;


/*
--test--
735 3  4 125  6 5  3 350
633 4  500 30  6 100  1 5  0 1
735 0
0 3  10 100  10 50  10 10
10 3 0 1 3 2 1 11
2 1 1 1
100 2 20 1 10 101
---
735
630
0
0
6
1
20
--test--
*/

class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()){

            int W = sc.nextInt();
            int n = sc.nextInt();

            int[] cc = new int[n];
            int[] vv = new int[n];

            for (int i = 0;i<n;i++){
                cc[i] = sc.nextInt();
                vv[i] = sc.nextInt();
            }

            int[] dp = new int[W+1];
            for (int j = 0;j<W+1;j++){
                dp[j] = -100000;
            }
            dp[0] = 0;

            for (int i  = 0;i<n;i++){
                int k = 1;
                while (k<=cc[i]){
                    int nv = k*vv[i];
                    for (int j = W;j>=nv;j--){
                        dp[j] = Math.max(dp[j],dp[j-nv]+nv);
                    }

                    cc[i]-=k;
                    k*=2;
                }

                if (cc[i]>0){
                    int nv = cc[i]*vv[i];
                    for (int j = W;j>=nv;j--){
                        dp[j] = Math.max(dp[j],dp[j-nv]+nv);
                    }
                }
            }
        
            boolean rs = false;
            for (int j = W;j>=0;j--){
                // System.out.println("("+j+","+dp[j]+")");
                if (dp[j]>0){
                    System.out.println(j);
                    rs = true;
                    break;
                }
            }
            if (!rs) System.out.println(0);
        }
    }
}