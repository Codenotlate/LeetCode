class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxLen = 0;
        int curLen = 0;
        for (int i = 0; i < nums.length; i++) {
        	if(nums[i] == 1) {
        		curLen++;
        	} else {
        		maxLen = Math.max(maxLen, curLen);
        		curLen = 0;
        	}
        }
        return Math.max(maxLen, curLen);
    }
}