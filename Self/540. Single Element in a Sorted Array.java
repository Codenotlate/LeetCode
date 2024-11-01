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

// phase3 self(above M1 is a better way)
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            // case1: if number in left and right side is even
            if ((mid - low) % 2 == 0) {
                if (nums[mid] == nums[mid - 1]) {
                    high = mid - 2;
                } else if (nums[mid] == nums[mid + 1]) {
                    low = mid + 2;
                } else {return nums[mid];}
            } else { // number in both sides are odd
                if (nums[mid] == nums[mid - 1]) {
                    low = mid + 1;
                } else if (nums[mid] == nums[mid + 1]) {
                    high = mid - 1;
                }             
            }
        }
        return nums[low];
    }
}


// Review
/* Initial thought
require O(logn) time and the array is sorted -> hint for Binary search
condition for half the searching range each time: compare nums[mid] with nums[mid-1] and nums[mid + 1].
if not equal to both, return nums[mid]
if nums[mid] == nums[mid-1]: if (end - mid) % 2 == 0,  then end = mid - 2;else start = mid + 1;
similarly for nums[mid] == nums[mid +1].
break when start == end, return nums[start]
*/
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            boolean isEven = (end - mid) % 2 == 0;
            if (nums[mid] == nums[mid - 1]) {
                if (isEven) {
                    end = mid - 2;
                } else {
                    start = mid + 1;
                }
            } else if (nums[mid] == nums[mid + 1]) {
                if (isEven) {
                    start = mid + 2;
                } else {
                    end = mid - 1;
                }
            } else {
                return nums[mid];
            }
        }
        return nums[start];
    }
}
// Or like above M1 way. Always adjust mid to be at odd positions, so that we don't need to move differently based on even and odd cases.




// Review 23/1/13
/*Thoughts - similar as two above self ways
O(n) way is trivial. O(logn) way - binary search.
Need to consider about even or odd case. compare mid with mid+1 and mid - 1
if both sides len is even, then if either side == mid, then meaning the single number is on that side. Otherwise mid is the number.
if both sides len is odd, then if either side == mid, then meaning the single number is on the other side.
If we move the range carefully, we won't have cases when one side is odd len and the other is even len.
 */
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if ((mid - start) % 2 == 0) {
                if (nums[mid] == nums[mid + 1]) {
                    start = mid + 2;
                } else if (nums[mid] == nums[mid - 1]) {
                    end = mid - 2;
                } else {
                    return nums[mid];
                }
            } else {
                if (nums[mid] == nums[mid + 1]) {
                    end = mid - 1;
                } else  {
                    start = mid + 1;
                } 
            }
        }
        return nums[end];
    }
}


// practice above M1 way, as it's a better way. - basically to adjust mid positin to always have both sides with odd len.
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if ((mid - start) % 2 == 0) {mid++;}
            if (nums[mid] == nums[mid+1]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return nums[end];
    }
}



// 2024.10.29
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int  left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right-left) / 2;
            if ((mid - left + 1)%2 != 0) {mid++;} // make sure left till mid is always even length
            if (nums[mid-1]==nums[mid]) {
                left = mid + 1;
            } else {
                right = mid-1; // This is important, right = mid is not possible here. right = mid will cause infinite loop.
            }
        }
        return nums[left];
    }
}