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


// phase 3 self
class Solution {
    public int findMin(int[] nums) {
        int s = 0; 
        int e = nums.length - 1;
        
        while (s <= e) {
            int m = s + (e - s) / 2;
            // if it's sorted(no rotate, then can return nums[s] directly)
            if (nums[s] <= nums[e]) {return nums[s];} 
            if (nums[s] <= nums[m]) {
                s = m + 1;
            } else {
                e = m;
            }
        }
        return nums[s];
    }
}


// Phase 3 self
class Solution {
    public int findMin(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) /2 ;
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return nums[start];
    }
}