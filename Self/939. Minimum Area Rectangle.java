/* From solution
since the rect should be parallel to the axes, an area of a rect can actually be determined by the positions of its diag. Thus for each p, we can pair it with other p in points to be the lower left and upper right corner of a rect, and check if other two points of the rect exist in the points set, if it has, then update the res = min(res, current area).
Note since int[] can't be compared equally, we can use a set of pair, or hashmap with x as key and y set as value.

time O(n^2)
space O(n)

*/
class Solution {
    public int minAreaRect(int[][] points) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        for(int[] p: points) {
            set.add(new Pair(p[0], p[1]));
        }
        int n = points.length;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int[] p1 = points[i];
                int[] p2 = points[j];
                if (p1[1] == p2[1] || p1[0] == p2[0]) {continue;}
                Pair p3 = new Pair(p1[0],p2[1]);
                Pair p4 = new Pair(p2[0], p1[1]);
                if(set.contains(p3) && set.contains(p4)) {
                    res = Math.min(res, Math.abs(p1[0]-p2[0]) * Math.abs(p1[1] - p2[1]));
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}