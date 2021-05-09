class Solution {
	// M1: dfs + backtracking with memo
	// time O(n * target) n = nums.length, target = (S + sum(nums)) / 2
	// space O(n * target)
	// memory Limit exceeded
    public int findTargetSumWays(int[] nums, int S) {
        int target = 0;
        for (int n: nums) {
        	target += n;
        }
        // this step is crucial, avoid unnecessary space and runtime cost
        if ((target + S) % 2 != 0 || S > target) {return 0;}
        target = (target + S) / 2;
        int[][] memo = new int[nums.length][target + 1];
        for (int[] m: memo) {
        	Arrays.fill(m, -1);
        }

        return dfs(nums, target, 0, memo);
    }

    private int dfs(int[] nums, int target, int curPos, int[][] memo) {
    	if (curPos == nums.length || target < 0) {
    		return target == 0 ? 1 : 0;
    	}
    	if (memo[curPos][target] != -1) {return memo[curPos][target];}
    	int res = dfs(nums, target - nums[curPos], curPos + 1, memo) + dfs(nums, target, curPos + 1, memo);
    	memo[curPos][target] = res;
    	return res;
    }
}


// can also do direct dfs, without converting to target



class Solution {
	// M2: use knapsack pattern with optimized space 1d dp
	// dp[i][w] represents # of subset with sum = target using nums[0:i]
	// time O(n * target) space O(target)
    public int findTargetSumWays(int[] nums, int S) {
        int target = 0;
        for (int n: nums) {
        	target += n;
        }
        // this step is crucial, avoid unnecessary space and runtime cost
        if ((target + S) % 2 != 0 || target < S) {return 0;}
        target = (target + S) / 2;


        int[] dp = new int[target + 1];
        dp[0] = 1; 
        for (int i = 0; i < nums.length; i++) {
        	for (int w = target; w >= nums[i]; w--) {
        		dp[w] = dp[w - nums[i]] + dp[w];
        	}
        }
        return dp[target];
    }
}

























