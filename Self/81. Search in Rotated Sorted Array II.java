// As a followup to problem 33 (in this problem, we have duplicates)
// worst time case: O(n) 1111111111116 to find target 6
// best time O(logn)

class Solution {
    public boolean search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
        	int mid = start + (end - start) / 2;
        	if (nums[mid] == target) {
        		return true;
        	} 
        	if (nums[start] < nums[mid]) {
        		if (target < nums[mid] && target >= nums[start]) {
        			end = mid - 1;
        		} else {
        			start = mid + 1;
        		}
        	} else if (nums[start] > nums[mid]) {
        		if (target > nums[mid] && target <= nums[end]) {
        			start = mid + 1;
        		} else {
        			end = mid - 1;
        		}
        	} else { // additional case compared to 33 when nums[mid] == nums[start]
        		start++; // in this case, left is the only search range we can eliminate
        	}
        }

        return false;
    }
}





/* another way: also O(n) but this will be slower in average case
To avoid duplicates, we can refer to the solution to Problem 15: 3 Sum, which is

      while (lo < hi && nums[lo] == nums[lo + 1])
        ++lo;
      while (lo < hi && nums[hi] == nums[hi - 1])
        --hi;
After this step, this problem becomes Problem 33: Search in Rotated Sorted Array.

Below is my complete code:
*/
public boolean search(int[] nums, int target) {
	if (null == nums || 0 == nums.length)
	  return false;

	int lo = 0;
	int hi = nums.length - 1;
	while (lo <= hi) {
	  // to avoid duplicates
	  while (lo < hi && nums[lo] == nums[lo + 1])
	    ++lo;
	  while (lo < hi && nums[hi] == nums[hi - 1])
	    --hi;
	  
	  // the code below is exactly the same with Problem 33.
	  int mid = lo + (hi - lo) / 2;
	  if (nums[mid] == target)
	    return true;
	  
	  if (nums[mid] >= nums[lo]) {
	    if (target >= nums[lo] && target < nums[mid])
	      hi = mid - 1;
	    else
	      lo = mid + 1;
	  } else {
	    if (target <= nums[hi] && target > nums[mid])
	      lo = mid + 1;
	    else
	      hi = mid - 1;
	  }
	}

	return false;
}



// Phase 3 self
class Solution {
    public boolean search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start)/2;
            if (nums[mid] == target) {return true;}
            if (nums[start] < nums[mid]) {
                if (nums[start] <= target && target < nums[mid]) {
                    end = mid -1;
                } else {
                    start = mid + 1;
                }
            } else if (nums[start] > nums[mid]) {
                if (nums[mid] < target && nums[end] >= target) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            } else {// can only remove nums[start] if nums[start] == nums[mid]
                start += 1;
            }
        }
        return false;
    }
}
