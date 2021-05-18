// Phase3 based on solution
/* two pointers label first and second, first guarantee no element smaller than it before,
and second guarantee there's one element smaller than it before.
update the first and second along the way.
If the cur > second, then return true directly
if cur < first, update first to cur, cause this enlarged potential true solution range, but won't exclude the one with previous first
if first < cur < second, update second to cur, based on same reason above.

time O(n), space O(1)
*/

class Solution {
    public boolean increasingTriplet(int[] nums) {
    	if (nums.length <= 2) {return false;}
        int first = nums[0];
        int second = Integer.MAX_VALUE;
        for (int n: nums) {
        	if (n > second) {return true;}
        	if (n < first) {
        		first = n;
        	} else if (first < n && n < second) { // pay attention to exclude equal condition
        		second = n;
        	}
        }
        return false;
    }
}

// can be understood as a simplified version of LIS