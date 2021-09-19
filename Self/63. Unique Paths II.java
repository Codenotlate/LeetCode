// Phase3 self

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        // special case: rememebr!
        if (obstacleGrid[0][0] == 1) {return 0;}
        
        
        // pay attention to the init of dp[0][j] and dp[i][0]
        int[] dp = new int[n];
        dp[0] = 1;
        for (int j = 1; j < n; j++) {
            if (obstacleGrid[0][j] != 1) {
                dp[j] = dp[j - 1];
            } else {
                dp[j] = 0;
            }
        }
        
        for (int i = 1; i < m; i++) {
            if (obstacleGrid[i][0] == 1) {
                dp[0] = 0;
            } 
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[j] += dp[j - 1];
                } else {
                    dp[j] = 0;
                }
                
            }
        }
        return dp[n - 1];
        
        
    }
}

// can optimize to a shorter version next time - no need to treat any case specially
// https://leetcode.com/problems/unique-paths-ii/discuss/23250/Short-JAVA-solution