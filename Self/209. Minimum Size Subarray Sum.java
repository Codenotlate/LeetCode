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





// Review
/*Initial thought
Need to be contiguous, thus can't sort. process element one by one using sliding window. using two pointers, left and right. left and right both init as 0. windowSum = 0. At each round, if windowSum < target, right++, windowSum += nums[right]; else update minRes with (right - left + 1). then left++, windowSum -= nums[left-1]. Also if minRes = 1 we can return directly since 1 is the minimum possible valid answer. And since there could be no answer, we need to init minRes = nums.length + 1. And if in the end minRes still == nums.length + 1, then we return 0. This way, time O(n) space O(1)
e.g. target = 7, nums = [2,3,1,2,4,3]
2,3,1,2 => 4
3,1,2,4 => 4
1,2,4 => 3
2,4,3 => 3
4,3 => 2
3


followup: find a O(nlogn) way. Have accumulated sum [2,5,6,8,12,15]. for each num in pos i, use binary search to find the first pos with value >= (target - nums[i] + accumsum[i]) in sorted array sum[i+1:end]. [The first method above]
*/
// similar as above sliding window way, but each time only move one position. Easier to remeber and implement.
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int minres = nums.length + 1;
        int windowSum = nums[0];
        while (right < nums.length) {
            if(windowSum < target) {
                right++;
                if(right >= nums.length) {break;}
                windowSum += nums[right];
            } else {
                minres = Math.min(minres, right - left + 1);
                if(minres == 1) {return minres;}
                left++;
                windowSum -= nums[left-1];
            }
        }
        return minres == nums.length + 1 ? 0 : minres;
    }
}




