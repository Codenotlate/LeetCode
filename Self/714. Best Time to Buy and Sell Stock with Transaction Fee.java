class Solution {
	// similar as 309
	// M1: state machine way: time O(n), space O(1)
    public int maxProfit(int[] prices, int fee) {
        int dp_i_0 = 0;
        int dp_i_1 = Integer.MIN_VALUE;
        for (int p: prices) {
        	int temp = dp_i_0;
        	dp_i_0 = Math.max(dp_i_0, dp_i_1 + p);
        	dp_i_1 = Math.max(dp_i_1, temp - p - fee);
        }
        return dp_i_0;
    }
}


class Solution {
	// normal recursion way with memo
	// not the best way, time O(n^2), space O(n)
	// would have TLE for this problem's testcase
    public int maxProfit(int[] prices, int fee) {
        if (prices.length == 0) {return 0;}
        int[] memo = new int[prices.length];
        Arrays.fill(memo, -1);
        return dfs(prices, fee, 0, memo);
    }


    private int dfs(int[] prices, int fee, int curPos, int[] memo) {
    	if (curPos >= prices.length) {return 0;}
    	if (memo[curPos] != -1) {return memo[curPos];}
    	int res = 0;
    	int curMin = prices[curPos];
    	for (int i = curPos; i < prices.length; i++) {
    		curMin = Math.min(curMin, prices[i]);
    		res = Math.max(res, prices[i] - curMin - fee + dfs(prices, fee, i + 1, memo));
    	}
    	memo[curPos] = res;
    	return res;
    }
}