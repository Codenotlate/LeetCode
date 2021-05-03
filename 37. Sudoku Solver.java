class Solution {
	// dfs: since we still need to test each possibility and backtrack if it's wrong answer
	// have the dfs return true to end the search early once find the answer
    public void solveSudoku(char[][] board) {
        solveHelper(board, 0);
    }

    private boolean solveHelper(char[][] board, int cell) {
    	// skip cells already have values
    	while (cell <= 80 && board[cell / 9][cell % 9] != '.') {cell++;}
    	// end case
    	if (cell > 80) {return true;}
    	
    	int i = cell / 9;
    	int j = cell % 9;
    	for (char c = '1'; c <= '9'; c++) {
    		if (isValid(board, i, j, c)) {
    			board[i][j] = c;
    			if (solveHelper(board, cell + 1)) {
    				return true;
    			}
    			board[i][j] = '.';
    		}
    	}
    	return false;
    }

    // return true if c is valid for row, column and 3 * 3 box
    private boolean isValid(char[][] board, int i, int j, char c) {
    	for (int k = 0; k < 9; k++) {
    		if (board[i][k] == c || board[k][j] == c 
    			|| board[i / 3 * 3 + k / 3][j / 3 * 3 + k % 3] == c) {
    			return false;
    		}
    	}
    	return true;
    }
}