import java.util.Scanner;

// 颜色覆盖问题
/*
Painting some colored segments on a line, some previously painted segments may be covered by some the subsequent ones.
Your task is counting the segments of different colors you can see at last.


Input

The first line of each data set contains exactly one integer n, 1 <= n <= 8000, equal to the number of colored segments.

Each of the following n lines consists of exactly 3 nonnegative integers separated by single spaces:

x1 x2 c

x1 and x2 indicate the left endpoint and right endpoint of the segment, c indicates the color of the segment.

All the numbers are in the range [0, 8000], and they are all integers.

Input may contain several data set, process to the end of file.


Output

Each line of the output should contain a color index that can be seen from the top, following the count of the segments of this color, they should be printed according to the color index.

If some color can't be seen, you shouldn't print it.

Print a blank line after every dataset.


Sample Input

5
0 4 4
0 3 1
3 4 2
0 2 2
0 2 3
4
0 1 1
3 4 1
1 3 2
1 3 1
6
0 1 0
1 2 1
2 3 1
1 2 0
2 3 0
1 2 1


Sample Output

1 1
2 1
3 1

1 1

0 2
1 1
*/

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {

            int n = sc.nextInt();
            int max_color_idx = 0;

            // input
            int[][] update_op = new int[n][3];
            int max_r = 0;
            for (int i = 0; i < n; i++) {
                int l = sc.nextInt();
                int r = sc.nextInt();
                int c = sc.nextInt();
                if (c > max_color_idx) {
                    max_color_idx = c + 1;
                }
                if (r > max_r) {
                    max_r = r;
                }
                update_op[i][0] = l;
                update_op[i][1] = r;
                update_op[i][2] = c;
            }

            SegmentTree tree = new SegmentTree(max_r);
            for (int i = 0; i < n; i++) {
                tree.update(update_op[i][0] + 1, update_op[i][1], update_op[i][2], 1);
            }

            int[] colors = new int[max_color_idx];
            tree.update(1);

            tree.search(colors);

            for (int i = 0; i < max_color_idx; i++) {
                if (colors[i] != 0) {
                    System.out.println(i + "," + colors[i]);
                }
            }
            System.out.println();
        }
    }
}

class SegmentTree {
    public TreeNode[] nodes;

    public SegmentTree(int n) {
        this.nodes = new TreeNode[n << 2];
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
    }

    public void update(int k) {
        TreeNode node = this.nodes[k];
        if (node.l == node.r)
            return;
        if (node.color != -1) {
            this.nodes[k << 1].color = node.color;
            this.nodes[k << 1 | 1].color = node.color;
            this.nodes[k].color = -1;
        }
        update(k << 1);
        update(k << 1 | 1);
    }

    public void search(int[] colors) {
        for (int i = 2; i < this.nodes.length; i++) {
            if (this.nodes[i] != null && this.nodes[i].color != -1 && this.nodes[i - 1].color != this.nodes[i].color) {
                // System.out.println(i);
                colors[this.nodes[i].color]++;
            }
        }
    }
}

class TreeNode {
    int l, r;
    int color = -1;
    int color_num = 0;

    public TreeNode(int l, int r) {
        this.l = l;
        this.r = r;
    }

    public String toString() {
        return String.format("node: l %d r %d color_num %d color %d", l, r, color_num, color);
    }
}