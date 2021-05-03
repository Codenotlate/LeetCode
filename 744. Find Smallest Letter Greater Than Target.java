class Solution {
	// binary search
	// time O(logn), space O(1)
    public char nextGreatestLetter(char[] letters, char target) {
        int start = 0;
        int end = letters.length - 1;
        while (start < end) {
        	int mid = start + (end - start) / 2;
        	if (letters[mid] <= target) {
        		start = mid + 1;
        	} else {
        		end = mid;
        	}
        }
        // since the loop ends when start = end, leaving start unchecked
        // we need to recheck
        if (letters[start] > target) {return letters[start];}
        // because of the wrap up rule
        return letters[0];
    }
}