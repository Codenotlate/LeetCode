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


// phase3 self: slower one O(n^2) time and O(n) space
class Solution {
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[n - 1] = 1;
        for (int i = n-2; i >= 0; i--) {
            for(int j = 1; j <= nums[i] && i + j < n; j++) {
                if (dp[i + j] == 1) {dp[i] = 1; break;}
            }
        }
        return dp[0] == 1;
    }
}

// similar way to M1: time O(n), space O(1)
class Solution {
    public boolean canJump(int[] nums) {
        int farp = 0;
        for (int i = 0; i < nums.length; i++) {
            farp = Math.max(farp,i + nums[i]);
            if (farp >= nums.length - 1) {return true;}
            if (i == farp) {return false;}
        }
        return false;
    }
}


// Review (similar as above line 17 idea)
/*Initial thought
Here we only care about whether we can reach the last index. 
Thus we can move backwards from the last index. And track the current smallest index that can reach the end. For each i, we only need to check if i + nums[i] can reach that smallest true index. If yes, update smallest to i. Move backwards till reach index 0.
*/
class Solution {
    public boolean canJump(int[] nums) {
        int minTrueIdx = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= minTrueIdx) {minTrueIdx = i;}
        }
        return minTrueIdx == 0;
    }
}