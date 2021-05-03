class Solution {
	// M1: iterative way, can also be done with recursion
	// iterative way: time O(logn) space O(1)
	// recursive way: time O(logn) space O(logn)
    public boolean isPowerOfTwo(int n) {
        //special case:
        if (n <= 0) {return false;}
        // loop
        while (n > 1) {
        	if (n % 2 != 0) {
        		return false;
        	}
        	 n /= 2;
        }
        return true;
    }
}



class Solution {
	// M2: bit manipulation
	// time O(1) space O(1)
	// if it's power of two, after remove the least significant 1 bit of it, it should be 0
    public boolean isPowerOfTwo(int n) {
    	if (n <= 0) {return false;}
    	return (n & (n - 1)) == 0;
    }
}

