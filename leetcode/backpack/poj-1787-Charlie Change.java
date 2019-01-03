package backpack;

import java.util.*;

/*
--test--
12 5 3 1 2
16 0 0 0 1
12 12 10 10 10
12 1 5 10 10
0 0 0 0 0
----
2 2 0 0
0
12 0 0 0 
0
--test--
*/

class Main{
    private static int[] vv = new int[]{1,5,10,25};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true){
            int W = sc.nextInt();
            int[] dp = new int[W+1];
            int[][] state = new int[W+1][4];
            for (int i = 0;i<W+1;i++){
                state[i] = new int[4];
            }

            int[] cc = new int[4];
            int sum = 0;
            for (int i = 0;i<4;i++){
                cc[i] = sc.nextInt();
                sum+=vv[i]*cc[i];
            }
            if (sum==0) break;

            for (int i = 0;i<4;i++){
                int c = cc[i];
                int j = 1;
                while (j<=c){
                    int v = j*vv[i];
                    for (int k=W;k>=v;k--){
                        if (dp[k-v]+j>dp[k]){
                            dp[k] = dp[k-v]+j;
                            state[k] = state[k-v];
                            state[k][i]+=j;
                        }
                    }
                    c = c-j;
                    j = j<<1;
                }

                if (c!=0){
                    int v = c*vv[i];
                    for (int k=W;k>=v;k--){
                        if (dp[k-v]+c>dp[k]){
                            dp[k] = dp[k-v]+c;
                            state[k] = state[k-v];
                            state[k][i]+=c;
                        }
                    } 
                }
            }

            for (int i = 0;i<=W;i++){
                System.out.println(i+","+dp[i]+","+state[W][0]+","+state[W][1]+","+state[W][2]+","+state[W][3]);
            }
            System.out.println();
            
            if (dp[W]==0){
                System.out.println("Charlie cannot buy coffee.");
            }else{
                String s= String.format("Throw in %d cents, %d nickels, %d dimes, and %d quarters.",
                                        state[W][0],state[W][1],state[W][2],state[W][3]);
                System.out.println(s);
            }
        }
    }
}