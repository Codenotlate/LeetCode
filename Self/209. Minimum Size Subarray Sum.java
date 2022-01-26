// M1: slower solution Binary Search using accumulated sum
// time O(nlogn), space O(n)

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        // array to store cumulative sum
        int n = nums.length;
        int[] cumSum = new int[n];
        cumSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
        	cumSum[i] = cumSum[i - 1] + nums[i];
        }

        int minLen = nums.length + 1;

        for (int i = 0; i < n ; i++) {
        	// findFirst returns the position j where cumsum[j] >= target + cumsum[i] - nums[i], if no, return -1
        	int j = findFirst(cumSum, i, n - 1, target + cumSum[i] - nums[i]);
        	if (j != -1) {
        		minLen = Math.min(minLen, j - i + 1);
        	}
        }

        return minLen == nums.length + 1 ? 0 : minLen;
    }


    private int findFirst(int[] cumSum, int start, int end, int target) {
   	
    	while (start < end) {
    		int mid = start + (end - start) / 2;
    		if (cumSum[mid] >= target) {
    			end = mid;
    		} else {
    			start = mid + 1;
    		}
    	}

    	return cumSum[start] >= target ? start : -1;
    }
}



// M2: O(n) sliding window way
// each position is at most scanned twice

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minLen = nums.length + 1;
        int curSum = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
        	curSum += nums[right];
        	while (curSum >= target) {
        		minLen = Math.min(minLen, right - left + 1);
        		curSum -= nums[left];
        		left++;
        	}
        }

        return minLen == nums.length + 1 ? 0 : minLen;
    }
}


/* related to post of problem 76
https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
1. Use two pointers: start and end to represent a window.
2. Move end to find a valid window.
3. When a valid window is found, move start to find a smaller window.
*/



// Review self: similar idea as above M2 but code less clean
class Solution {
    // initial thought: sliding window, find first window with sum >= target, then remove the left side of the window as long as the window sum >= target, record the min length along the way.Until the right side of the window reaches the end of the array
    // time: O(n) since each element in nums being processed at most twice(add in/ remove from the window) space O(1)
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int minLen = target;
        // set the first window
        int subsum = 0;
        while(right < nums.length) {
            subsum += nums[right];
            if (subsum >= target) {break;}
            right++;
        }
        if (right == nums.length) {return 0;}
        minLen = Math.min(minLen, right-left+1);
        while(right < nums.length) {
            while(left <= right && subsum - nums[left] >= target) {subsum -= nums[left]; left++;}
            if (left == right) {return 1;}
            minLen= Math.min(minLen, right-left+1);
            right++;
            if(right < nums.length) {subsum += nums[right];}
                        
        }
        return minLen;
        
    }
}



class Solution {
    // above way optimized. slightly different moving window rule: first find the window, then move the left side to update the minLen until the window sum is < target, move right side to find the window again and repeat the process.
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int minLen = target;
        // set the first window
        int subsum = 0;
        while(right < nums.length) {
            subsum += nums[right];
            while (subsum >= target) {
                minLen = Math.min(minLen, right-left+1);
                subsum -= nums[left];
                left++;
            }
            right++;
        }
        return minLen == target ? 0 : minLen;
        
    }
}







