// Phase 3 solution simplify self
// binary search time O(logn) space O(1)
// two cases
// and we only disgard the half based on the sorted part

class Solution {
    public int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;

        while (start <= end) {
        	int mid = start + (end - start) / 2;
        	if (nums[mid] == target) {return mid;}
        	// note! here should be >= instead of >
        	if (nums[mid] >= nums[start]) {
        		if (target >= nums[start] && target < nums[mid]) {
        			end = mid - 1;
        		} else {
        			start = mid + 1;
        		}
        	} else {
        		if (target > nums[mid] && target <= nums[end]) {
        			start = mid + 1;
        		} else {
        			end = mid - 1;
        		}
        	}
        }

        return -1;
    }
}
