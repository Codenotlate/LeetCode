class Solution {
    // phase3 self
    // M1, use one set for each col, one set for each row, and one set for each 3*3 block. For the set, we can use hashset or an array from 0 to 9 to label.
    // time O(n^2), space O(n^2) or O(1) since n = 9
    public boolean isValidSudoku(char[][] board) {
        int[][] cols = new int[9][9];
        int[][] rows = new int[9][9];
        int[][] blocks = new int[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    // check same row
                    if (rows[i][num] != 0) {return false;}
                    if (cols[j][num] != 0) {return false;}
                    if (blocks[i / 3 + j / 3 * 3][num] != 0) {return false;}
                    rows[i][num] = 1;
                    cols[j][num] = 1;
                    blocks[i/3 + j/3 * 3][num] = 1;
                }  
            }
        }
        return true;
    }
}

// can also do bitmasking - using a 9 digits binary number to replace the array to label visited
// don't think it's necessary to learn for now

// Review self
class Solution {
    /* initial thought
    since only filled cells need to be checked. We can maintain three arrays for each cell. one for row, one for col, and another one for 3*3 space it is in. If any of the cell has been visited in any of the array, return false.
    time O(9*9) space O(3*9*9)
    */
    public boolean isValidSudoku(char[][] board) {
        int[][] rows = new int[9][9];
        int[][] cols = new int[9][9];
        int[][] boxes = new int[9][9];
        
        for (int i = 0; i< board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {continue;}
                int num = board[i][j] - '0' - 1;
                if (rows[i][num] != 0 || cols[j][num] != 0 || boxes[i/3 *3 + j / 3][num] != 0) {return false;}
                rows[i][num] = 1;
                cols[j][num] = 1;
                boxes[i/3*3+j/3][num] = 1;
            }
        }
        return true;
    }
}