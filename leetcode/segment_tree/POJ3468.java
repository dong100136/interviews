import java.util.Scanner;

// 颜色覆盖问题
/*
Description

You have N integers, A1, A2, ... , AN. You need to deal with two kinds of operations. One type of operation is to add some given number to each number in a given interval. The other is to ask for the sum of numbers in a given interval.

Input

The first line contains two numbers N and Q. 1 ≤ N,Q ≤ 100000.
The second line contains N numbers, the initial values of A1, A2, ... , AN. -1000000000 ≤ Ai ≤ 1000000000.
Each of the next Q lines represents an operation.
"C a b c" means adding c to each of Aa, Aa+1, ... , Ab. -10000 ≤ c ≤ 10000.
"Q a b" means querying the sum of Aa, Aa+1, ... , Ab.

Output

You need to answer all Q commands in order. One answer in a line.

Sample Input

10 5
1 2 3 4 5 6 7 8 9 10
Q 4 4
Q 1 10
Q 2 4
C 3 6 3
Q 2 4
Sample Output

4
55
9
15

-----
10 22
1 2 3 4 5 6 7 8 9 10
Q 4 4
C 1 10 3
C 6 10 3
C 6 9 3
C 8 9 -100
C 7 9 3
C 7 10 3
C 1 10 3
Q 6 10
Q 6 9
Q 8 9
Q 7 9
Q 7 10
Q 1 10
Q 2 4
C 3 6 3
Q 9 9
Q 1 1
Q 5 5
Q 6 6
Q 7 7
Q 6 8

ans

4
-82
-104
-147
-122
-100
-37
27
-73
7
14
21
25
-28

---

10 4
1 2 3 4 5 6 7 8 9 10
Q 1 10
C 5 10 -5
Q 1 10
Q 1 5
*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int O = sc.nextInt();

        SegmentTree tree = new SegmentTree(N);

        for (int i = 1; i <= N; i++) {
            int num = sc.nextInt();
            tree.update(i, i, num, 1);
        }

        String v = sc.nextLine();

        for (int i = 0; i < O; i++) {
            String[] line = sc.nextLine().split(" ");

            String o = line[0];
            if (o.equals("C")) {
                int l = Integer.valueOf(line[1]);
                int r = Integer.valueOf(line[2]);
                long update_num = Integer.valueOf(line[3]);

                if (l > r) {
                    int t = r;
                    r = l;
                    l = t;
                }
                tree.update(l, r, update_num, 1);
            } else if (o.equals("Q")) {
                int l = Integer.valueOf(line[1]);
                int r = Integer.valueOf(line[2]);
                if (l > r) {
                    int t = r;
                    r = l;
                    l = t;
                }
                long count = tree.search(l, r, 1);
                System.out.println(count);
            }
        }

        // for (int i = 1; i <= 3 * N; i++) {
        // System.out.println(tree.nodes[i]);
        // }
    }
}

class SegmentTree {
    public TreeNode[] nodes;

    public SegmentTree(int n) {
        this.nodes = new TreeNode[n << 1 + 1];
        this.build(1, n, 1);
    }

    public void build(int l, int r, int k) {
        this.nodes[k] = new TreeNode(l, r);
        if (l == r)
            return;
        int mid = l + (r - l) / 2;
        build(l, mid, k << 1);
        build(mid + 1, r, k << 1 | 1);
    }

    public void update(int l, int r, long update_num, int k) {
        TreeNode node = this.nodes[k];
        if (node.l == l && node.r == r) {
            node.update += update_num;
            node.sum += update_num * (node.r - node.l + 1);
            return;
        }

        // go down
        int mid = node.l + (node.r - node.l) / 2;
        if (this.nodes[k].update != 0) {
            update(node.l, mid, node.update, k << 1);
            update(mid + 1, node.r, node.update, k << 1 | 1);
            this.nodes[k].update = 0;
        }

        if (r <= mid) {
            update(l, r, update_num, k << 1);
        } else if (l > mid) {
            update(l, r, update_num, k << 1 | 1);
        } else {
            update(l, mid, update_num, k << 1);
            update(mid + 1, r, update_num, k << 1 | 1);
        }

        // 检查左右子树的情况
        long sum_l = this.nodes[k << 1].sum;
        long sum_r = this.nodes[k << 1 | 1].sum;
        node.sum = sum_l + sum_r + node.update * (node.r - node.l + 1);
    }

    public long search(int l, int r, int k) {
        if (this.nodes[k].l == l && this.nodes[k].r == r) {
            return this.nodes[k].sum;
        }
        int mid = (this.nodes[k].l + this.nodes[k].r) / 2;
        if (r <= mid) {
            return search(l, r, k << 1) + this.nodes[k].update * (r - l + 1);
        } else if (l > mid) {
            return search(l, r, k << 1 | 1) + this.nodes[k].update * (r - l + 1);
        } else {
            long a = search(l, mid, k << 1);
            long b = search(mid + 1, r, k << 1 | 1);
            return a + b + this.nodes[k].update * (r - l + 1);
        }
    }
}

class TreeNode {
    int l, r;
    long sum = 0;
    long update = 0;

    public TreeNode(int l, int r) {
        this.l = l;
        this.r = r;
    }

    public String toString() {
        return String.format("node: l %d r %d sum %d update %d", l, r, sum, update);
    }
}