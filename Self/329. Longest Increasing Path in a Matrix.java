// Phase3 self
// time O(m*n)  space O(m * n)
// M1: DFS + memo
class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] memo = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, getLen(matrix, i, j, memo));
            }
        }
        return res;
    }
    
    
    private int getLen(int[][] matrix, int i, int j, int[][] memo) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (i >= m || j >= n) {return 0;} // this line can be removed since we check i and j before pass in below
        if (memo[i][j] != 0) {return memo[i][j];}
        // can move neighbors array as class var, 
        int[][] neighbors = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        memo[i][j] = 1; // can also remove this line and change below to memo[i][j] = Math.max(memo[i][j], getLen(matrix, newi, newj, memo)); return ++memo[i][j]
        for (int[] neighbor: neighbors) {
            int newi = i + neighbor[0];
            int newj = j + neighbor[1];
            if (newi < m && newi >= 0 && newj < n && newj >=0 && matrix[newi][newj] > matrix[i][j]) {
                memo[i][j] = Math.max(memo[i][j], getLen(matrix, newi, newj, memo) + 1);
            }
        }
        return memo[i][j];
    }
}



// future: review DP way in solution
// https://leetcode.com/problems/longest-increasing-path-in-a-matrix/solution/