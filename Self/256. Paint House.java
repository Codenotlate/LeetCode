class Solution {
    // time O(n * 3 * 2)
    // space O( n * 3)
    public int minCost(int[][] costs) {
        // M1 DFS + Memo
        int n = costs.length;
        int[][] memo = new int[n][3];
        // here memo[i][exc] represents the minimum sum of house starting from i to the end and with "exc" color excluded for house i.
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i],3000);
        }
        
        int minres = 3000;
        for (int c = 0; c <= 2; c++) {
            minres = Math.min(minres, costs[0][c] + dp(1, c, memo, costs));
        }
        return minres;
    }
    
    
    
    private int dp(int i, int exc, int[][] memo, int[][] costs) {
        if (i == memo.length) {return 0;}
        if (memo[i][exc] != 3000) {return memo[i][exc];}
        int res = 3000;
        for (int c = 0; c <= 2; c++) {
            if ( c== exc) {continue;}
            res = Math.min(res, costs[i][c] + dp(i + 1, c, memo, costs));
        }
        memo[i][exc] = res;
        return res;
    }
}



class Solution {
    // M2: bottom-up dp
    // optimize space to  O(3) -> O(1)
    //here dp[i][col] means the min cost of house from 0 to i and choose color "col" on house i.
    public int minCost(int[][] costs) {
        int n = costs.length;
        int[] dp = new int[3];
        int[] temp = new int[3];
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(temp, 3000);
            for (int col = 0; col <=2; col++) {
                for (int c = 0; c <=2; c++) {
                    if (c == col) {continue;}
                    temp[col] = Math.min(temp[col], costs[i][col] + dp[c]);
                }
            }
            System.arraycopy(temp, 0, dp, 0, 3);
        }
        
        return Math.min(Math.min(dp[0], dp[1]), dp[2]);
    }
}

// another dp way: similar idea as M2: directly use 3 variables instead of loop
// https://leetcode.com/problems/paint-house/discuss/68211/Simple-java-DP-solution
class Solution {
    // M2 improve: bottom-up dp
    // optimize space to  O(3) -> O(1)
    //here dp[i][col] means the min cost of house from 0 to i and choose color "col" on house i.
    public int minCost(int[][] costs) {
        int n = costs.length;
        int[] dp = new int[3];
        int[] temp = new int[3];
        
        for (int i = 0; i < n; i++) {
            int col0, col1;
            col0 = Math.min(dp[1], dp[2]) + costs[i][0];
            col1 = Math.min(dp[0], dp[2]) + costs[i][1];
            dp[2] = Math.min(dp[0], dp[1]) + costs[i][2];
            dp[0] = col0;
            dp[1] = col1;
            
        }
        
        return Math.min(Math.min(dp[0], dp[1]), dp[2]);
    }
}


// Review self
class Solution {
    /* initial thought
    recursion + memo or DP
    recur(i,prevColor) = min(recur(i+1, c1)+ cost[i][c1], recur(i+1, c2) + cost[i][c2])
    memo[i][c] represents the min cost from house i to the end house, with c as the color at house i-1.
    time O(n * 3) space O(n*3)
    */
    public int minCost(int[][] costs) {
        int[][] memo = new int[costs.length][3];
        int cost0 = costHelper(costs, 1, 0, memo) + costs[0][0];
        int cost1 = costHelper(costs, 1, 1, memo) + costs[0][1];
        int cost2 = costHelper(costs, 1, 2, memo) + costs[0][2];
        
        return Math.min(cost0, Math.min(cost1, cost2));
    }
    
    private int costHelper(int[][] costs, int i, int prevColor, int[][] memo) {
        if (i == costs.length) {return 0;}
        if (memo[i][prevColor] != 0) {return memo[i][prevColor];}
        int minCost = Integer.MAX_VALUE;
        for (int c = 0; c <= 2; c++) {
            if (prevColor == c) {continue;}
            minCost = Math.min(minCost, costHelper(costs, i+1, c, memo) + costs[i][c]);
        }
        memo[i][prevColor] = minCost;
        return memo[i][prevColor];
    }
}


// slightly different from above DP, move backwards
class Solution {
    /* initial thought
    DP way: dp[i][c] means the minCost from house i to the end and at house i choose color c.
    dp[i][c] = min(dp[i+1][c1]+cost[i][c], dp[i+1][c2]+cost[i][c])
    dp[n-1][c_x] = costs[n-1][c_x];
    time O(n * 3) space O(n*3) optimized to O(3) = O(1)
    */
    public int minCost(int[][] costs) {
        int n = costs.length;
        int dp_c0 = costs[n-1][0];
        int dp_c1 = costs[n-1][1];
        int dp_c2 = costs[n-1][2];
        
        for (int i = n-2; i >= 0; i--) {
            int temp_c0 = dp_c0;
            dp_c0 = Math.min(dp_c1+costs[i][0], dp_c2+costs[i][0]);
            int temp_c1 = dp_c1;
            dp_c1 = Math.min(temp_c0+costs[i][1], dp_c2+costs[i][1]);
            dp_c2 = Math.min(temp_c0+costs[i][2], temp_c1+costs[i][2]);         
        }        
        return Math.min(dp_c0, Math.min(dp_c1, dp_c2));
    }
}