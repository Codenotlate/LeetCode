class Solution {
	// M1: dfs + memoization
	// time O(target * len(nums)) space O(target)
	// this is a permutation problem, so no need to track curPos in nums
    public int combinationSum4(int[] nums, int target) {
        int[] memo = new int[target + 1];
        Arrays.fill(memo, -1);
        memo[0] = 1;

        return dfs(nums, target, memo);
    }

    private int dfs(int[] nums, int target, int[] memo) {
    	// base case (can include target == 0 part)
    	if (memo[target] != -1) {return memo[target];}
    	// dfs part
    	memo[target] = 0; // !!! don't forget this line!!!!
    	for (int n: nums) {
    		if (target >= n) {
    			memo[target] += dfs(nums, target - n, memo);
    		}
    	}
    	return memo[target];
    }
}




class Solution {
	// M2:dp way: complete knapsack problem with order!
	// loop knapsack before items
	// if we loop items before bag, then it returns # of combination not permutation.
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int w = 1; w <= target; w++) {
        	for (int n: nums) {
        		if (w >= n) {
        			dp[w] = dp[w] + dp[w - n];
        		}       		
        	}
        }
        return dp[target];
    }
}

// comments about loop order
// https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation

/*
follow-up: if there's negative value, then we need to constrain the maxLen of the combination or 
the number of times each item can be used. Otherwise, we go into an infinite loop, as long as we -a * x times + b * y times = 0
https://leetcode.com/problems/combination-sum-iv/discuss/85038/JAVA%3A-follow-up-using-recursion-and-memorization.

*/
















