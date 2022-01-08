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

// phase3 self
// M1: slow O(n) way
class Solution {
    public int mySqrt(int x) {
        if (x <= 3) {return x == 0? 0: 1;}
        int i = 2;
        for (i = 2; i <= x/2; i++) {
            if (i > x / i) {break;}
        }
        return i == x / i ? i : i - 1;
    }
}

// M2: BS O(logn) way
class Solution {
    // try binary search
    public int mySqrt(int x) {
        if (x <= 3) {return x == 0? 0: 1;}
        int low = 2;
        int high = x / 2;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (mid == x / mid) {return mid;}
            else if (mid < x / mid) {low = mid + 1;}
            else {high = mid - 1;}
            
        }
        return low > x / low? low -1 : low;
    }
}