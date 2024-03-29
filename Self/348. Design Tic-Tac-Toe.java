/*Initial thought
track the number of each player in each row, each col and two diagonals
when do move, we can add the number in corresponding row, col and diag. If any of it reaches n, this player wins, return this player
each move should be O(1) time.

from the solution: further optimizing, instead of using two sets of row/col/diag for two players, we use only one set, and player1 do count++, player2 do count--. For player1 winning condition is count == n, and for player 2 count==-n.
*/
class TicTacToe {
    int[] rows;
    int[] cols;
    int[] diags; // diags[0] for i=j diag, diags[1] for i+j = n-1 diag
    int win;

    public TicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
        diags = new int[2];
        win = n;
    }
    
    public int move(int row, int col, int player) {
        int increment = player % 2 == 0 ? -1 : 1;
        int winCount = player % 2 == 0? -win : win;
        
        rows[row] += increment;
        cols[col] += increment;
        if (row == col) {diags[0] += increment;}
        if (row + col == rows.length - 1) {diags[1] += increment;}
        if (rows[row] == winCount || cols[col] == winCount || diags[0] == winCount || diags[1] == winCount) {return player;}
        return 0;
    }
}

/*
check the solution with optimized path from brute-force O(n^2) way to slightly optimized time O(n) space O(n^2) (board space itself) way,
and finally to time O(1) space O(n) way.
*/






// Review
/*Thought
initial a mat[n][n].
for each move, set mat[r][c] = player. And check the row r, column c, and if r == c, check the left diag, or if (r + c == n-1), check right diag. This will take O(n) time.
Further improved way: if we label player -1 and 1 and keep track of the sum of each row and each col as well as the two diags. Then for each move, we just need to add (1/-1) to the current sum of row r, col c and two diags if applicable. And check is the abs(sum) == n to determine if the game ends. This will take O(n) for each move.
*/

class TicTacToe {
    int[] rowSum;
    int[] colSum;
    int[] diagsSum;
    int count;

    public TicTacToe(int n) {
        count = n;
        rowSum = new int[n];
        colSum = new int[n];
        diagsSum = new int[2]; // 0 for left one and 1 for right one,
    }
    
    public int move(int row, int col, int player) {
        int label = player == 1 ? -1 : 1;
        rowSum[row] += label;
        colSum[col] += label;
        if (row == col) {diagsSum[0] += label;}
        if (row + col == count - 1) {diagsSum[1] += label;} // should be if instead of else if here
        if (Math.abs(rowSum[row]) == count || Math.abs(colSum[col]) == count || Math.abs(diagsSum[0]) == count || Math.abs(diagsSum[1]) == count) {return player;}
        return 0;
    }
}
