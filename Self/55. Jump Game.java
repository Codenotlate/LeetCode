// Phase3 self: using sliding window
// time O(N) space O(1)

class Solution {
    public boolean canJump(int[] nums) {
        int end = 0;
        for (int i = 0; i < nums.length; i++) {
        	if (i > end) {return false;}
        	end = Math.max(end, i + nums[i]);
        	if (end >= nums.length - 1) {return true;} //this line can be skipped
        }
        return true; 
    }
}

// https://leetcode.com/problems/jump-game/discuss/20900/Simplest-O(N)-solution-with-constant-space
// can also move backwards, find the smallest node that can reach the end