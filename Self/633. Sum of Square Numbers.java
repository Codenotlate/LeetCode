class Solution {
	// just use two pointers method same as 167
	// since we have square, the search range is from 0 to (int) sqrt(c)
    public boolean judgeSquareSum(int c) {
        int end = (int) Math.sqrt(c);
        int start = 0;
        // since a could be equal to b
        while (start <= end) {
        	int sum = start * start + end * end;
        	if (sum > c) {
        		end -= 1;
        	} else if (sum < c) {
        		start += 1;
        	} else {
        		return true;
        	}
        }
        return false;
    }
}