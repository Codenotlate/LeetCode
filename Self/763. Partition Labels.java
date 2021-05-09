class Solution {
	// method1: using iterval expanding
	// time O(n) space O(1) since only lowercase letters
    public List<Integer> partitionLabels(String S) {
        // build the [start, end] interval for each char in S
        // store in a list of 26 buckets
        int[][] intervals = new int[26][2];
        for (int i = 0; i < intervals.length; i++) {
        	intervals[i][0] = -1;
        	intervals[i][1] = -1;
        }
        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	// set the start of this char first time have this char
        	if (intervals[idx][0] == -1 ) {
        		intervals[idx][0] = i;
        	}
        	// update the end each time
        	intervals[idx][1] = i;
        }

        // go through s again and track the start and end of the current window
        int start = 0;
        int end = 0;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	if (intervals[idx][0] <= end) { // meaning overlap
        		end = Math.max(end, intervals[idx][1]);
        	} else { // nonoverlap
        		res.add(end - start + 1);
        		start = intervals[idx][0];
        		end = intervals[idx][1];
        	}
        }
        res.add(end - start + 1);
        return res;
    }
}



// method2: optimize method1, actually no need to keep track of start of the interval
// only need to expand end of the window each time, 
// and start will remain same until encounter a non-overlapping interval
// which is detected by index of s reached current end.
class Solution {
    public List<Integer> partitionLabels(String S) {
        // build a map to store the end index of each char in S
        int[] endPositions = new int[26];

        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	endPositions[idx] = i;
        }

        // second pass of S
        int start = 0;
        int end = 0;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	end = Math.max(endPositions[idx], end);

        	// when nonoverlapping
        	if (i == end) {
        		res.add(end - start + 1);
        		start = i + 1;
        	}
        }
        return res;
    }
}

// can also do it in sliding window way
//https://leetcode.com/problems/partition-labels/discuss/113264/Easy-O(n)-Java-solution-using-sliding-window-(two-pointers)-comments-and-explanation-given























