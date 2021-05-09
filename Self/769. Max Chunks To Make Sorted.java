class Solution {
	// Time O(n), space O(1)
	// the problem is reduce to find splitting lines so that
	// all numbers in the left must be smaller than numbers in the right
	// we only need to compare the max number up until now with the current index i
	// since this array is a permutation of 0 to arr.length - 1
    public int maxChunksToSorted(int[] arr) {
    	int curMax = -1;
    	int res = 0;
    	for (int i = 0; i < arr.length; i++) {
    		curMax = Math.max(curMax, arr[i]);
    		if (curMax == i) {res++;}
    	}
    	return res;
    }
}