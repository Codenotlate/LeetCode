// Phase 3: based on solution Time O(n)
/* Use two pointers start from most left and most right. Each time only move the one with smaller a[i]. Cause the otherways don't have the potential to get a larger area size. Track the max area size.

proof idea:
* The widest container (using first and last line) is a good candidate, because of its width. Its water level is the height of the smaller one of first and last line.
* All other containers are less wide and thus would need a higher water level in order to hold more water.
* The smaller one of first and last line doesn't support a higher water level and can thus be safely removed from further consideration.

special case: when a[l] == a[r], we can move either side:
because if h[i] == h[j], no matter perform which strategy (i++ or j--), we only need to compare the max area only when both h[i++] and h[j--] are larger than the original h[i], in this case, the two pointers will automatically reach this two pointers, since we followed the strategy that choosing the smaller pointer to move.


explanation links:
https://leetcode.com/problems/container-with-most-water/discuss/6100/Simple-and-clear-proofexplanation
Very good one:
https://leetcode.com/problems/container-with-most-water/discuss/6099/Yet-another-way-to-see-what-happens-in-the-O(n)-algorithm

*/

class Solution {
    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
        	maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));
        	if (height[left] <= height[right]) {
        		left++;
        	} else {
        		right--;
        	}
        }
        return maxArea;
    }
}



//Phase3 self
class Solution {
    // use two pointer for start and end
    // each time move one pointer, since the area = (end-start) * Min(nums[start], nums[end]), if we move one pointer towards center, (end-start) part will definitely be smaller, thus only move the pointer with smaller height can give us potential chance to get a larger area with smaller (end-start) width. 
    public int maxArea(int[] height) {
        int start = 0;
        int end = height.length - 1;
        int maxarea = 0;
        while (start < end) {
            maxarea = Math.max(maxarea, (end - start) * Math.min(height[start], height[end]));
            if (height[start] < height[end]) {start++;}
            else{end--;}
        }
        return maxarea;
    }
}
























