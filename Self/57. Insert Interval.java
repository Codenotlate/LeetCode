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