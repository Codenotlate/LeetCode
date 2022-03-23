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



// Phase3 self
class Solution {
    // M1: Top-down: DFS + memo
    public int coinChange(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        return dfs(coins, amount, memo);
    }
    
    private int dfs(int[] coins, int amount, int[] memo) {
        if (amount < 0) {return -1;}
        if (amount == 0) {return 0;}
        if (memo[amount] != 0) {return memo[amount];}
        int min = amount + 1;
        for (int c: coins) {
            int res = dfs(coins, amount - c, memo);
            if (res >= 0 & res + 1 < min) {min = res + 1;}
        }
        memo[amount] = min == amount + 1 ? -1 : min;
        return memo[amount];
    }
}



class Solution {
    public int coinChange(int[] coins, int amount) {
        // M2: bottom-up
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int t =1; t <= amount; t++) {
            for (int c: coins) {
                if (t >= c && dp[t - c] != -1) {
                    dp[t] = Math.min(dp[t], dp[t - c] + 1);
                }
            }
        }
        
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}



// Review self : pay attention to the loop order of w and c and their direction
class Solution {
    // knapsack problem
    // dp[i][w] = min(dp[i-1][w], dp[i-1][w-c] + 1)
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        for (int w=0; w<=amount; w++) {
            for (int c:coins) {
                if (w < c) {continue;}
                dp[w] = Math.min(dp[w], dp[w-c] + 1);
            }
        }
        return dp[amount] > amount? -1 : dp[amount];
    }
}



// Review
/*Initial thought
M1 way: since requires fewest number of coins, we can view this as a n-nary tree and do BFS. time O(n^h) where n is coins.len, h is the fewest number to reach amount. h is at most amount. Thus time is possible as O(n^amount). space O(n^h) for the queue size.
M2 way: consider dp. for each position, we have two choices, choose or not choose. dp[i][w] = min(1+d[i][w-coins[i]], dp[i+1][w]). This is a knapsack problem. time O(n*amount) space O(amount) with optimization.
*/
// dp way
// actually given the amount range, we don't need to use integer.max below, set to amount+1 should be good enough
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = coins.length - 1; i >= 0; i--) {
            for (int w = coins[i]; w <= amount; w++) {
                int choose = dp[w-coins[i]] == Integer.MAX_VALUE ? Integer.MAX_VALUE : 1 + dp[w-coins[i]];
                dp[w] = Math.min(choose, dp[w]);
            }
        }
        return dp[amount] == Integer.MAX_VALUE? -1: dp[amount] ;
    }
}








