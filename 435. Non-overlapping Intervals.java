class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        // special case
        if (intervals.length == 0) {return 0;}

        // sort intervals using their end
        //Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        Arrays.sort(intervals, new Comparator<int[]>() {
        	@Override
        	public int compare(int[] o1, int[] o2) {
        		if (o1[1] < o2[1]) {return -1;}
        		else if (o1[1] > o2[1]) {return 1;}
        		else {return 0;}
        	}
        });


        int end = intervals[0][1];
        int count = 1;

        for (int i = 1; i < intervals.length; i++) {
        	// determine whether overlap
        	if (intervals[i][0] >= end) {
        		count++;
        		end = intervals[i][1];
        	}
        }
        return intervals.length - count;
    }
}