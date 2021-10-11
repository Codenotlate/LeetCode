class Solution {
	// dp same as 198, max of 2 separate cases
	// time O(n), space O(1)
    public int rob(int[] nums) {
        if (nums.length == 0) {return 0;}
        if (nums.length == 1) {return nums[0];}
        int start = 0;
        int end = nums.length - 1;
        return Math.max(robRange(nums, start, end - 1), robRange(nums, start + 1, end));
    }

    private int robRange(int[] nums, int start, int end) {
    	int next1 = 0;
    	int next2 = 0;
    	for (int i = end; i >= start; i--) {
    		int cur = Math.max(next2 + nums[i], next1);
    		next2 = next1;
    		next1 = cur;
    	}
    	return next1;
    }
}




// thinking process
// https://leetcode.com/problems/house-robber-ii/discuss/227366/Thinking-process-from-easy-question-to-harder-question-within-the-same-question-set



// Phase3 self
// similar as 198 phase3, move forward, above method is moving backwards
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        // note pay attention to n=1 special case
        if (n == 1) {return nums[0];}
        int res1 = getRes(nums, 0, n - 2);
        int res2 = getRes(nums, 1, n - 1);
        return Math.max(res1, res2);
    }
    
    private int getRes(int[] nums, int start, int end) {
        int dp_i0 = 0;
        int dp_i1 = nums[start];
        for (int i = start + 1; i <= end; i++) {
            int temp = dp_i0;
            dp_i0 = Math.max(dp_i0, dp_i1);
            dp_i1 = temp + nums[i];
        }
        return Math.max(dp_i1, dp_i0);
    }
}