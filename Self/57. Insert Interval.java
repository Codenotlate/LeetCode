// Phase3 self
// time O(n), space O(n) for answers
/*
basic idea is to deal with intervals in three type
1) non overlap and in front of new
2) non overlap and behind new
3) overlap with new.
*/

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> resList = new LinkedList<>();

        for (int[] interval: intervals) {
        	if (interval[0] > newInterval[1]) {
        		resList.add(newInterval);
        		newInterval = interval;
        	} else if (interval[1] < newInterval[0]) {
        		resList.add(interval);
        	} else {
        		newInterval[0] = Math.min(newInterval[0], interval[0]);
        		newInterval[1] = Math.max(newInterval[1], interval[1]);
        	}
        }

        resList.add(newInterval);

        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {
        	res[i] = resList.get(i);
        }
        return res;
    }
}



// Phase3 self: not recommended, since the time is still O(n) for worst case, yet the implementation is complicated. Above M1 is more preferred for this question.
class Solution {
    // first use binary search to find the first left interval with start larger than newstart, and use binary search again to find the first right interval with start larger than newend. compare the end of [right-1] interval with newend, and compare the end of [left-1] interval with newstart.
    // Time: Best - O(logN), Worst - O(N)
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = findPos(intervals, newInterval[0]);
        int right = findPos(intervals, newInterval[1]);
        
        // if (left == -1) {
        //     int[][] resarr = new int[intervals.length+1][2];
        //     for (int i = 0; i<intervals.length;i++) {resarr[i] = intervals[i];}
        //     resarr[intervals.length] = newInterval;
        //     return resarr;
        // }
        
        List<int[]> res= new LinkedList<>();
        for (int i = 0; i < left - 1; i++) {
            res.add(intervals[i]);
        }
        
        int newstart = 0;
        int newend = 0;
        if (left == 0 || intervals[left-1][1] < newInterval[0]) {
            if (left>0) {res.add(intervals[left-1]);}
            newstart = newInterval[0];
        } else {
            newstart = intervals[left-1][0];
        }
        if (right >0 && intervals[right-1][1] >= newInterval[1]) {
            newend = intervals[right-1][1];
        } else {
            newend = newInterval[1];
        }
        
        res.add(new int[]{newstart, newend});
        for (int i = right; i < intervals.length; i++) {
            res.add(intervals[i]);
        }
        
        
        // convert list back to array
        int[][] resarr = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            resarr[i] = res.get(i);
        }
        return resarr;
    }
    
    // return the first pos in intervals that has start > t
    private int findPos(int[][]intervals, int t) {
        int low = 0;
        int high = intervals.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (intervals[mid][0] <= t) {low = mid + 1;}
            else {high = mid;}
        }
        
        return low;
    }
       
}

