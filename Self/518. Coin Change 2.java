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


// Phase3 self
// pretty slow: time O(n * l) space O(n * l)
class Solution {
    // dfs + memo
    public int change(int amount, int[] coins) {
        int[][] memo = new int[amount + 1][coins.length];
        for (int[] m: memo) {
            Arrays.fill(m, -1);
        }
        Arrays.fill(memo[0],1);
        calWay(amount, coins, 0, memo);
        return memo[amount][0];
    }
    
    
    private int calWay(int amount, int[] coins, int idx, int[][] memo) {
        if(memo[amount][idx] != -1) {return memo[amount][idx];}
        int res = 0;
        for (int i = idx; i < coins.length; i++) {
            if(coins[i] <= amount) {
                res += calWay(amount - coins[i], coins, i, memo);
            }
        }
        memo[amount][idx] = res;
        return res;   
        
    }
}

// knapsack way
class Solution {
    // Knapsack problem
    // items in coins and the max is amount
    // dp[i][w] = dp[i-1][w-coins[i]] + dp[i-1][w]; optimize to 1d by omiting i
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin: coins) {
            for (int w = 0; w <= amount; w++) {
                if (w >= coin) {dp[w] += dp[w - coin];}            
            }
        }
        return dp[amount];
    }
}
// summary for knapsack problems
//https://leetcode.com/discuss/study-guide/1200320/Thief-with-a-knapsack-a-series-of-crimes.


// Review
/* Initial thought
similar to knapsack problem. dp(0,5) = dp(1,5) + dp(0,4)
dp[i][w] represensts the result starting at coins[i: end] to sum to amount w. 
dp[i][w] = dp[i+1][w] + dp[i][w-coins[i]];
Thus i from large to small,
*/
class Solution {
    public int change(int amount, int[] coins) {
        int[] dp =new int[amount + 1];
        dp[0] = 1;
        for(int i = coins.length - 1; i >= 0; i--) {
            for (int w = coins[i]; w <= amount; w++) {
                dp[w] += dp[w - coins[i]];
            }
        }
        return dp[amount];
    }
}
// note if we use 2d array, doesn't matter who's the outer loop. But this way is not able to transform to 1d space version.(draw the matrix and check the loop order will see)
// https://leetcode.com/problems/coin-change-2/discuss/1135391/Java-clean-2D-1D-Dynamic-Programming-Solution-oror-with-detailed-comments
class Solution {
    public int change(int amount, int[] coins) {
        int[][] dp =new int[coins.length + 1][amount + 1];
        for(int[] row: dp) {row[0] = 1;}

        for(int w = 0; w <= amount; w++) {
            for (int i = coins.length - 1; i >= 0; i--) {
                dp[i][w] = dp[i+1][w] + (w >= coins[i] ? dp[i][w - coins[i]]:0);
            }
        }
        return dp[0][amount];
    }
}





// Review 23/1/6
/* Thoughts
M1: recursive + memo. time & space O(amount * coins.len)
One note: to avoid dup combinations, we need to know the previous coin position used, and start from that coin position as the smallest.
Also note the special case for amount == 0.
5
5 f(0,5);  2 f(3,2);   1 f(4,1)
        |  2 f(1,2);  1 f(2,1)  | 1 f(3,1)
        |  1 f(0,1); | 1 f(1,1) | 1 f(2,1)
                        ...


M2: dp way.
Above M1 can be converted to a dp way of thinking. Have dp[amount][i] represents #of ways with amount and coins[i:]. Then dp[amount][i] = dp[amount - coins[i]][i] + dp[amount][i+1].
amount -  up to down
i - right to left
To optimize the space to O(amount) and store it as a row(in above setting, can store as a column, but more familiar with the row way), can adjust the order: dp[i][amount] = dp[i][amount - coins[i]] + dp[i+1][amount]
i - bottom to up
amount - left to right
 */
// M1
class Solution {
    public int change(int amount, int[] coins) {
        if (amount == 0) {return 1;}
        int[][] memo = new int[amount+1][coins.length];
        for (int[] row: memo) {Arrays.fill(row, -1);}
        int res = 0;
        for (int i = 0; i < coins.length; i++) {
            if (amount < coins[i]) {continue;}
            res += count(amount-coins[i], i, coins, memo);
        }
        return res;
    }

    private int count(int amount, int cur, int[] coins, int[][] memo) {
        if (amount == 0) {return 1;}
        if (memo[amount][cur] != -1) {return memo[amount][cur];}
        int res = 0;
        for (int i = cur; i < coins.length; i++) {
            int c = coins[i];
            if ( c > amount) {continue;}
            res += count(amount - c, i, coins, memo);
        }
        memo[amount][cur] = res;
        return res;

    }
}


// M2 - can be more precise as above; can also have the dp rule as dp[i] based on dp[i-1]. Like above dp ways (top down).
class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = coins.length - 1; i >= 0; i--) {
            int c = coins[i];
            for (int w = 1; w <= amount; w++) {
                if (w >= c) {
                    dp[w] += dp[w-c];
                }
            }           
        }
        return dp[amount];
    }   
}
