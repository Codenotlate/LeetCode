class Solution {
	// M1 self: regular dp as LIS, time O(n^2) space O(n)
    public int wiggleMaxLength(int[] nums) {
        if (nums == null) {return 0;}
        if (nums.length <= 1) {return nums.length;}
        int n = nums.length;
        
        int[] diff = new int[n - 1];
        // use dp[i] to store max len for diff[0, i]
        int[] dp = new int[n - 1];
        // loop first time to get diff array and at the same time initialize dp
        for (int i = 0; i < n - 1; i++) {
        	diff[i] = nums[i + 1] - nums[i];
        	dp[i] = diff[i] != 0 ? 2 : 1;
        }

        for (int i = 1; i <= n - 2; i++) {
        	for (int j = i - 1; j >= 0; j--) {
        		if (diff[i] > 0 && diff[j] < 0 || (diff[i] < 0 && diff[j] > 0)) {
        			dp[i] = dp[j] + 1;
        			break;
        		}
        	}
        }

        // return max dp[i]
        int res = 0;
        for (int l: dp) {
        	res = Math.max(res, l);
        }
        return res;

    }
}



class Solution {
	// M2 self: based on M1, optimize to O(n) time， space O(n)
	// use prePos and preNeg to record the most recent position of positve diff and negative diff
    public int wiggleMaxLength(int[] nums) {
    	if (nums == null) {return 0;}
        if (nums.length <= 1) {return nums.length;}
        int n = nums.length;
        
        int[] diff = new int[n - 1];
        // use dp[i] to store max len for diff[0, i]
        int[] dp = new int[n - 1];
        // loop first time to get diff array and at the same time initialize dp
        for (int i = 0; i < n - 1; i++) {
        	diff[i] = nums[i + 1] - nums[i];
        	dp[i] = diff[i] != 0 ? 2 : 1;
        }

    	int prePos = -1, preNeg = -1;
    	for (int i = 0; i <= n - 2; i++) {
        	if (diff[i] > 0) {
        		if (preNeg >= 0) {
        			dp[i] = dp[preNeg] + 1;
        		}
        		prePos = i;
        	}
        	if (diff[i] < 0) {
        		if (prePos >= 0) {
        			dp[i] = dp[prePos] + 1;
        		}
        		preNeg = i;
        	}

        }

        // return max dp[i]
        int res = 0;
        for (int l: dp) {
        	res = Math.max(res, l);
        }
        return res;      
    }
}



class Solution {
	// M2.1 self: based on M2, optimize to O(1) space
	// use prePos and preNeg to record the most recent position of positve diff and negative diff
    // use prePosLen to record dp[prePos] and preNegLen to record dp[preNeg]
    // use res to record max len.
    public int wiggleMaxLength(int[] nums) {
    	if (nums == null) {return 0;}
        if (nums.length <= 1) {return nums.length;}
        int n = nums.length;

    	int prePos = -1, preNeg = -1;
    	int prePosLen = 2, preNegLen = 2;
    	int maxLen = 1;
    	for (int i = 0; i <= n - 2; i++) {
    		int diff = nums[i + 1] - nums[i];
        	if (diff > 0) {
        		if (preNeg >= 0) {
        			//dp[i] = dp[preNeg] + 1;
        			prePosLen = preNegLen + 1;
        		}
        		prePos = i;
        		maxLen = Math.max(maxLen, prePosLen);
        	}
        	if (diff < 0) {
        		if (prePos >= 0) {
        			//dp[i] = dp[preNeg] + 1;
        			preNegLen = prePosLen + 1;
        			
        		}
        		preNeg = i;
        		maxLen = Math.max(maxLen, preNegLen);
        	}

        }

        return maxLen;     
    }
}
// another way of DP, using 2 1d dp arrays: up[] and down
// https://leetcode.com/problems/wiggle-subsequence/solution/
// https://leetcode.com/problems/wiggle-subsequence/discuss/84843/Easy-understanding-DP-solution-with-O(n)-Java-version


public class Solution {
	// M4: greedy way O(n) time, O(1) space
	// https://leetcode.com/problems/wiggle-subsequence/solution/
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2)
            return nums.length;
        int prevdiff = nums[1] - nums[0];
        int count = prevdiff != 0 ? 2 : 1;
        for (int i = 2; i < nums.length; i++) {
            int diff = nums[i] - nums[i - 1];
            if ((diff > 0 && prevdiff <= 0) || (diff < 0 && prevdiff >= 0)) {
                count++;
                prevdiff = diff;
            }
        }
        return count;
    }
}























