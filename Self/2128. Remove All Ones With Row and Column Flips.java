/*Thought from solution
M1: Notice for the first row, if we have some 1's there, we need to flip those cols having 1, then the first row will all be 0. Then for all following rows, you are no along able to flip the col, cause you will change the first row. Thus you can only change the whole row for them, which requires them to be either all 0 or all 1 to be flipped to all 0. If any one of the following row is not all 0 or all 1, return false.
time O(mn) space O(1) which changes the grid

M2: find the pattern. without changing the grid. Notice if we want to succeed, we need all rows to be either exactly same pattern or opposite pattern. Thus we can use first row as the pattern and check every following rows with it.
time O(mn) space O(1) not changing the grid

*/
// M1:
class Solution {
    public boolean removeOnes(int[][] grid) {
        // change cols for first row
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[0][j] != 0) {flipCol(grid, j);}
        }
        
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                if (grid[i][0] != grid[i][j]) {return false;}
            }
        }
        return true;
    }
    
    private void flipCol(int[][] grid, int j) {
        for (int i = 0; i < grid.length; i++) {
            grid[i][j] = 1 - grid[i][j];
        }
    }
}

// M2:
class Solution {
    public boolean removeOnes(int[][] grid) {        
        for (int i = 1; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (Math.abs(grid[0][j] - grid[i][j]) != Math.abs(grid[0][0] - grid[i][0])) {return false;}
            }
        }
        return true;
    }
}