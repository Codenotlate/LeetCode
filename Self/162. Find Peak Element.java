// Phase3 solution
// binary search time O(logn)

class Solution {
    public int findPeakElement(int[] nums) {   	
        int s = 0;
        int e = nums.length - 1;
        while (s < e) {
        	int m = s + (e - s) / 2;
        	int prev = m - 1 >= 0 ? nums[m - 1] : Integer.MIN_VALUE;
        	int next = m + 1 < nums.length ? nums[m + 1] : Integer.MIN_VALUE;

        	if (nums[m] > prev && nums[m] > next) {return m;}
        	if (nums[m] > next) {
        		e = m;
        	} else {
        		s = m + 1;
        	}
        }

        return s;
    }
}

/*
Binary search works since :

1) only need to return any local peak
2) peak definition can be checked with 2 indices adjacent to the mid value. (nums[i] != nums[i + 1] for any i)
3) nums[-1] = nums[n] = -inf
*/

class Solution {
    public int findPeakElement(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            long left = mid - 1 < start ? -Long.MIN_VALUE : nums[mid - 1];
            long right = mid + 1 > end ? -Long.MIN_VALUE : nums[mid + 1];
            int middle = nums[mid];
            if (middle > left && middle > right) {return mid;}
            if (left < middle && middle < right) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }          
        }
        return start;
    }
}


// Phase3 self: similar as above, with self analysis below
class Solution {
    // O（logn) time => indicates binary search, and we can return index of any of the peak, meaning when we reduce range, we only need to make sure at least one peak is kept in remaining range.
    // The key is to compare nums[mid-1], nums[mid] and nums[mid+1]
    // also note nums[i] != nums[i+1] for all valid i
    // always check two element special case for binary search to avoid infinite loop
    
    public int findPeakElement(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            int left = mid >0? nums[mid-1]: Integer.MIN_VALUE;
            int right = mid <= nums.length - 2? nums[mid+1]: Integer.MIN_VALUE;
            if (nums[mid] > left && nums[mid] > right) {return mid;}
            if (nums[mid] < right) {
                start = mid + 1;
            } else {
                end = mid;
            }            
        }
        return start;
    }
}





// Review - more elegant than above ways
/*Initial thought
require O(logn) time, thus thinking about binary search. Since we assume nums[-1] = nums[n] = -inf and nums[i] != nums[i+1]. Thus when nums[mid] < nums[mid+1], we can guarantee at least one peek value in range[mid+1, end], and when nums[mid] > nums[mid+1], we can guarantee at least one peek value in range[start, mid].
*/
class Solution {
    public int findPeakElement(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[mid + 1]) {
                start = mid +1;
            } else {
                end = mid;
            }
        }
        return start;
    }
}