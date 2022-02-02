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




// review self dp way
/* initial thought
at each day i, having 6 status based on two kinds of conditions: whether own stocks [0/1], and how may transactions did[0/1/2](if we cound sell as one transaction). And at each day there are two choices: buy or sell the stock.
dp[i][0][k] = max(dp[i-1][1][k-1] + price[i], dp[i-1][0][k])
dp[i][1][k] = max(dp[i-1][0][k] - price[i], dp[i-1][1][k])
init: dp[-1][1][k] = -inf, dp[-1][0][0] = 0, dp[-1][0][1/2] = -inf, dp[i][0/1][-1] = -inf
init: dp[0][1][k] = -inf, dp[0][0][0] = 0, dp[0][0][1/2] = -inf
*/

class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][2][3];
        for(int k = 0; k <=2; k++) {
            dp[0][1][k] = -prices[0];
            dp[0][0][k] = 0;
        }
        for (int i = 1; i < n; i++) {
            for (int k = 0; k <= 2; k++) {
                int dp_i1_1_k1 = k == 0? Integer.MIN_VALUE : dp[i-1][1][k-1];
                dp[i][0][k] = Math.max(dp_i1_1_k1 + prices[i], dp[i-1][0][k]);
                dp[i][1][k] = Math.max(dp[i-1][0][k] - prices[i], dp[i-1][1][k]);
            }
        }
        int maxProfit = 0;
        for (int k = 0; k<=2; k++) {
            maxProfit = Math.max(maxProfit, dp[n-1][0][k]);
        }
        return maxProfit;
        
    }
}


// This can be improved, if k represents the max# of transactions permitted that day. Then we only need to consider k=1 or k=2
// also if we update k when we buy stocks, it would be easier for us to optimize the dp space to O(1). With all these optimization, we will have above M1.
/*
dp[i][0][k] = max(dp[i-1][1][k-1] + price[i], dp[i-1][0][k])
dp[i][1][k] = max(dp[i-1][0][k] - price[i], dp[i-1][1][k])
change to
dp[i][0][k] = max(dp[i-1][1][k] + price[i], dp[i-1][0][k])
dp[i][1][k] = max(dp[i-1][0][k-1] - price[i], dp[i-1][1][k])
write k =1 and k=2 out
dp[i][0][1] = max(dp[i-1][1][1] + price[i], dp[i-1][0][1])
dp[i][0][2] = max(dp[i-1][1][2] + price[i], dp[i-1][0][2])
dp[i][1][1] = max(dp[i-1][0][0] - price[i], dp[i-1][1][1])
dp[i][1][2] = max(dp[i-1][0][1] - price[i], dp[i-1][1][2])
init: dp[i][0][0] = 0 dp[i-1][0][1] = -inf dp[-1][0][k] = 0 dp[-1][1][k] = -inf


*/





















