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
/* M2:similar idea as M1, for each current meeting, we care about the earliest end time of all exsiting meetings. Thus we can sort the start time and end time separately into two arrays. Then loop start array and compare to end array. if curstart < curend, means no matter what is the endtime corresponding to curstart, it is later than curend, and since curend corresponding meeting hasn't ended, we need an extra room, thus count++. Else meaning there's one meeting ended, we can move curend one step forward.
time O(nlogn)  space O(n)
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




// review self
class Solution {
    /* M1: using a priorityqueue to mimic the meeting room occupation condition. PQ(sorted by end time) provides us the earliest end time of all existing meetings, which makes it easier to determine whether a room is freed for current meeting. or we need to add an extra room (by adding the end time of the current meeting in to the PQ). In the end, the size of the PQ is the number of rooms we need.
    time O(nlogn)  space O(n)
    */
    public int minMeetingRooms(int[][] intervals) {
        PriorityQueue<Integer> endheap = new PriorityQueue<>();
        Arrays.sort(intervals, (e1, e2) -> (e1[0] - e2[0]));
        for (int[] itv: intervals) {
            if (!endheap.isEmpty() && itv[0] >= endheap.peek()) {endheap.poll();}
            endheap.add(itv[1]);
        }
        return endheap.size();
    }
}











// Review
/*Thought
Two ways.
M1 way, sort by start time and then using PQ. mimic the actual process. The PQ will be ordered based on end time. min end time at the top. process intervals one by one. while cur interval start > pq.peek[end], then keep poll out from PQ. Outside the while loop, add cur[1] into PQ, update max size of PQ.
time O(nlogn) space O(n) for PQ
*/
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (i1,i2)->(i1[0]- i2[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int res = 0;
        for (int[] cur: intervals) {
            while(!pq.isEmpty() && pq.peek() <= cur[0]) {pq.poll();}
            pq.add(cur[1]);
            res = Math.max(res, pq.size());
        }
        return res;
    }
}
// next time implement way2: sort start and end separately







// Review 23/3/27 (self come up with M1, M2 with little hint)
/*Thoughts
M1: sort the intervals based on start time, then add it to a pq sorted in ending time to mimic the logic. If the least ending time is >= current meeting start time, we need a new room. Track the max size of the pq along the process.
Time O(nlogn + nlogn) = O(nlogn)
space O(logn + n) = O(n)

M2: similar as above idea, sort the start and end into two arrays. Then two pointers to check start and end separaterly, if curstart >= curend, curend++, size--. In both case, curstart++, size++. Again track the maxSize along the process.
time O(nlogn + nlogn + n) = O(nlogn)
space O(n + n +logn + logn) = O(n)

 */
// M1:
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (i1,i2) -> (i1[0] - i2[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int size = 0;
        int maxSize = 0;
        for (int[] itv: intervals) {
            if (!pq.isEmpty() && pq.peek() <= itv[0]) {
                pq.poll();
                size--;
            } 
            pq.add(itv[1]);
            size++;
            maxSize = Math.max(maxSize, size);
        }
        return maxSize;

    }
}
// M2:
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;
        int[] start = new int[n];
        int[] end = new int[n];
        for (int i = 0; i < n; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start);
        Arrays.sort(end);
        int startpt = 0;
        int endpt = 0;
        int size = 0;
        int maxSize = 0;
        while(startpt < n && endpt < n) {
            if (start[startpt] >= end[endpt]) {
                endpt++;
                size--;
            }
            startpt++;
            size++;
            maxSize = Math.max(maxSize, size);
            
        }
        return maxSize;

    }
}








