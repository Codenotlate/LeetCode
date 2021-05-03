class Solution {
    // use 2d dp, start from bottom right corner
    // dp[i][j] represents the min path sum from grid[i][j] to grid[m-1][n-1]
    public int minPathSum(int[][] grid) {
    	int m = grid.length;
    	int n = grid[0].length;
    	int[][] dp = new int[m][n];
    	
    	for (int i = m - 1; i >= 0; i--) {
    		for (int j = n - 1; j >= 0; j--) {
    			if (i == m - 1 && j == n - 1) {
    				dp[i][j] = grid[i][j];
    			} else if (i == m - 1) {
    				dp[i][j] = grid[i][j] + dp[i][j + 1];
    			} else if (j == n - 1) {
    				dp[i][j] = grid[i][j] + dp[i + 1][j];
    			} else {
    				dp[i][j] = grid[i][j] + Math.min(dp[i + 1][j], dp[i][j + 1]);
    			}
    		}
    	}
    	return dp[0][0];       
    }
}




class Solution {
	// dp optimized based on above dp, use O(n) space instead of O(n^2)
    public int minPathSum(int[][] grid) {
    	int m = grid.length;
    	int n = grid[0].length;
    	int[] dp = new int[n];
    	for (int i = m - 1; i >= 0; i--) {
    		for (int j = n - 1; j >= 0; j--) {
    			// note j = n - 1 should be put before i = m - 1
    			// guarantee dp[j + 1] not out of index range
    			if (j == n - 1) {
    				dp[j] = dp[j];
    			} else if (i == m - 1) {
    				dp[j] = dp[j + 1]; 
    			} else {
    				dp[j] = Math.min(dp[j], dp[j + 1]);
    			}
    			dp[j] += grid[i][j];
    		}
    	}
    	return dp[0];
        
    }
}


// thinking from recursion to dp
// https://leetcode.com/problems/minimum-path-sum/discuss/344980/Java.-Details-from-Recursion-to-DP.