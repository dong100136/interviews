import java.util.HashMap;
import java.util.Scanner;
import java.util.*;

import javax.swing.text.Segment;

// 贴简历问题
/*
Description

The citizens of Bytetown, AB, could not stand that the candidates in the mayoral election campaign have been placing their electoral posters at all places at their whim. The city council has finally decided to build an electoral wall for placing the posters and introduce the following rules: 
Every candidate can place exactly one poster on the wall. 
All posters are of the same height equal to the height of the wall; the width of a poster can be any integer number of bytes (byte is the unit of length in Bytetown). 
The wall is divided into segments and the width of each segment is one byte. 
Each poster must completely cover a contiguous number of wall segments.

They have built a wall 10000000 bytes long (such that there is enough place for all candidates). When the electoral campaign was restarted, the candidates were placing their posters on the wall and their posters differed widely in width. Moreover, the candidates started placing their posters on wall segments already occupied by other posters. Everyone in Bytetown was curious whose posters will be visible (entirely or in part) on the last day before elections. 
Your task is to find the number of visible posters when all the posters are placed given the information about posters' size, their place and order of placement on the electoral wall. 
Input

The first line of input contains a number c giving the number of cases that follow. The first line of data for a single case contains number 1 <= n <= 10000. The subsequent n lines describe the posters in the order in which they were placed. The i-th line among the n lines contains two integer numbers li and ri which are the number of the wall segment occupied by the left end and the right end of the i-th poster, respectively. We know that for each 1 <= i <= n, 1 <= li <= ri <= 10000000. After the i-th poster is placed, it entirely covers all wall segments numbered li, li+1 ,... , ri.

Sample Input

1
5
1 4
2 6
8 10
3 4
7 10
Sample Output

4

---
1
3
5 6
4 5
6 8

2

*/

class Main {
    private static int len = 10000000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int c = sc.nextInt();
        for (int i = 0; i < c; i++) {
            int n = sc.nextInt();

            Set<Integer> numsSet = new HashSet<Integer>();
            int[][] op = new int[n][2];

            for (int j = 0; j < n; j++) {
                op[j][0] = sc.nextInt();
                op[j][1] = sc.nextInt();
                numsSet.add(op[j][0]);
                numsSet.add(op[j][1]);
            }
            List<Integer> nums = new ArrayList<Integer>(numsSet);
            Collections.sort(nums);

            // 离散化
            Map<Integer, Integer> numDict = new HashMap<Integer, Integer>();
            for (int j = 0; j < nums.size(); j++) {
                numDict.put(nums.get(j), j + 1);
                // System.out.println("#" + nums.get(j) + "," + (j + 1));
            }

            SegmentTree tree = new SegmentTree(nums.size());
            for (int j = 0; j < n; j++) {
                int l = numDict.get(op[j][0]);
                int r = numDict.get(op[j][1]);
                // System.out.println("-" + l + "," + r + "," + j);
                tree.update(l, r, j, 1);
            }

            int[] count = new int[n];
            tree.search(1, nums.size(), 1, count);
            int rs = 0;
            for (int j = 0; j < n; j++) {
                if (count[j] > 0)
                    rs += 1;
            }
            System.out.println(rs);

            // for (int j = 0; j < 10; j++) {
            // System.out.println(tree.nodes[j]);
            // }
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

    public void search(int l, int r, int k, int[] count) {
        if (this.nodes[k].color != -1) {
            count[this.nodes[k].color] = 1;
            return;
        }

        if (this.nodes[k].l == this.nodes[k].r) {
            return;
        }

        int mid = (this.nodes[k].l + this.nodes[k].r) / 2;
        if (r <= mid) {
            search(l, r, k << 1, count);
        } else if (l > mid) {
            search(l, r, k << 1 | 1, count);
        } else {
            search(l, mid, k << 1, count);
            search(mid + 1, r, k << 1 | 1, count);
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
