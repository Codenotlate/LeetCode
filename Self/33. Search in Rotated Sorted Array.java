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
  




// Phase3 self
class Solution {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {return mid;}
            // pay attention the equal sign here
            if (nums[mid] >= nums[start]) {
                if (target > nums[mid] || target < nums[start]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            } else {
                if (target < nums[mid] || target >= nums[start]) { // pay attention the equal sign here
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }
        return -1;
    }
}


// The key is to draw out the 4 graphs in the goodnote and categories them

// self review
// slightly different conditions used for case distinguish, but similar idea as drawing out 4 cases
class Solution {
    /* initial thought
    O(logn) time requires binary search.
    */
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start <= end) {
            int mid = start + (end- start) /2 ;
            if (target == nums[mid]) {return mid;}
            if(target > nums[mid]) {
                if (nums[start] > nums[mid] && nums[start] <= target) {end = mid - 1;}
                else {start = mid +1;}
            } else {
                if (nums[mid] > nums[end] && nums[end] >= target) {start = mid +1;}
                else {end = mid -1;}
            }
        }
        return -1;
    }
}








// Review
/*Thought
Require O(logn) time and with sorted array, think about using binary search. The key is to find the sorted part and eliminate half of the range each time. There are three type of arrays. First type without rotation. Second type with rotation and mid is on first part. Third type with rotation and mid is on second part. By drawing out the graph we will notice for each condition(target > nums[mid] or target < nums[mid]), we will have one special case moving start/end poistions differently comparing to the other cases.
*/
class Solution {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {return mid;}
            else if (nums[mid] > target) {
                if(target < nums[start] && nums[mid] > nums[end]) {start = mid + 1;}
                else {end = mid - 1;}
            } else {
                if(target >= nums[start] && nums[start] > nums[mid]) {end = mid - 1;}
                else {start = mid + 1;}
            }            
        }
        return nums[start] == target? start: -1;
    }
}

/* Another idea instead of listing out all cases
Formula: If a sorted array is shifted, if you take the middle, always one side will be sorted. Take the recursion according to that rule.

1- take the middle and compare with target, if matches return.
2- if middle is bigger than left side, it means left is sorted
2a- if [left] < target < [middle] then do recursion with left, middle - 1 (right)
2b- left side is sorted, but target not in here, search on right side middle + 1 (left), right
3- if middle is less than right side, it means right is sorted
3a- if [middle] < target < [right] then do recursion with middle + 1 (left), right
3b- right side is sorted, but target not in here, search on left side left, middle -1 (right)
*/






// Review 23/3/17 - still need to draw out all graphs and do case by case summary on conditions, not a good way for interview I think. Also forgot the equal sign in all <= conditions with first try.
/*Thoughts
Binary search. M2 preferred.
M1: draw out 4 types of graphs
comparison between start, mid, end, target
target > mid case:
    start <= mid || target <= end: start = mid+1
    else: end = mid-1
target < mid case:
    start <= target|| mid <= end: end = mid-1
    else: start = mid+1 

M2:idea: every time we can only exclude the sorted part
if mid >= start : meaning left side sorted
    if start <= target < mid: end = mid-1
    else >: start = mid+1
else: meaning right side sorted
    if end >= target > mid: start = mid+1
    else <: end = mid-1



 */
// M1: not preferred
class Solution {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start < end) {
            int mid = start + (end-start) /2;
            if (nums[mid] == target) {return mid;}
            else if (nums[mid] < target) {
                if (nums[start] <= nums[mid] || target <= nums[end]) {
                    start = mid+1;
                } else{
                    end = mid-1;
                }
            } else {
                if (nums[start] <= target || nums[mid] <= nums[end]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }
        return nums[start] == target?start:-1;
    }
}

// M2: prefered way
class Solution {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start < end) {
            int mid = start + (end-start) /2;
            if (nums[mid] == target) {return mid;}
            if (nums[mid] >= nums[start]) {
                if (target < nums[mid] && target >= nums[start]) {end = mid-1;}
                else {start = mid + 1;}
            } else {
                if (target > nums[mid] && target <= nums[end]) {start = mid + 1;}
                else {end = mid-1;}
            }
        }
        return nums[start] == target?start:-1;
    }
}



// 2024.10.25 - same as above M2
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {return mid;}
            if (nums[mid] >= nums[left]) {
                if (nums[mid] > target && target >= nums[left]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
        }
        return nums[left] == target? left : -1;
    }
}







