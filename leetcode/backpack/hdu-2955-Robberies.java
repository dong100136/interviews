import java.util.*;
import java.*;


/*
The aspiring Roy the Robber has seen a lot of American movies, and knows that the bad guys usually gets caught in the end, often because they become too greedy. He has decided to work in the lucrative business of bank robbery only for a short while, before retiring to a comfortable job at a university.



For a few months now, Roy has been assessing the security of various banks and the amount of cash they hold. He wants to make a calculated risk, and grab as much money as possible.


His mother, Ola, has decided upon a tolerable probability of getting caught. She feels that he is safe enough if the banks he robs together give a probability less than this.

The first line of input gives T, the number of cases. For each scenario, the first line of input gives a floating point number P, the probability Roy needs to be below, and an integer N, the number of banks he has plans for. Then follow N lines, where line j gives an integer Mj and a floating point number Pj . 
Bank j contains Mj millions, and the probability of getting caught from robbing it is Pj .

For each test case, output a line with the maximum number of millions he can expect to get while the probability of getting caught is less than the limit set.

Notes and Constraints
0 < T <= 100
0.0 <= P <= 1.0
0 < N <= 100
0 < Mj <= 100
0.0 <= Pj <= 1.0
A bank goes bankrupt if it is robbed, and you may assume that all probabilities are independent as the police have very low funds.

3
0.04 3
1 0.02
2 0.03
3 0.05
0.06 3
2 0.03
2 0.03
3 0.05
0.10 3
1 0.03
2 0.02
3 0.05


2
4
6

1
0.00 1
1 0.02
*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t>0){
            double max_p =1.0-sc.nextFloat();
            int n = sc.nextInt();

            int[] m = new int[n];
            double[] p = new double[n];
            int m_sum = 0;
            for (int i = 0; i < n; i++) {
                m[i] = sc.nextInt();
                p[i] = 1.0-sc.nextFloat();
                m_sum+=m[i];
            }
            

            double[] rs = new double[m_sum+1];
            rs[0] = 1;

            for (int i = 0; i < n; i++) {
                for (int j = m_sum;j>=m[i];j--){
                    rs[j] = Math.max(rs[j],rs[j-m[i]]*p[i]);
                }
            }

            for (int i = m_sum; i >= 0; i--) {
                // System.out.println(rs[i]);
                if (rs[i]>=max_p){
                    System.out.println(i);
                    break;
                }
            }

            // System.out.println("-------------");
            t--;
        }
    }
}