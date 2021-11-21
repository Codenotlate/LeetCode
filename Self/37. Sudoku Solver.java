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



class Solution {
    // phase3 self: DFS + backtracking
    // similar as 36, use arrays cols\rows\blocks to track numbers used
    // time O(9^m) where m represents the number of unfilled cells in board
    public void solveSudoku(char[][] board) {
        int[][] cols = new int[9][9];
        int[][] rows = new int[9][9];
        int[][] blocks = new int[9][9];
        // update cols\rows\blocks with already filled cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    cols[j][num] = 1;
                    rows[i][num] = 1;
                    blocks[i / 3 + j / 3 * 3][num] = 1;
                }
            }
        }
        
        dfs(board, 0, 0, cols, rows, blocks);
    }
    
    private boolean dfs(char[][] board, int i, int j, int[][] cols, int[][] rows, int[][] blocks) {
        // end condition
        if (i*9 + j >= 81) {return true;}
        if (board[i][j] != '.') { // cell is filled already
            if (j < 8) {return dfs(board, i, j+1, cols, rows, blocks);}
            else {return dfs(board, i+1, 0, cols, rows, blocks);}
        } else { // unfilled cell
            for (char k = '1'; k <= '9'; k++) {
                if (isValid(i, j, k-'1', cols, rows, blocks)) {
                    // fill and update
                    board[i][j] = k;
                    cols[j][k-'1'] = 1;
                    rows[i][k-'1'] = 1;
                    blocks[i / 3 + j / 3 * 3][k-'1'] = 1;
                    if (j < 8) {if(dfs(board, i, j+1, cols, rows, blocks)) {return true;}}
                    else {if(dfs(board, i+1, 0, cols, rows, blocks)){return true;}}
                    // backtracking: unfill and unchange
                    board[i][j] = '.';
                    cols[j][k-'1'] = 0;
                    rows[i][k-'1'] = 0;
                    blocks[i / 3 + j / 3 * 3][k-'1'] = 0;
                }
            }
            
        }
        return false;
    }
    
    
    private boolean isValid(int i, int j, int num, int[][] cols, int[][] rows, int[][] blocks) {
        return cols[j][num] != 1 && rows[i][num] != 1 && blocks[i/3 + j /3 * 3][num] != 1;
    }
}





// https://leetcode.com/problems/sudoku-solver/discuss/15903/Two-very-Simple-and-Neat-Java-DFSBacktracking-solutions
// shows two ways












