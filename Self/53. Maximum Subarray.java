class Solution {
	// method1 time O(n) space O(1)
	// this method is actually DP
    public int maxSubArray(int[] nums) {
        // special case:
        if (nums.length == 1) {return nums[0];}
        int curSum = nums[0];
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
        	if (curSum <= 0) {
        		curSum = nums[i];
        	} else {
        		curSum += nums[i];
        	}
        	maxSum = Math.max(maxSum, curSum);
        	
        }
        return maxSum;
    }
}
// https://leetcode.com/problems/maximum-subarray/discuss/20193/DP-solution-and-some-thoughts




class Solution {
	// method2: divide and conquer
	// time O(nlogn) space O(logn)
    public int maxSubArray(int[] nums) {
        return maxSubArray(nums, 0, nums.length - 1);
    }

    private int maxSubArray(int[] nums, int left, int right) {
    	if (left == right) {return nums[left];}
    	int mid = left + (right - left) / 2;
    	int leftSum = maxSubArray(nums, left, mid);
    	int rightSum = maxSubArray(nums, mid + 1, right);

    	int midToLeftSum = 0;
    	int midToRightSum = 0;
    	int l = mid;
    	int r = mid + 1;
    	int midToLeftMax = Integer.MIN_VALUE;
    	int midToRightMax = Integer.MIN_VALUE;

    	while (l >= 0) {
    		midToLeftSum += nums[l];   		
    		midToLeftMax = Math.max(midToLeftMax, midToLeftSum);
    		l--;
    	}
    	while (r <= nums.length - 1) {
    		midToRightSum += nums[r];
    		midToRightMax = Math.max(midToRightMax, midToRightSum);
    		r++;
    	}

    	return Math.max(Math.max(leftSum, rightSum), midToRightMax + midToLeftMax);
    }
}




// method3: it's still divide and conquar, but with optimized conquer method O(1)
// thus reduce time to O(n)
// this is achieved by returning more information from each recusive call of the two halves
// detail explanation in below page

class Solution {
	private class Val {
		int maxPrefix;
		int maxSuffix;
		int wholeSum;
		int maxSub;

		Val(int l, int e, int all, int m) {
			maxPrefix = l;
			maxSuffix = e;
			wholeSum = all;
			maxSub = m;
		}
	}

    public int maxSubArray(int[] nums) {
        Val val = maxSubArrayHelper(nums, 0, nums.length - 1);
        return val.maxSub;
    }

    private Val maxSubArrayHelper(int[] nums, int left, int right) {
    	if (left == right) {return new Val(nums[left], nums[left], nums[left], nums[left]);}
    	int mid = left + (right - left) / 2;
    	Val v1 = maxSubArrayHelper(nums, left, mid);
    	Val v2 = maxSubArrayHelper(nums, mid + 1, right);

    	// update those 4 values based on v1 and v2
    	int maxPrefix = Math.max(v1.maxPrefix, v1.wholeSum + v2.maxPrefix);
    	int maxSuffix = Math.max(v2.maxSuffix, v2.wholeSum + v1.maxSuffix);
    	int wholeSum = v1.wholeSum + v2.wholeSum;
    	int maxSub = Math.max(Math.max(v1.maxSub, v2.maxSub), v1.maxSuffix + v2.maxPrefix);

    	return new Val(maxPrefix, maxSuffix, wholeSum, maxSub);

    }
}

//https://leetcode.com/problems/maximum-subarray/discuss/20200/Share-my-solutions-both-greedy-and-divide-and-conquer
//https://leetcode.com/problems/maximum-subarray/discuss/199163/Python-O(N)-Divide-and-Conquer-solution-with-explanations



// Phase 3 self precise way of M1
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int curSum = 0;
        
        for (int n: nums) {
            curSum = Math.max(n, n + curSum);
            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }
}





