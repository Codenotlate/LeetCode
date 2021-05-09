class Solution {
	// M1: state machine
    public int maxProfit(int[] prices) {
        int dp_i20 = 0, dp_i10 = 0;
        int dp_i21 = Integer.MIN_VALUE, dp_i11 = Integer.MIN_VALUE;
        for (int p: prices) {
        	dp_i20 = Math.max(dp_i20, dp_i21 + p);
        	dp_i21 = Math.max(dp_i21, dp_i10 - p);
        	dp_i10 = Math.max(dp_i10, dp_i11 + p);
        	dp_i11 = Math.max(dp_i11, -p);
        }
        return dp_i20;
    }
}




class Solution {
	// M2: normal recursion way with memo
	// not the best way, time O(n^2), space O(n)
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {return 0;}
        int[][] memo = new int[prices.length][3];
        for (int[] m: memo) {
        	Arrays.fill(m, -1);
        }
        return dfs(prices, 0, 2, memo);
    }


    private int dfs(int[] prices, int curPos, int k, int[][] memo) {
    	if (k == 0 || curPos >= prices.length) {return 0;}
    	if (memo[curPos][k] != -1) {return memo[curPos][k];}
    	int res = 0;
    	int curMin = prices[curPos];
    	for (int i = curPos; i < prices.length; i++) {
    		curMin = Math.min(curMin, prices[i]);
    		res = Math.max(res, prices[i] - curMin + dfs(prices, i + 1, k - 1, memo));
    	}
    	memo[curPos][k] = res;
    	return res;
    }
}