class Solution {
	// method1: adjust to mid in odd position version
    public int singleNonDuplicate(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) { // not equal, because we can't have mid-1 or mid+1 when start==end
        	int mid = start + (end - start) / 2;
        	// adjust mid to always be in odd position
        	// because the removal of search space is different for mid in odd position or even position
        	if (mid % 2 != 1) {mid--;} 
        	if (nums[mid] == nums[mid + 1]) {
        		// meaning the single number is in the left side
        		end = mid - 1;
        	} else {
        		start = mid + 1;
        		// we won't have nums[mid] != nums[mid + 1] && nums[mid] != nums[mid - 1] case
        		// cause if that's the case, then mid must be in the position that mid % 2 != 1
        		// which can't happen since we adjust mid in advance
        	}

        }
        return nums[start];
    }
}



public class Solution {
	// method2: lo and high not regular index, but pair index
	// Basically you want to find the first even-index number not followed by the same number.
    public int singleNonDuplicate(int[] nums) {
        // binary search
        int n=nums.length, lo=0, hi=n/2;
        while (lo < hi) {
            int m = (lo + hi) / 2;
            if (nums[2*m]!=nums[2*m+1]) hi = m;
            else lo = m+1;
        }
        return nums[2*lo];
    }
}



//https://leetcode.com/problems/single-element-in-a-sorted-array/discuss/100754/Java-Binary-Search-short-(7l)-O(log(n))-w-explanations
// another way similar to method1
// just adjust to mid in even position version
// in this case, lo = mid + 2; h = mid.



