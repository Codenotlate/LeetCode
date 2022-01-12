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



// Phase3 self
class Solution {
    public int minPathSum(int[][] grid) {
        //M1: top - down time O(m* n) space O(m * n)
        int m = grid.length;
        int n = grid[0].length;
        int[][] memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, grid, memo, m, n);
    }
    
    private int dfs(int i, int j, int[][] grid, int[][] memo, int m, int n) {
        if (i == m || j == n) {return 1000;}
        if (i == m - 1 && j == n - 1) {return grid[i][j];}
        if (memo[i][j] != -1) {return memo[i][j];}
        memo[i][j] = grid[i][j] + Math.min(dfs(i + 1, j, grid, memo, m, n), dfs(i, j + 1, grid, memo, m, n));
        return memo[i][j];
    }
}



class Solution {
    public int minPathSum(int[][] grid) {
        // M2 bottom-up and optimize space to O(n)
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        Arrays.fill(dp, 30000); // given the n <= 200 and grid[i][j] <= 100
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // pay attention to deal with i, j = 0 special cases
                if (j == 0) {
                    if (i == 0) {dp[0] = grid[0][0]; continue;}
                    dp[j] += grid[i][j];
                    continue;
                }
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        } 
        return dp[n - 1];
    }
}






// Review self
class Solution {
    // M1: Top down dp, starting from right bottom pos, dp[i][j] represents pathSum from (i, j) to (m-1, n-2).
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (i == m - 1 && j == n-1) {dp[j] = grid[i][j]; continue;}
                int down = i == m-1? Integer.MAX_VALUE : dp[j];
                int right = j == n-1? Integer.MAX_VALUE: dp[j+1];
                dp[j] = grid[i][j] + Math.min(down, right);
            }
        }
        return dp[0];
    }
}


class Solution {
    // M2: bottom up dp, starting from left upper pos, dp[i][j] represents pathSum from (0, 0) to (i, j).
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {dp[j] = grid[i][j]; continue;}
                int up = i == 0? Integer.MAX_VALUE : dp[j];
                int left = j == 0? Integer.MAX_VALUE: dp[j-1];
                dp[j] = grid[i][j] + Math.min(up, left);
            }
        }
        return dp[n-1];
    }
}