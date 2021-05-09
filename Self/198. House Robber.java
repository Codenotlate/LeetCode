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