class Solution {
	// Binary search time O(logn)
	// the key of removal condition is based on nums[mid] and nums[end]
    public int findMin(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
        	int mid = start + (end - start) / 2;
        	if (nums[mid] > nums[end]) {
        		start = mid + 1;
        	} else {
        		end = mid;
        	}
        }
        return nums[start];
    }
}