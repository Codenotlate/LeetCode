class Solution {
	// use binary search
	// time O(logn) space O(1)
    public int mySqrt(int x) {
    	if (x == 0) {return 0;}
        int left = 1;
        int right = x / 2;
        while (left <= right) {
        	int mid = left + (right - left ) / 2;
        	if (mid <= x / mid && (mid + 1) > x / (mid + 1)) {
        		return mid;
        	} else if (mid > x / mid) {
        		right = mid - 1;
        	} else {
        		left = mid + 1;
        	}
        }
        return left;
    }
}

// other solutions
// https://leetcode.com/problems/sqrtx/discuss/25198/3-JAVA-solutions-with-explanation