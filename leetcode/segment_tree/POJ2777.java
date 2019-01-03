import java.util.Scanner;

// 颜色覆盖问题
/*
Description

Chosen Problem Solving and Program design as an optional course, you are required to solve all kinds of problems. Here, we get a new problem. 

There is a very long board with length L centimeter, L is a positive integer, so we can evenly divide the board into L segments, and they are labeled by 1, 2, ... L from left to right, each is 1 centimeter long. Now we have to color the board - one segment with only one color. We can do following two operations on the board: 

1. "C A B C" Color the board from segment A to segment B with color C. 
2. "P A B" Output the number of different colors painted between segment A and segment B (including). 

In our daily life, we have very few words to describe a color (red, green, blue, yellow…), so you may assume that the total number of different colors T is very small. To make it simple, we express the names of colors as color 1, color 2, ... color T. At the beginning, the board was painted in color 1. Now the rest of problem is left to your. 
Input

First line of input contains L (1 <= L <= 100000), T (1 <= T <= 30) and O (1 <= O <= 100000). Here O denotes the number of operations. Following O lines, each contains "C A B C" or "P A B" (here A, B, C are integers, and A may be larger than B) as an operation defined previously.
Output

Ouput results of the output operation in order, each line contains a number.
Sample Input

2 2 4
C 1 1 2
P 1 2
C 2 2 2
P 1 2

Sample Output

2
1


-------
5 3 5
C 1 3 2
P 2 4
C 3 5 3
P 1 5
P 3 4

---
2
2
1

*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int L = sc.nextInt();
        int T = sc.nextInt();
        int O = sc.nextInt();

        String v = sc.nextLine();

        SegmentTree tree = new SegmentTree(L);
        tree.update(1, L, 1, 1);

        int[] rs = new int[O];
        int j = 0;
        for (int i = 0; i < O; i++) {
            String[] line = sc.nextLine().split(" ");
            // for (String v : line) {
            // System.out.println(v);
            // }
            // System.out.println("--------");
            // System.out.println("=" + line.length);
            String o = line[0];
            if (o.equals("C")) {
                int l = Integer.valueOf(line[1]);
                int r = Integer.valueOf(line[2]);
                int c = Integer.valueOf(line[3]);

                if (l > r) {
                    int t = r;
                    r = l;
                    l = t;
                }
                tree.update(l, r, c, 1);
            } else if (o.equals("P")) {
                int l = Integer.valueOf(line[1]);
                int r = Integer.valueOf(line[2]);
                if (l > r) {
                    int t = r;
                    r = l;
                    l = t;
                }
                int count = tree.search(l, r, 1);
                int c = 0;
                for (c = 0; count > 0; c++) {
                    count &= (count - 1);
                }
                System.out.println(c);
                // rs[j++] = c;
            }
        }
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

    public void update(int l, int r, int c, int k) {
        TreeNode node = this.nodes[k];
        // System.out.println(k);
        // System.out.println(l + "," + r);
        // System.out.println(node);
        if (node.l == l && node.r == r) {
            node.color = c;
            return;
        }

        // go down
        if (this.nodes[k].color != -1) {
            this.nodes[k << 1].color = node.color;
            this.nodes[k << 1 | 1].color = node.color;
            this.nodes[k].color = -1;
        }

        int mid = node.l + (node.r - node.l) / 2;
        if (r <= mid) {
            update(l, r, c, k << 1);
        } else if (l > mid) {
            update(l, r, c, k << 1 | 1);
        } else {
            update(l, mid, c, k << 1);
            update(mid + 1, r, c, k << 1 | 1);
        }

        // 检查左右子树的情况
        if (this.nodes[k << 1].color == this.nodes[k << 1 | 1].color) {
            this.nodes[k].color = this.nodes[k << 1].color;
        }
    }

    public int search(int l, int r, int k) {
        if (this.nodes[k].color != -1) {
            return 1 << this.nodes[k].color;
        }
        int mid = (this.nodes[k].l + this.nodes[k].r) / 2;
        if (r <= mid) {
            return search(l, r, k << 1);
        } else if (l > mid) {
            return search(l, r, k << 1 | 1);
        } else {
            int a = search(l, mid, k << 1);
            int b = search(mid + 1, r, k << 1 | 1);
            return a | b;
        }
    }
}

class TreeNode {
    int l, r;
    int color = -1;

    public TreeNode(int l, int r) {
        this.l = l;
        this.r = r;
    }

    public String toString() {
        return String.format("node: l %d r %d color %d", l, r, color);
    }
}