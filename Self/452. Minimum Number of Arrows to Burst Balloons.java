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