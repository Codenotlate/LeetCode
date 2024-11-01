/* Initial thought (use below improved way)
from hint, use sliding window. left side starts with each starting 1, then expand right side, if encounter 0 and k > 0, we can keep moving right and k-- until k == 0 or right reach the end. After update the max len of the window, we move left to the next starting 1.
note if right reach the end before k <= 0, we need to add remaining k to the window size and check the maxlen in the end, to cap it as the length of nums.
we only expand window to the right side because the left side will be covered by the previous 1 window. And the leading zeros will be covered by adding remaining k to window length.
time O(n) space O(1)
*/

// try own sliding window way first
class Solution {
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int maxLen = 0;
        int n = nums.length;
        
        while(right < n && left < n) {
            // find the first 1
            int count = k;
            while(left < n && nums[left] == 0) {left++;}
            right = left;
            while(right < n && count >= 0) {
                if (nums[right] == 0) {count--;}
                right++;
            }
            maxLen = Math.max(maxLen, right - left + count);
            while(left < n && nums[left] == 1) {left++;}
        }
        
        return Math.min(maxLen, n);
 
        
    }
}


/* Improved way from solution
use sliding window, the left and right start at 0, we expand the window till count < 0, then move left one step forward. Also since we want the maxLen, thus we can higher the efficiency by having the following window set having a starting size as the maxLen at that time. (because if the window size is smaller than maxLen, even if it's valid, it's not the answer.)
since we don't shrink the window and only maintain it to the largest valid one it has reached. Thus in the end, the window size is the largest.((i,j) may not refer to the maxLen window range, but the length is the same).

time O(n) space O(1)
*/

// try own sliding window way first
class Solution {
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int right = 0;
        
        while (right < nums.length) {
            if (nums[right] == 0) {k--;}
            if (k < 0) {
                if (nums[left] == 0) {k++;}
                // move both sides to maintain the window size
                left++;
            }
            right++;
        }
        return right - left;
        
    }
}



// solution and similar questions list
// https://leetcode.com/problems/max-consecutive-ones-iii/discuss/247564/JavaC%2B%2BPython-Sliding-Window








// Review - similar to above M1
/* Thought
if use dp way, time O(nk) space O(k)
Greedy way - sliding window. a left pointer move one step each time, the right pointer keep forward until right == nums.len or (k==0 && nums[right] == 0). Along the way k-- if cell = 0. End the right loop, update maxlen based on right-left. Continue the outside loop until left or right == nums.len.
time O(n) space O(1)
*/
class Solution {
    public int longestOnes(int[] nums, int k) {
        int maxlen = 0;
        int left = 0;
        int right = left;
        while (right < nums.length && left < nums.length) {
            while (right < nums.length && (nums[right] == 1 || k > 0)) {
                if (nums[right] == 0) {k--;}
                right++;
            }
            maxlen = Math.max(maxlen, right - left);
            if (nums[left] == 0) {k++;}
            left++;
        }
        return maxlen;
    }
}




// 2024/3/27 - same as above S2
/** This solution strictly follows the template from Q76.
Time O(n) space O(1)
*/
class Solution {
    public int longestOnes(int[] nums, int k) {
        int maxLen = 0;
        int left = 0;
        int right = 0;
        while(right < nums.length) {
            if (nums[right]== 0) {k--;}
            if (k < 0) { // starts to shrink, and because we want max len, we don't need to keep shrinking, thus 'if' instead of 'while'.
                if(nums[left]==0) {k++;}
                left++;
            }
            maxLen = Math.max(maxLen, right-left+1);
            right++;
        }
        return maxLen;
    }
}



//2024.10.31
// sliding window, and since asking for longest window length, don't need to shrink multiple steps for left side each time, just one step is enough.
// O(n) - similar idea as above
class Solution {
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int maxLen = 0;
        int curZeros = 0;
        while (right < nums.length) {
            if (nums[right] == 0) {curZeros++;}
            if(curZeros <= k) {
                maxLen = Math.max(maxLen, right - left+1);
            } else {
                if (nums[left] == 0) {curZeros--;}
                left++;
            }
            right++;
        }
        return maxLen;
    }
}