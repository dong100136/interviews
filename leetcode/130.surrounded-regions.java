/*
 * @lc app=leetcode id=130 lang=java
 *
 * [130] Surrounded Regions
 *
 * https://leetcode.com/problems/surrounded-regions/description/
 *
 * algorithms
 * Medium (21.43%)
 * Total Accepted:    125.1K
 * Total Submissions: 583.8K
 * Testcase Example:  '[["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]'
 *
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions
 * surrounded by 'X'.
 * 
 * A region is captured by flipping all 'O's into 'X's in that surrounded
 * region.
 * 
 * Example:
 * 
 * 
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * 
 * 
 * After running your function, the board should be:
 * 
 * 
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * 
 * 
 * Explanation:
 * 
 * Surrounded regions shouldn’t be on the border, which means that any 'O' on
 * the border of the board are not flipped to 'X'. Any 'O' that is not on the
 * border and it is not connected to an 'O' on the border will be flipped to
 * 'X'. Two cells are connected if they are adjacent cells connected
 * horizontally or vertically.
 * 
 */
// class Solution {
//     private int n;
//     private int m;
//     private int[] ids;
//     public void solve(char[][] board) {
//         Set<Integer> set = new HashSet<Integer>();
        
//         n = board.length;
//         if (n==0) return;
//         m = board[0].length;
//         if (m==0) return;

//         ids = new int[n*m];
//         for (int i = 0;i<n;i++){
//             for (int j = 0;j<m;j++){
//                 int id = i*m+j;
//                 ids[id] = id;
//             }
//         }

//         for (int i = 0;i<n;i++){
//             for (int j =0;j<m;j++){
//                 if (board[i][j]=='O'){
//                     int id = i*m+j;

//                     if (i-1>=0 && board[i-1][j]=='O'){
//                         union(id,id-m);
//                     }

//                     if (j-1>=0 && board[i][j-1]=='O'){
//                         union(id,id-1);
//                     }
//                 }
//             }
//         }

//         for (int i = 0;i<n;i++){
//             if (board[i][0]=='O'){
//                 set.add(find(i*m));
//             }
//             if (board[i][m-1]=='O'){
//                 set.add(find(i*m+m-1));
//             }
//         }

//         for (int i = 0;i<m;i++){
//             if (board[0][i]=='O'){
//                 set.add(find(i));
//             }
//             if (board[n-1][i]=='O'){
//                 set.add(find((n-1)*m+i));
//             }
//         }

//         for (int i = 0;i<n;i++){
//             for (int j =0;j<m;j++){
//                 if (board[i][j]=='O' && !set.contains(find(i*m+j))){
//                     board[i][j]='X';
//                 }
//             }
//         }
//     }

//     public int find(int id){
//         if (id==ids[id]) return ids[id];
//         int p = find(ids[id]);
//         ids[id] = p;
//         return p;
//     }

//     public void union(int p,int q){
//         p = find(p);
//         q = find(q);
//         if (p==q) return;
//         ids[p] = q;
//     }
// }

class Solution {
    private int n,m;
    private int[][] marks;

    public void solve(char[][] board) {
        n = board.length;
        if (n==0) return;
        m = board[0].length;
        if (m==0) return;

        marks = new int[n][m];
        for (int i =0;i<n;i++){
            find(board,i,0);
            find(board,i,m-1);
        }

        for (int i=0;i<m;i++){
            find(board,0,i);
            find(board,n-1,i);
        }

        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                if (marks[i][j]!=-1 && board[i][j]=='O'){
                    board[i][j]='X';
                }
            }
        }
    }

    public void find(char[][] board,int i,int j){
        if (i<0 || i>=n || j<0 || j>=m || board[i][j]=='X' || marks[i][j]==-1){
            return;
        }

        marks[i][j] = -1;
        find(board,i+1,j);
        find(board,i-1,j);
        find(board,i,j-1);
        find(board,i,j+1);
    }
}
