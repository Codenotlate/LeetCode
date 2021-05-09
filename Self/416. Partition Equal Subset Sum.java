// The key of this problem is to convert to find a subset with sum = sum(nums) / 2;
// and if sum(nums) % 2 != 0, then directly return false

class Solution {
	// m1: dfs + backtracking 
	// time O(2^n) space O(n) -- TLE
    public boolean canPartition(int[] nums) {
        // get sum of nums
        int sum = 0;
        for (int n: nums) {
        	sum += n;
        }
        if (sum % 2 != 0) {return false;}

        return dfs(nums, 0, sum / 2, 0);
    }

    private boolean dfs(int[] nums, int pos, int sum, int curSum) {
    	if (pos == nums.length || sum == curSum) {
    		return curSum == sum;
    	}
    	if (dfs(nums, pos + 1, sum, curSum + nums[pos])) {return true;}
    	if (dfs(nums, pos + 1, sum, curSum)) {return true;}
    	return false;

    }
}



class Solution {
	// m2: convert to knapsack problem
	// time O(n^2) space O(n)
    public boolean canPartition(int[] nums) {
        // get sum of nums
        int sum = 0;
        for (int n: nums) {
        	sum += n;
        }
        if (sum % 2 != 0) {return false;}

        sum /= 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++) {
        	for (int w = sum; w >= nums[i]; w--) {
        		dp[w] = dp[w - nums[i]] || dp[w];
        	}
        }
        return dp[sum];
    }
}





// multiple ways explained in whiltboard way
// https://leetcode.com/problems/partition-equal-subset-sum/discuss/462699/Whiteboard-Editorial.-All-Approaches-explained.
















