class Solution {
	// similar to problem 231
    public boolean isPowerOfFour(int n) {
        // special case
        if (n <= 0) {return false;}
        while (n > 1) {
        	if (n % 4 != 0) {return false;}
        	n /= 4;
        }
        return true;
    }
}


class Solution {
	// bit manipulation
	// if it's power of 4, it can only have signle 1 bit, 
	// and it's position should be in the last odd number position.
    public boolean isPowerOfFour(int n) {
        // special case
        if (n <= 0) {return false;}
        
        int count = 0;
        // right move until the last one bit is 1
        while ((n & 1) != 1) {
        	n >>>= 1;
        	count++;
        }
        // the # of zeros on the right side of last 1 bit should be even
        // and after removing the last 1bit, n should be 0.
        return (count % 2 == 0) && ((n >>> 1) == 0);
    }
}

class Solution {
	// bit manipulation
	// if it's power of 4, it can only have signle 1 bit, 
	// and it's position should be in the last odd number position.
    public boolean isPowerOfFour(int num) {
        return num > 0 && (num&(num-1)) == 0 && (num & 0x55555555) != 0;
    }
}
