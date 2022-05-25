/*Thought
The dist between two times is at most 12h which is 720min. This problem is like find the smallest dist between a set of points.
M1: sort the array normally based on HH then MM, and just get min dist between two adjacent times, including the last one with the first one. Time O(nlogn)
M2: bucket sort, since only 24HH and 60MM, the space is O(24*60). Then we can do O(n) time.
*/
// M2: similar as optimized solution in discussion
class Solution {
    public int findMinDifference(List<String> timePoints) {
        int[] buckets = new int[24 * 60];
        for (String t: timePoints) {
            String[] strs = t.split(":");
            int idx = Integer.valueOf(strs[0]) * 60 + Integer.valueOf(strs[1]);
            if (buckets[idx] == 1) {return 0;} // don't forget this line
            buckets[idx] = 1;
        }
        int mindiff = Integer.MAX_VALUE;
        int first = -1;
        int prev = -1;
        int last = -1;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != 0) {
                last = i;
                if (first == -1) {first = i;}
                if (prev != -1) {mindiff = Math.min(mindiff, Math.min(i-prev, 1440-(i-prev)));}
                prev = i;
            }
        }
        return Math.min(mindiff, Math.min(last-first, 1440-(last-first)));
        
    }
}