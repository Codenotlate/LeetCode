// Phase3 self
// M1: recursion + memo
// time O(mn) space O(mn)
// here memo[r][c] represents the result we want [Max of {harm of each path (negative sum result)}] from cell [r, c] to cell [m-1, n-1].

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] memo = new int[m][n];
        for (int[] r: memo) {
            Arrays.fill(r, Integer.MAX_VALUE);
        }
        int res = dfs(0, 0, memo, dungeon);
        // if the harm is positive meaning we only need 1 to start, and the rest cell will have the sufficient health by themselves
        return res > 0 ? 1 : -res + 1;
    }
    
    
    private int dfs(int r, int c, int[][] memo, int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        if (r == m - 1 && c == n - 1) {
            return dungeon[r][c];
        }
        if (memo[r][c] != Integer.MAX_VALUE) {return memo[r][c];}
        int downRes = r == m - 1 ? Integer.MIN_VALUE : dfs(r + 1, c, memo, dungeon);
        int rightRes = c == n - 1 ? Integer.MIN_VALUE : dfs(r, c + 1, memo, dungeon);
        downRes = Math.min(0, downRes);
        rightRes = Math.min(0, rightRes);
        memo[r][c] = Math.max(downRes, rightRes) + dungeon[r][c];
        return memo[r][c];
        
    }
}


// can also go with the solution in this post: directly use negative along the way.  ALso the discussion about why backwards instead of forwards from [0,0] to [r, c] That's because the result will change once we add one more row or column if go forwards, the result is not known for sure and subject to change in that case.
// https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start



// can use bottom up dp and space optimization
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        // M2: bottom-up dp way
        // dp[r][c] represents the result from cell (r, c) to (m-1, n-1);
        // time O(m * n) space O(m *n)
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];
        // init with -inf
        for(int[] row: dp) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        for (int r = m-1; r >= 0; r--) {
            for (int c = n-1; c >= 0; c--) {
                if (r == m - 1 && c == n - 1) {dp[r][c] = dungeon[r][c]; continue;}
                dp[r][c] = dungeon[r][c] + Math.max(Math.min(0, dp[r + 1][c]), Math.min(0, dp[r][c + 1]));
            }
        }
        
        return dp[0][0] >= 0 ? 1 : -dp[0][0] + 1;
        
    }
}


class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        // M2.1: bottom-up dp way
        // dp[r][c] represents the result from cell (r, c) to (m-1, n-1);
        // time O(m * n); optimize space from O(m *n) to O(n)
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n + 1];
        // init with -inf
        Arrays.fill(dp, Integer.MIN_VALUE);

        for (int r = m-1; r >= 0; r--) {
            for (int c = n-1; c >= 0; c--) {
                if (r == m - 1 && c == n - 1) {dp[c] = dungeon[r][c]; continue;}
                dp[c] = dungeon[r][c] + Math.max(Math.min(0, dp[c]), Math.min(0, dp[c + 1]));
            }
        }
        
        return dp[0] >= 0 ? 1 : -dp[0] + 1;
        
    }
}


// Review self
class Solution {
    /* initial thought
    we have multiple paths to reach the bottomright corner and along the way the min accum sum is the health we needed for that path. We want the max{min accumsum of all paths}
    At position (i,j) we want to know info: max min_accumsum of (i, j+1) to the end and max min_accumsum of (i+1, j) to the end.
    one thing to note is that if dp[i][j+1] or dp[i+1][j] >0, then it should be count as 0, since we are not able to use the health from backwords cells.
    dp[i][j] = max(min(0,dp[i][j+1]), min(0,dp[i+1][j])) + m[i][j]
    time O(mn) space optimize to O(n)
    */
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n+1];
        Arrays.fill(dp,Integer.MIN_VALUE);
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (i == m-1 && j == n-1) {dp[j] = dungeon[i][j]; continue;}
                dp[j] = Math.max(Math.min(0,dp[j+1]), Math.min(0,dp[j])) + dungeon[i][j];
            }
        }
        return dp[0] <= 0 ? -dp[0] + 1 :  1;
    }
}







// Review
/*Thought
A DP problem. want to find the max negative accumSum from start to end for all paths. Not a very clear way from topleft to bottomright. So we check backwards. Have dp[i][j] represents the max pathSum from cell(i,j) to end position(bottom right). 
Then dp[i][j] = max(min(dp[i-1][j],0), min(0,dp[i][j-1])) + dungeon[i][j]. Given it only moves rightward and downward. And final result would be max(0,-dp[0][0]) + 1.
space can be optimized to O(n)
time O(mn)
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        for (int i = m-1; i >= 0; i--) {
            for(int j = n-1; j >= 0; j--) {
                if(i == m-1 && j == n-1) {dp[j] = dungeon[i][j]; continue;}
                dp[j] = Math.max(Math.min(dp[j],0), Math.min(dp[j+1],0)) + dungeon[i][j];
            }
        }
        
        return Math.max(0, -dp[0]) + 1;
    }
}



// Review - 23/1/31 (self done)
/* Thoughts
want the max of the negative numbers
dp[i][j] = mat[i][j] + max(min(0, dp[i+1][j]), min(0, dp[i][j+1])) for out of boundary ones, dp = -inf
in the end, return max(0,-dp[0][0]) + 1.
time O(mn) space O(min(m,n))
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n];
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (i == m-1 && j == n-1) {dp[j] = dungeon[i][j]; continue;}
                int right = j+1 < n? Math.min(0, dp[j+1]) : Integer.MIN_VALUE;
                int bottom = i + 1 < m? Math.min(0, dp[j]) : Integer.MIN_VALUE;
                dp[j] = dungeon[i][j] + Math.max(right, bottom);
            }
        }

        return Math.max(-dp[0], 0) + 1;
    }
}