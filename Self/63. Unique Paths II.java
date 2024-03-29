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


// phase3 self
class Solution {
    // still start from end position, if end position itself has an obstacle, return 0 directly. Otherwise, basic rule same as 62: res[i][j] = res[i+1][j] + res[i][j+1].
    // in the question, special case is when grid[i][j] = 1, then res[i][j] = 0.
    // naive dp with space O(mn), or optimized dp with space O(n)
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[m-1][n-1] == 1) {return 0;}
        int[] res = new int[n];
        res[n-1] = 1; // remeber to put this line
        for (int i = m - 1; i >= 0; i--) {
            // remember to adjust res[n-1] based on the row below
            if (obstacleGrid[i][n-1] == 1) {res[n-1]= 0;}
            for (int j = n - 2; j >= 0; j--) {
                if (obstacleGrid[i][j] == 0) {
                    res[j] += res[j+1];
                } else {
                    res[j] = 0;
                }
            }
        }
        return res[0];
    }
}

// interviewer may ask alternative approaches and the comparison between them. Another way could be dfs + memo


// Reveiw
/* Initial thought
DP way. moving backwards from the bottomright pos. If encounter 1, then num = 0 for that cell. dp(i,j) = dp(i+1, j) + dp(i, j+1).
special case: m,n=1 or 1 in grid[m-1][n-1]
the space may be further optimized.
*/
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m+1][n+1];
        if (obstacleGrid[m-1][n-1] == 1 || obstacleGrid[0][0] == 1) {return 0;}
        dp[m-1][n-1] = 1;
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if(i == m-1 && j == n-1) {continue;}
                dp[i][j] = obstacleGrid[i][j] == 1? 0: (dp[i+1][j] + dp[i][j+1]);
            }
        }
        return dp[0][0];
    }
}
// optimize space
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[] dp = new int[n+1];
        if (obstacleGrid[m-1][n-1] == 1 || obstacleGrid[0][0] == 1) {return 0;}
        dp[n-1] = 1;
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if(i == m-1 && j == n-1) {continue;}
                dp[j] = obstacleGrid[i][j] == 1? 0: (dp[j] + dp[j+1]);
            }
        }
        return dp[0];
    }
}


// Review 23/1/3
// unlike above, this is moving forward way and no special treatment for edge cases.
// time O(mn) space O(min(m,n))
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int left = 0;
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) {
            left = 0;
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[j] = obstacleGrid[0][0] == 1 ? 0 : 1;
                    left = dp[j];
                    continue;
                }
                dp[j] = obstacleGrid[i][j] == 1 ? 0 : left + dp[j];
                left = dp[j];
            }
        }
        return dp[n-1];
    }
}



// (copy from above) interviewer may ask alternative approaches and the comparison between them. Another way could be dfs + memo

