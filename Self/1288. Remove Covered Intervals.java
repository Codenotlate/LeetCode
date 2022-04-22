/*Initial thought
sort the array based on start position O(nlogn). All earlier uncovered interval will be stored as map, end-> start. And we keep check of the current maxEnd. Then for each current interval, we compare curEnd with maxEnd, if curEnd > maxEnd, the current interval is an uncovered one, we add curEnd->curStart to the map, and update maxEnd to curEnd.
Else if curEnd <= maxEnd, this is a covered one.
return the size of the map.
Actually we can save the space by just recording the earlier uncovered pair with maxEnd and the number of all earlier uncovered pairs.
Also note, for sort intervals with same starting position, we want to put the longer one first based on our comparing logic.

time O(nlogn)
space O(space for sort) (O(logn) in Java Arrays.sort())
*/
class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2)->(i1[0] == i2[0]? i2[1]-i1[1] : i1[0] - i2[0]));
        int maxEnd = intervals[0][1];
        int count = 1;
        for (int i = 1; i < intervals.length; i++) {
            if(intervals[i][1] > maxEnd) {
                maxEnd = intervals[i][1];
                count++;
            } 
        }
        return count;
        
    }
}



// Review - time O(nlogn) space O(logn)
class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2)->(i1[0]==i2[0]? i2[1] - i1[1] : i1[0] - i2[0]));
        int preend = -1;
        int count = 0;
        for (int[] curint: intervals){
            int curend = curint[1];
            if(curend > preend) {
                preend = curend;
                count++;
            }
        }
        return count;
    }
}