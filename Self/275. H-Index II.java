// self phase 3
// time O(logn) -> binary search, since it's already sorted, thus the goal is to find the first index from left that has nums[i] >= n - i, return n - i.
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int start = 0;
        int end = n - 1;
        while (start < end) {
        	int mid = start + (end - start) / 2;
        	if (citations[mid] >= n - mid) {
        		end = mid; //since we want to find the first one from left, thus we can only delete the right half    		
        	} else {
        		start = mid + 1;
        	}
        }
        // note still need to check this, otherwise the answer is wrong for [0]
        return citations[start] >= n - start ? n - start : 0;
    }
}

// another way is to directly return n - mid when citations[mid] == n - mid