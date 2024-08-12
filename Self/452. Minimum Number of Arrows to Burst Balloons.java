class Solution {
	// This problem can be converted to find the max # of non-overlap intervals.
	// because for each non-overlap interval, we need at least one arrow to burst it
	// and in order to busrt all balloons, we at least need to burst all those non-overlapped intervals.
	// Time O(nlogn) space O(1)
    public int findMinArrowShots(int[][] points) {
        // special case
        if (points.length == 0) {return 0;}
        // sort the intervals based on end
        Arrays.sort(points, new Comparator<int[]>() {
        	@Override
        	public int compare(int[] o1, int[] o2) {
        		return (o1[1] > o2[1] ? 1 : (o1[1] < o2[1] ? -1 : 0));
        	}
        });

        // set end and count
        int end = points[0][1];
        int count = 1;
        for (int i = 0; i < points.length; i++) {
        	if (points[i][0] > end) {
        		end = points[i][1];
        		count++;
        	}
        }

        return count;
    }
}

/**
Here I provide a concise template that I summarize for the so-called "Overlapping Interval Problem", e.g. Minimum Number of Arrows to Burst Balloons, and Non-overlapping Intervals etc. I found these problems share some similarities on their solutions.

1)Sort intervals/pairs in increasing order of the start position.
2)Scan the sorted intervals, and maintain an "active set" for overlapping intervals. At most times, we do not need to use an explicit set to store them. Instead, we just need to maintain several key parameters, e.g. the number of overlapping intervals (count), the minimum ending point among all overlapping intervals (minEnd).
3)If the intervasl that we are currently checking overlaps with the active set, which can be characterized by cur.start > minEnd, we need to renew those key parameters or change some states.
4)If the current interval does not overlap with the active set, we just drop current active set, record some parameters, and create a new active set that contains the current interval.

*/



// 20240810
// Didn't pass casea: [[-2147483646,-2147483645],[2147483646,2147483647]]
// Apparently a new test case has been added recently. If you cannot pass this one, then it is because the result of subtraction is too large and thus the overflow is encountered. So don't use a-b to compare when sorting. Use Integer.compare(a,b) instead!!!
// another interesting greedy way is to sort by end, and always shoot as right as possible.
// One point missing in the explanation is that there might be some balloon later in the array supposed to be shot already but still seating in the array. However, this can be taken care of by some later arrow.
// from (https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/solutions/93703/share-my-explained-greedy-solution/?envType=study-plan-v2&envId=top-interview-150)
class Solution {
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a,b)->(Integer.compare(a[0],b[0])));
        int count = 0;
        int[] cur = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > cur[1]) {
                count++;
                cur = points[i];
            } else {
                cur[0] = Math.max(cur[0], points[i][0]);
                cur[1] = Math.min(cur[1], points[i][1]);
            }
        }
        return count+1;
    }
}