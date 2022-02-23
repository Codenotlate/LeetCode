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