class Solution {
	// dp: f(i) represents the max amount we can get from robbing nums[i:]
	// moving backwards: f(i) = max (f(i+1), nums[i] + f(i+2))
	// time O(n), space O(1)
    public int rob(int[] nums) {
        int n = nums.length;
        int next1 = 0;
        int next2 = 0;
        for (int i = n - 1; i >= 0; i--) {
        	int cur = Math.max(next1, next2 + nums[i]);
        	next2 = next1;
        	next1 = cur;
        }
        return next1;
    }
}



// phase3 self
// different from above method. Above method
class Solution {
    public int rob(int[] nums) {
        //M1: basic dp way, time O(n), space O(n)
        //need 2 results from i - 1 to represent two cases: i-1 included or i-1 not included
        // dp[i][0] represents max result from nums[0] to nums[i] and i not chosen
        // dp[i][1] represents max result from nums[0] to nums[i] and i is chosen
        int n = nums.length;
        int[][] dp = new int[n][2];
        // init
        dp[0][0] = 0;
        dp[0][1] = nums[0];
        
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][1], dp[i-1][0]);
            dp[i][1] = dp[i-1][0] + nums[i];
        }
        return Math.max(dp[n-1][0], dp[n-1][1]);
    }
}


class Solution {
    public int rob(int[] nums) {
        //M1.1: optimized dp way, time O(n), space O(1)
        //need 2 results from i - 1 to represent two cases: i-1 included or i-1 not included
        // dp[i][0](dp_i0) represents max result from nums[0] to nums[i] and i not chosen
        // dp[i][1](dp_i1) represents max result from nums[0] to nums[i] and i is chosen
        int n = nums.length;
        // init
        int dp_i0 = 0;
        int dp_i1 = nums[0];
        
        for (int i = 1; i < n; i++) {
            int temp = dp_i0;
            dp_i0 = Math.max(dp_i0, dp_i1);
            dp_i1 = temp + nums[i];
        }
        return Math.max(dp_i0, dp_i1);
    }
}



// Review self
class Solution {
    /* initial thought
    At each position, we have two choices, rob or not rob
    dp[i][0] = max(dp[i-1][1], dp[i-1][0]);
    dp[i][1] = dp[i-1][0] + nums[i]
    return max(dp[n-1][0], dp[n-1][1])
    time O(n) space O(n) and can be optmized to O(1)
    */
    public int rob(int[] nums) {
        int dp_i0 = 0;
        int dp_i1 = -Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int prev_i0 = dp_i0;
            dp_i0 = Math.max(dp_i0, dp_i1);
            dp_i1 = prev_i0 + nums[i];
        }
        return Math.max(dp_i0, dp_i1);
    }
}