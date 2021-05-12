class Solution {
	// M1: state machine way: time O(n * maxk) space O(n * maxk * 2)
	// K: represent the largest number of trades that day can have (i.e. k times left until the end date)
    // to avoid a very large k costing too much space, we decide different approach based on whether k > n / 2
    // if k > n/2, then it's same as k as +inf, can use a dp array without k-dimension.
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (k == 0 || n == 0) {return 0;}
        if (k > n / 2) {
        	return maxProfitInfK(prices);
        } else {
        	return maxProfitK(k, prices);
        }
    }

    // return the max return when you can trade infinite times
    private int maxProfitInfK(int[] prices) {
    	int dp_i_0 = 0;
    	int dp_i_1 = Integer.MIN_VALUE;
    	for (int p: prices) {
    		dp_i_0 = Math.max(dp_i_0, dp_i_1 + p);
    		dp_i_1 = Math.max(dp_i_1, dp_i_0 - p);
    	}
    	return dp_i_0;
    }

    // return the max return when you can trade at most k times
    private int maxProfitK(int k, int[] prices) {
    	// dp_k_0[k1] represents dp[i][k1][0]
    	int[] dp_k_0 = new int[k + 1]; // already initialized with 0
    	int[] dp_k_1 = new int[k + 1];
    	// initialize dp_k_1 to -inf
    	Arrays.fill(dp_k_1, Integer.MIN_VALUE);
    	for(int p: prices) {
    		for(int j = k; j >= 1; j--) {
    			dp_k_0[j] = Math.max(dp_k_0[j], dp_k_1[j] + p);
    			dp_k_1[j] = Math.max(dp_k_1[j], dp_k_0[j - 1] - p);
    		}
    	}
    	return dp_k_0[k];
    }
}







class Solution {
	// M2: normal recursion way with memo
	// not the best way, time O(maxk * n^2), space O(n * maxk)
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) {return 0;}
        int[][] memo = new int[prices.length][k + 1];
        for (int[] m: memo) {
        	Arrays.fill(m, -1);
        }
        return dfs(prices, 0, k, memo);
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



// phase3 self
// Note we still need to deal with k > n/2 case, to optimize to O(n) as M1 above
class Solution {
    public int maxProfit(int k, int[] prices) {
        int[] k0 = new int[k + 1];
        int[] k1 = new int[k + 1];
        Arrays.fill(k1, Integer.MIN_VALUE);
        
        for (int p : prices) {
            for (int j = k; j >= 1; j--) {
                k0[j] = Math.max(k0[j], k1[j] + p);
                k1[j] = Math.max(k1[j], k0[j - 1] - p);
            }
        }
        return k0[k];
    }
}











