class Solution {
	// M1: dfs + backtracking with memo
	// time O(n * target) n = nums.length, target = (S + sum(nums)) / 2
	// space O(n * target)
	// memory Limit exceeded
    // this solution is no longer valid, cause now S could be negative in the problem. 
    public int findTargetSumWays(int[] nums, int S) {
        int target = 0;
        for (int n: nums) {
        	target += n;
        }
        // this step is crucial, avoid unnecessary space and runtime cost
        // since now S can be negative, we should have abs(S) > target
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

//https://leetcode.com/problems/target-sum/discuss/97334/Java-(15-ms)-C%2B%2B-(3-ms)-O(ns)-iterative-DP-solution-using-subset-sum-with-explanation
// why converting the target


// Phase3 (diff from above methods, problem setting changes, now the target can be a negative number)
// the key step is to convert it to subset sum problem
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int n: nums) {
            sum += n;
        }
        if ((target + sum) % 2 != 0 || Math.abs(target) > sum) {
            return 0;
        }
        int convertT = (target + sum) / 2;
        int[][] memo = new int[nums.length][convertT + 1];
        for (int[] m: memo) {Arrays.fill(m, -1);}
        return dfs(nums, convertT, 0, memo);
    }
    
    
    private int dfs(int[] nums, int t, int i, int[][] memo) {
        if (i >= nums.length) {return t == 0 ? 1 : 0;}
        if (memo[i][t] != -1) {return memo[i][t];}
        int notchoose = dfs(nums, t, i+1, memo);
        int choose = t-nums[i] >= 0? dfs(nums, t - nums[i], i+1, memo) : 0;
        memo[i][t] = choose + notchoose;
        return choose + notchoose;
    }
}

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int n: nums) {
            sum += n;
        }
        if ((target + sum) % 2 != 0 || Math.abs(target) > sum) {
            return 0;
        }
        int convertT = (target + sum) / 2;
        // dp[i][w] = dp[i-1][w] + dp[i-1][w-nums[i]], then convert to 1d
        int[] dp = new int[convertT + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int w = convertT; w >= 0; w--) {
                if (w >= nums[i]) {
                    dp[w] += dp[w - nums[i]];
                }       
            }
        }
        return dp[convertT];
    }
    
    
   
}

























