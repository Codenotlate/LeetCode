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