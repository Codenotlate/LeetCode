class Solution {
	// M1: state machine way
	// time: O(n) space O(1)
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        int dp_pre_0 = 0;
        for (int p : prices) {
        	int temp = dp_i_0;
        	dp_i_0 = Math.max(dp_i_0, dp_i_1 + p);
        	dp_i_1 = Math.max(dp_i_1, dp_pre_0 - p);
        	dp_pre_0 = temp;
        }
        return dp_i_0;
    }
}



class Solution {
	// normal recursion way with memo
	// not the best way, time O(n^2), space O(n)
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {return 0;}
        int[] memo = new int[prices.length];
        Arrays.fill(memo, -1);
        return dfs(prices, 0, memo);
    }


    private int dfs(int[] prices, int curPos, int[] memo) {
    	if (curPos >= prices.length) {return 0;}
    	if (memo[curPos] != -1) {return memo[curPos];}
    	int res = 0;
    	int curMin = prices[curPos];
    	for (int i = curPos; i < prices.length; i++) {
    		curMin = Math.min(curMin, prices[i]);
    		res = Math.max(res, prices[i] - curMin + dfs(prices, i + 2, memo));
    	}
    	memo[curPos] = res;
    	return res;
    }
}


// phase3 self

class Solution {
    public int maxProfit(int[] prices) {
        int dp_i0 = 0, dp_i1 = Integer.MIN_VALUE;
        int dp_prev_i0 = 0;
        for (int p: prices) {
            int temp = dp_i0; // store the value that will become the dp[i - 2][0] when go to the i+1 round
            dp_i0 = Math.max(dp_i0, dp_i1 + p);
            dp_i1 = Math.max(dp_i1, dp_prev_i0 - p);
            dp_prev_i0 = temp;
        }
        return dp_i0;   
    }
} 




// Review self: step by step
// M1: naive dp way with exact formula implemented. without space optimization. space O(n)  time O(n)
class Solution {
    // diff status: at ith day, can choose to buy/sell/do nothing, which is dependent on last day stock owning condition(own or not own), and will impact the next day
    // dp[i][j] represents the profit on day i with stock owning status j
    // dp[i][0] = max(dp[i-1][1] + price[i], dp[i-1][0])
    // Originally, dp[i][1] = max(dp[i-1][0] - price[i], dp[i-1][1]) since cooldown onew day after sell, thus dp[i-1][0] in the above second formula meanings we want to buy at day i, meaning we can't sell at day i-1. Thus that dp[i-1][0] = dp[i-2][0]. Thus: 
    // dp[i][1] = max(dp[i-2][0]-price[i], dp[i-1][1]).
    // init: dp[0][0] = 0; dp[0][1] = -inf
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i-1][1] + prices[i], dp[i-1][0]);
            if (i == 1) {dp[i][1] = Math.max(-prices[i], dp[i-1][1]);}
            else {dp[i][1] = Math.max(dp[i-2][0] - prices[i], dp[i-1][1]);}
        }
        return dp[prices.length-1][0];
    }
}


// M2: same idea as M1, but optimize with O(1) space.
class Solution {
    public int maxProfit(int[] prices) {
        int dp_previ_0 = 0; // dp[-2][0]
        int dp_i_0 = 0; //dp[-1][0]
        int dp_i_1 = Integer.MIN_VALUE; // dp[-1][1]
        // loop now starts at i = 0
        for (int p: prices) {
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_1 + p, dp_i_0);
            dp_i_1 = Math.max(dp_previ_0 - p, dp_i_1);
            dp_previ_0 = temp;
        }
        return dp_i_0;
    }
}



// Review
/*Initial thought
DP problem. At each day, we have two status, with a stock or without, and we have three actions buy or sell or do nothing. Use dp[i][1] to represent the profit at ith day with stock, and dp[i][0] without stock.
dp[i][0] = Math.max(dp[i-1][1] + p[i], dp[i-1][0])
dp[i][1] = Math.max(dp[i-1][0] - p[i], dp[i-1][1]) since we have 1 day cooldown, dp[i-1][0] = dp[i-2][0] => 
dp[i][0] = Math.max(dp[i-1][1] + p[i], dp[i-1][0])
dp[i][1] = Math.max(dp[i-2][0] - p[i], dp[i-1][1])
traditional way time O(n), space O(n)
optimized space to O(1)
*/
class Solution {
    public int maxProfit(int[] prices) {
        int dp_10 = 0;
        int dp_11 = Integer.MIN_VALUE;
        int dp_20 = 0;
        for (int p : prices) {
            int temp = dp_10;
            dp_10 = Math.max(dp_11 + p, dp_10);
            dp_11 = Math.max(dp_20 - p, dp_11);
            dp_20 = temp;
        }
        return Math.max(dp_11, dp_10);
    }
}