/*Initial thought
since the shape can only be 1*k or k*1 and they will be separated by at least one vertical or horizontal cell. Thus we can do one pass, starting from left upper corner and move row by row. For each'X' cell(i,j) we encounter, we only count++ if cell (i,j-1) or (i-1,j) is not 'X'. In this way, we make sure we only count in the top cell for vertical ones and left cell for horizontal ones.

time O(mn) space O(1)
This way solved the followup
*/
class Solution {
    public int countBattleships(char[][] board) {
        int res = 0;
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'X') {
                    boolean up = i == 0? true: board[i-1][j] != 'X';
                    boolean left = j == 0? true : board[i][j-1] != 'X';
                    if(left && up) {res++;}
                }
            }
        }
        return res;
    }
}