import java.util.Scanner;
import java.lang.Math;

/*
http://poj.org/problem?id=3264

For the daily milking, Farmer John's N cows (1 ≤ N ≤ 50,000) always line up in the same order. One day Farmer John decides to organize a game of Ultimate Frisbee with some of the cows. To keep things simple, he will take a contiguous range of cows from the milking lineup to play the game. However, for all the cows to have fun they should not differ too much in height.

Farmer John has made a list of Q (1 ≤ Q ≤ 200,000) potential groups of cows and their heights (1 ≤ height ≤ 1,000,000). For each group, he wants your help to determine the difference in height between the shortest and the tallest cow in the group.

Sample Input

6 3
1
7
3
4
2
5
1 5
4 6
2 2
Sample Output

6
3
0
*/

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cow_num = scanner.nextInt();
        int q_num = scanner.nextInt();

        int[] cow_height = new int[cow_num];
        for (int i = 0; i < cow_num; i++) {
            cow_height[i] = scanner.nextInt();
        }

        int[][] query = new int[q_num][2];
        for (int i = 0; i < q_num; i++) {
            query[i][0] = scanner.nextInt();
            query[i][1] = scanner.nextInt();
        }

        SegmentTree sTree = new SegmentTree(cow_height);

        for (int i = 0; i < q_num; i++) {
            System.out.println(sTree.query(query[i][0], query[i][1]));
        }
    }
}

class SegmentTree {
    public TreeNode[] tree;

    public SegmentTree(int[] data) {
        int n = data.length;
        tree = new TreeNode[n << 2];
        buildTree(data, 1, n, 1);
        // System.out.print("-------------");
        // System.out.println(tree[0].l+","+tree[0].r);
    }

    public void buildTree(int[] data, int l, int r, int k) {
        // System.out.println(l + "-" + r + "-" + k);
        tree[k] = new TreeNode();
        tree[k].l = l;
        tree[k].r = r;
        if (l == r) {
            tree[k].minHeight = data[l - 1];
            tree[k].maxHeight = data[l - 1];
            return;
        }

        int mid = l + (r - l) / 2;
        buildTree(data, l, mid, k << 1);
        buildTree(data, mid + 1, r, k << 1 | 1);

        tree[k].maxHeight = Math.max(tree[k << 1].maxHeight, tree[k << 1 | 1].maxHeight);
        tree[k].minHeight = Math.min(tree[k << 1].minHeight, tree[k << 1 | 1].minHeight);
    }

    public int query(int l, int r) {
        return query(l, r, 1, true) - query(l, r, 1, false);
    }

    public int query(int l, int r, int k, boolean isMax) {

        if (tree[k].l == l && tree[k].r == r) {
            return isMax ? tree[k].maxHeight : tree[k].minHeight;
        }

        int mid = tree[k].l + (tree[k].r - tree[k].l) / 2;
        // System.out.println(l + "," + r + "," + k + "," + mid);
        if (r <= mid) {
            return query(l, r, k << 1, isMax);
        } else if (l > mid) {
            return query(l, r, k << 1 | 1, isMax);
        } else {
            if (isMax) {
                return Math.max(query(l, mid, k << 1, isMax), query(mid + 1, r, k << 1 | 1, isMax));
            } else {
                return Math.min(query(l, mid, k << 1, isMax), query(mid + 1, r, k << 1 | 1, isMax));
            }
        }
    }

}

class TreeNode {
    public int minHeight;
    public int maxHeight;
    public int l, r;
}