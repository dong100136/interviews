
/*
Description

There are several ancient Greek texts that contain descriptions of the fabled island Atlantis. Some of these texts even include maps of parts of the island. But unfortunately, these maps describe different regions of Atlantis. Your friend Bill has to know the total area for which maps exist. You (unwisely) volunteered to write a program that calculates this quantity.
Input

The input consists of several test cases. Each test case starts with a line containing a single integer n (1 <= n <= 100) of available maps. The n following lines describe one map each. Each of these lines contains four numbers x1;y1;x2;y2 (0 <= x1 < x2 <= 100000;0 <= y1 < y2 <= 100000), not necessarily integers. The values (x1; y1) and (x2;y2) are the coordinates of the top-left resp. bottom-right corner of the mapped area. 
The input file is terminated by a line containing a single 0. Don't process it.
Output

For each test case, your program should output one section. The first line of each section must be "Test case #k", where k is the number of the test case (starting with 1). The second one must be "Total explored area: a", where a is the total explored area (i.e. the area of the union of all rectangles in this test case), printed exact to two digits to the right of the decimal point. 
Output a blank line after each test case.
Sample Input

2
10 10 20 20
15 15 25 25.5
0
Sample Output

Test case #1
Total explored area: 180.00 

----
5
1.37 0.10 2.75 0.20
2.96 2.01 3.06 2.75
2.65 1.05 4.97 1.15
2.33 0.74 4.02 0.84
2.96 0.10 4.02 1.47
8
9.33 19.52 30.44 24.39
8.06 29.38 9.75 51.77
22.38 15.06 32.35 33.30
30.66 19.20 44.13 19.62
17.93 15.38 39.35 41.58
9.65 6.47 29.80 10.39
26.20 24.61 40.63 35.85
25.25 12.51 29.80 36.17
8
157.98 278.30 246.25 501.22
152.89 15.06 355.43 214.74
61.53 56.12 141.85 96.97
21.43 303.77 250.07 329.65
5.83 115.65 306.10 211.88
275.44 132.52 353.21 276.49
149.71 175.49 271.40 379.94
127.42 32.57 187.05 282.22
0
--》1.85
--》 751.10
--》 99910.16
*/

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int testCase = 1;

        while (n != 0) {
            List<Line> lines = new ArrayList<Line>();
            Set<Double> ySet = new HashSet<Double>();
            for (int i = 0; i < n; i++) {
                Double x1 = sc.nextDouble();
                Double y1 = sc.nextDouble();
                Double x2 = sc.nextDouble();
                Double y2 = sc.nextDouble();
                lines.add(new Line(x1, y1, y2, 1));
                lines.add(new Line(x2, y1, y2, -1));
                ySet.add(y1);
                ySet.add(y2);
            }

            List<Double> y = new ArrayList<Double>(ySet);
            SegmentTree tree = new SegmentTree(y);

            Collections.sort(lines, new Comparator<Line>() {
                @Override
                public int compare(Line a, Line b) {
                    return a.x - b.x > 0 ? 1 : -1;
                }
            });

            double ylen = 0;
            double lastX = 0;
            double rs = 0;
            for (int i = 0; i < lines.size(); i++) {
                Line line = lines.get(i);
                double xlen = line.x - lastX;

                // System.out.println("xlen:" + xlen + ",ylen:" + ylen);
                rs += xlen * ylen;

                tree.update(line);
                ylen = tree.getLen();
                lastX = line.x;

                // for (TreeNode node : tree.nodes) {
                // System.out.println(node);
                // }
            }

            System.out.println(String.format("Test case #%d\nTotal explored area: %.2f\n", testCase++, rs));

            n = sc.nextInt();
        }
    }
}

class SegmentTree {
    private int n;
    private List<Double> y;
    private Map<Double, Integer> y2index = new HashMap<Double, Integer>();
    private Map<Integer, Double> index2y = new HashMap<Integer, Double>();
    public TreeNode[] nodes;

    public SegmentTree(List<Double> y) {
        this.y = y;
        this.buildYIndex();

        this.nodes = new TreeNode[this.n << 2];
        buildTree(1, n, 1);
    }

    public void buildYIndex() {
        Collections.sort(this.y);
        this.n = this.y.size();
        for (int i = 0; i < n; i++) {
            // System.out.println(this.y.get(i) + "," + (i + 1));
            this.y2index.put(this.y.get(i), i);
            this.index2y.put(i, this.y.get(i));
        }
    }

    public void buildTree(int l, int r, int k) {
        TreeNode node = new TreeNode();
        this.nodes[k] = node;
        node.l = l;
        node.r = r;

        if (l == r) {
            return;
        }

        int mid = l + (r - l) / 2;
        buildTree(l, mid, k << 1);
        buildTree(mid + 1, r, k << 1 | 1);
    }

    public void update(Line line) {
        int l = this.y2index.get(line.y1) + 1;
        int r = this.y2index.get(line.y2);
        // System.out.println(l + "," + r + "," + line.op);
        update(l, r, 1, line.op);
    }

    public void update(int l, int r, int k, int op) {
        // op=1 or -1,mean add segment line or remove it

        // System.out.println(l + "," + r);
        TreeNode node = this.nodes[k];
        // if (node.l == l && node.r == r) {
        if (node.l == node.r) {
            node.cnt += op;
            return;
        }

        int mid = node.l + (node.r - node.l) / 2;
        if (node.cnt > 0) {
            update(node.l, mid, k << 1, node.cnt);
            update(mid + 1, node.r, k << 1 | 1, node.cnt);
            node.cnt = 0;
        }

        if (r <= mid) {
            update(l, r, k << 1, op);
        } else if (l > mid) {
            update(l, r, k << 1 | 1, op);
        } else {
            update(l, mid, k << 1, op);
            update(mid + 1, r, k << 1 | 1, op);
        }

        // int a = this.nodes[k << 1].cnt;
        // int b = this.nodes[k << 1 | 1].cnt;
        // int up = a > b ? a - b : b - a;
        // node.cnt += up;
        // this.nodes[k << 1].cnt = a - up;
        // this.nodes[k << 1 | 1].cnt = b - up;
    }

    public double getLen() {
        return getLen(1, this.n, 1);
    }

    public double getLen(int l, int r, int k) {
        TreeNode node = this.nodes[k];
        if (node.cnt > 0) {
            return this.index2y.get(r) - this.index2y.get(l - 1);
        }

        if (node.l == node.r) {
            return 0;
        }

        int mid = node.l + (node.r - node.l) / 2;
        double a = getLen(l, mid, k << 1);
        double b = getLen(mid + 1, r, k << 1 | 1);
        return a + b;
    }
}

class TreeNode {
    int l, r;
    int cnt = 0;

    public String toString() {
        return String.format("l %d, r %d, cnt %d", l, r, cnt);
    }
}

class Line {
    public double x, y1, y2;
    public int op;

    public Line(double x, double y1, double y2, int op) {
        this.x = x;
        this.y1 = y1;
        this.y2 = y2;
        this.op = op;
    }
}