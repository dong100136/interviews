import java.util.*;

/*
Description

Farmer John has gone to town to buy some farm supplies. Being a very efficient man, he always pays for his goods in such a way that the smallest number of coins changes hands, i.e., the number of coins he uses to pay plus the number of coins he receives in change is minimized. Help him to determine what this minimum number is.

FJ wants to buy T (1 ≤ T ≤ 10,000) cents of supplies. The currency system has N (1 ≤ N ≤ 100) different coins, with values V1, V2, ..., VN (1 ≤ Vi ≤ 120). Farmer John is carrying C1 coins of value V1, C2 coins of value V2, ...., and CN coins of value VN (0 ≤ Ci ≤ 10,000). The shopkeeper has an unlimited supply of all the coins, and always makes change in the most efficient manner (although Farmer John must be sure to pay in a way that makes it possible to make the correct change).

Input

Line 1: Two space-separated integers: N and T. 
Line 2: N space-separated integers, respectively V1, V2, ..., VN coins (V1, ...VN) 
Line 3: N space-separated integers, respectively C1, C2, ..., CN
Output

Line 1: A line containing a single integer, the minimum number of coins involved in a payment and change-making. If it is impossible for Farmer John to pay and receive exact change, output -1.
Sample Input

3 70
5 25 50
5 2 1
Sample Output

3
Hint

Farmer John pays 75 cents using a 50 cents and a 25 cents coin, and receives a 5 cents coin in change, for a total of 3 coins used in the transaction.

--test--
3 70
5 25 50
5 2 1
----
3
-------
1 70
5
3
----
-1
--------
3 50
5 25 50
5 2 1
----
1
--------
3 5000
6000 9000 10000
1 1 0
----
3
--------
2 80
5 30
3 3
----
5
---------
2 10
1 1
20 0
----
10
--test--
*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int T = sc.nextInt();

        int[] v = new int[N];
        int[] c = new int[N];
        int max_coins = 0;
        for (int i = 0; i < N; i++) {
            v[i] = sc.nextInt();
            max_coins = Math.max(max_coins, v[i]);
        }

        for (int i = 0; i < N; i++) {
            c[i] = sc.nextInt();
        }

        int max_w = max_coins * max_coins + T;

        // muplit backpack
        int[] dp1 = new int[max_w + 1];
        for (int i = 0; i <= max_w; i++) {
            dp1[i] = 1000000;
        }

        dp1[0] = 0;

        for (int i = 0; i < N; i++) {
            for (int k = 1; c[i] > 0; k <<= 1) {
                k = Math.min(k, c[i]);
                int nv = k * v[i];
                for (int j = max_w; j >= nv; j--) {
                    dp1[j] = Math.min(dp1[j], dp1[j - nv] + k);
                }
                c[i] -= k;
            }
        }

        // complete backpack
        max_w = max_coins * max_coins;
        int[] dp2 = new int[max_w + 1];
        for (int i = 0; i <= max_w; i++) {
            dp2[i] = 1000000;
        }
        dp2[0] = 0;

        for (int i = 0; i < N; i++) {
            for (int j = v[i]; j <= max_w; j++) {
                dp2[j] = Math.min(dp2[j], dp2[j - v[i]] + 1);
            }
        }

        int min_coins = 100000;
        for (int i = 0; i <= max_w; i++) {
            min_coins = Math.min(min_coins, dp1[T + i] + dp2[i]);
        }

        if (min_coins < 100000) {
            System.out.println(min_coins);
        } else {
            System.out.println("-1");
        }
    }
}