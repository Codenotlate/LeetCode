/*Thought
DP not working, just DFS starting from each non-zero cell and backtesting.
time O(k*3^k) where k = # of nonzero cells and k <= 25
change the value to 0 to work as label visited. Since we do backtracking, in the end there matrix is unchanged. Space O(k)
*/
class Solution {
    public int getMaximumGold(int[][] grid) {
        int max = 0;
        for (int i = 0;  i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {continue;}
                max = Math.max(max, getSum(grid, i,j));
            }
        }
        return max;
    }
    
    private int getSum(int[][] grid, int i, int j) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        int curnum = grid[i][j];
        int maxsum = 0;
        grid[i][j] = 0;
        
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && grid[newi][newj] != 0) {
                maxsum = Math.max(maxsum, getSum(grid,newi, newj));
            }
        }
        
        grid[i][j] = curnum;
        return curnum + maxsum;
    }
}

// though with high runtime, the idea is the same with those faster submissions. should be good enough for interview.