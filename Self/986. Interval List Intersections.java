/*Initial thought
Just merge naturally, each time we have one interval from firstlist and one from secondlist. if l1[start] > l2[end], move l2 to the next one in secondlist. if l1[end] < l2[start], move l1 to next. else if l1[end] < l2[end], add [max(l1[start],l2[start]), l1[end]] to res, and move l1 to next. else add [max(l1[start],l2[start]), l2[end]] to res, and move l2 to next. until one list reaches the end.
time O(m + n) space O(m+n) if exclude result space

[[0,2],[5,10],[13,23],[24,25]]
[[1,5],[8,12],[15,25],[26,27]]
res= [[1,2],[5,5],[8,10],[15,23],[24,25]]
*/
class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> resList = new LinkedList<>();
        int m = firstList.length;
        int n = secondList.length;
        if(m == 0 || n== 0) {return new int[0][0];}
        int i1 = 0;
        int i2 = 0;
        while (i1 < m && i2 < n) {
            int[] l1 = firstList[i1];
            int[] l2 = secondList[i2];
            if (l1[0] > l2[1]) {i2++;}
            else if (l1[1] < l2[0]) {i1++;}
            else if (l1[1] < l2[1]) {
                resList.add(new int[]{Math.max(l1[0], l2[0]), l1[1]});
                i1++;
            } else {
                resList.add(new int[]{Math.max(l1[0],l2[0]), l2[1]});
                i2++;
            }
        }
        
        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {res[i] = resList.get(i);}
        return res;
    }
}




// from solution: same idea as above but in a more elegant way
class Solution {
  public int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> ans = new ArrayList();
    int i = 0, j = 0;

    while (i < A.length && j < B.length) {
      // Let's check if A[i] intersects B[j].
      // lo - the startpoint of the intersection
      // hi - the endpoint of the intersection
      int lo = Math.max(A[i][0], B[j][0]);
      int hi = Math.min(A[i][1], B[j][1]);
      if (lo <= hi)
        ans.add(new int[]{lo, hi});

      // Remove the interval with the smallest endpoint
      if (A[i][1] < B[j][1])
        i++;
      else
        j++;
    }

    return ans.toArray(new int[ans.size()][]);
  }
}






// Review
/*Thought
Think about relationship between two intervals. Case1: disjoint(f[start] > s[end] or f[end] < s[start]), move whoever smaller to the next itv in its array. Case2: They overlap, then add to result [max(f[start], s[start]), min(f[end], s[end])]. then move whoever has smaller end to the next one. Do it till one array reaches the end.
time O(l1 + L2)
space O(k) k is the number of result pairs

e.g.
firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
res:[1,2],[5,5],[8,10],[15,23][24,24][25,25]

*/
class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> resList = new LinkedList<>();
        int p1 = 0;
        int p2 = 0;
        while (p1 < firstList.length && p2 < secondList.length) {
            int[] first = firstList[p1];
            int[] second = secondList[p2];
            if (first[0] > second[1] || first[1] < second[0]) {
                if (first[0] > second[1]) {p2++;}
                else {p1++;}
            } else {
                resList.add(new int[]{Math.max(first[0], second[0]), Math.min(first[1], second[1])});
                if (first[1] < second[1]) {p1++;}
                else {p2++;}
            }
        }
        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {res[i] = resList.get(i);}
        return res;
    }
}
// further optimize the code
class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> resList = new LinkedList<>();
        int p1 = 0;
        int p2 = 0;
        while (p1 < firstList.length && p2 < secondList.length) {
            int[] first = firstList[p1];
            int[] second = secondList[p2];
            int low = Math.max(first[0], second[0]);
            int high = Math.min(first[1], second[1]);
            if (low <= high) {resList.add(new int[]{low, high});}
            if(first[1] > second[1]) {p2++;}
            else {p1++;}
        }
        return resList.toArray(new int[resList.size()][]);
    }
}