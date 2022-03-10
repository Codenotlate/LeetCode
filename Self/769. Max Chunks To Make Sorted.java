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


// Phase3 review: similar as above M1
class Solution {
    public int maxChunksToSorted(int[] arr) {
        int curEnd = -1;
        int group = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i > curEnd) {
                curEnd = Math.max(i, arr[i]);
                group++;
            } else {
                curEnd = Math.max(curEnd, arr[i]);
            }
        }
        return group;
    }
}


/*Initial thought
for each curnum, position curnum is the right range of the chunk it needs to belong to. So track curRight. If i == curRight and curNum(i.e. arr[i])<= curRight, we got a valid chunk, res++. curRight = i+1. Otherwise, update the curRight(max(curRight, curnum)).

time O(n) 
space O(1)
*/
class Solution {
    public int maxChunksToSorted(int[] arr) {
        int res = 0;
        int curRight = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (i == curRight && arr[i] <= curRight) {
                res++;
                curRight = i < arr.length - 1 ? arr[i+1] : -1;
            } else {
                curRight = Math.max(curRight, arr[i]);
            }
        }
        return res;
    }
}