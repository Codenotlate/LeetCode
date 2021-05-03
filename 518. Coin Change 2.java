class Solution {
	// M1: dfs with memoization
	// time O(len * amount) space O(len * amount)
	// somehow very slow
    public int change(int amount, int[] coins) {
        if (coins == null || coins.length == 0) {
            return amount == 0 ? 1 : 0;
        }
        int[][] memo = new int[coins.length][amount + 1];
        for (int[] m: memo) {
        	Arrays.fill(m, -1);
        }
        return dfs(coins, amount, memo, 0);
    }

    private int dfs(int[] coins, int amount, int[][] memo, int curPos) {
    	if (amount == 0) {return 1;}
    	if (memo[curPos][amount] != -1) {return memo[curPos][amount];}
    	memo[curPos][amount] = 0;
    	for (int i = curPos; i < coins.length; i++) {
    		if (amount >= coins[i]) {
    			memo[curPos][amount] += dfs(coins, amount - coins[i], memo, i);
    		}
    	}
    	return memo[curPos][amount];
    }
}



class Solution {
	// M2: dp; complete knapsack type problem
	// time O(len * amount) space O(len * amount)
    public int change(int amount, int[] coins) {
        if (coins == null || coins.length == 0) {
        	return amount == 0 ? 1 : 0;
        }
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int c: coins) {
        	for (int w = c; w <= amount; w++) {
        		dp[w] = dp[w - c] + dp[w];
        	}
        }
        return dp[amount];
    }
}

/*
Can anyone tell me why dp is "of course" better? Time complexity is the same, no?

@8939123 i believe it has to do with memory. bottom-up is usually considered better because of call stack space overhead. in this case, we'd have to account space for the map and space for the call stack whereas bottom-up is only for whatever the memoize object may be.

In addition to what @danielvd said, from a memory access point of view, it is faster to access elements of an in a sequential manner because the probability that contiguous array elements are fall within same virtual memory block is higher. DFS access the array very arbitrarliy.
It is also possible, in some occasions to reduce space from o(n^2) to o(n).

*/

// summary of multiple methods
// https://leetcode.com/problems/coin-change-2/discuss/623953/Evolve-from-brute-force-to-dp






