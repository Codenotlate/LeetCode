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