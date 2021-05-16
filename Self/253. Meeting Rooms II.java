// Phase3 solution time O(nlogn) space O(n)
// sort the array using start time, then use a minHeap to store end time
// minHeap represents all the on-going meetings
// adjust the heap by comparing the cur meeting with the on-going meeting with earliest end time. 

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // first sort the array
        Arrays.sort(intervals, (i1, i2) -> (i1[0] - i2[0]));
        // build a minHeap to store the end time of each on-going meeting
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] i: intervals) {
        	if (minHeap.size() > 0) {
        		int minEnd = minHeap.top();
        		if (i[0] >= minEnd) {
        			minHeap.pop();
        		}
        	}
        	minHeap.add(i[1]);
        }
        return minHeap.size();
    }
}



// phase3 solution M2: chronological sort
/* sort start and end separately, count++ when start < end, else end++
return count after loop every start.
Time O(nlogn) space O(n)
*/


class Solution {
    public int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
        	starts[i] = intervals[i][0];
        	ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int endpos = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
        	if (starts[i] < ends[endpos]) {
        		count++;
        	} else {
        		endpos++;
        	}
        }
        return count;
    }
}

















// post can take a look later
// https://leetcode.com/problems/meeting-rooms-ii/discuss/203658/HashMapTreeMap-resolves-Scheduling-Problem


