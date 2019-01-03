import java.io.*;
import java.*;
import java.util.*;

/*
2
5 10
1 2 3 4 5
5 4 3 2 1
5 10
1 2 3 4 5
5 4 3 2 1

14
14
*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t != 0) {
            task(sc);
            t--;
        }
    }

    public static void task(Scanner sc) {

        int n = sc.nextInt();
        int backpack = sc.nextInt();

        int[] w = new int[n];
        int[] v = new int[n];

        for (int i = 0; i < n; i++) {
            v[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            w[i] = sc.nextInt();
        }

        int[] rs = new int[backpack + 1];

        for (int i = 0; i < n; i++) {
            for (int j = backpack; j >= w[i]; j--) {
                rs[j] = rs[j] > rs[j - w[i]] + v[i] ? rs[j] : rs[j - w[i]] + v[i];
            }
        }

        System.out.println(rs[backpack]);
    }
}