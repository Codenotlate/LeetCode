public class Solution {
	// M1: dfs with memoization
	// time O(len(coins) * amount) space O(amount)
	public int coinChange(int[] coins, int amount) {
		if (amount < 1) return 0;
		// amount = 1 to amount, index for count from 0 to amount - 1
		return coinChange(coins, amount, new int[amount]);
	}

	private int coinChange(int[] coins, int rem, int[] count) {
		if (rem < 0) return -1;
		if (rem == 0) return 0;
		if (count[rem - 1] != 0) return count[rem - 1];
		int min = Integer.MAX_VALUE;
		for (int coin : coins) {
			int res = coinChange(coins, rem - coin, count);
			if (res >= 0 && res < min) {
				min = 1 + res;
			}		
		}
		count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
		return count[rem - 1];
	}
}





class Solution {
	// m2: dp, knapsack problem, each item can be used multiple times
	// time O(len(coins) * amount) space O(amount)
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 0; i < coins.length; i++) {
        	for (int w = coins[i]; w <= amount; w++) {
        		// it's ok even if dp[w-coins[i]] can't find
        		// then it will remain amount + 1, and with min, thr max value of dp[w] is still amount + 1
        		dp[w] = Math.min(dp[w], 1 + dp[w - coins[i]]);
        	}
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}

















