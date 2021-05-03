class Solution {
	// use DP, define dp[i] as the # of slices ends at index i and i must be included
    // time O(n) space O(n)
    public int numberOfArithmeticSlices(int[] A) {
    	if (A == null || A.length == 0) {return 0;}
    	int[] dp = new int[A.length];
    	for (int i = 2; i < A.length; i++) {
    		if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
    			dp[i] = dp[i - 1] + 1;
    		}
    	}
    	// return sum of dp[i] (slices ends in every position)
    	int res = 0;
    	for (int n: dp) {
    		res += n;
    	}
    	return res;
        
    }
}




class Solution {
	// can optimize above space to O(1)
    public int numberOfArithmeticSlices(int[] A) {
        int res = 0;
        int prev = 0;
        for (int i = 2; i < A.length; i++) {
    		if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
    			prev += 1;
    			res += prev;
    		} else {
    			prev = 0;
    		}
    	}
    	return res;
    }
}