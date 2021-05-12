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