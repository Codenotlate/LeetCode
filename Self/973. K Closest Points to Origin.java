class Solution {
    /*Initial Thought
    sort the array based on distance, and find the first k number
    time O(nlogn) space O(1) if don't count result space and sort is inplace
    Another way is to use max PQ with size k
    time O(nlogk)  space O(k)
    */
    public int[][] kClosest(int[][] points, int k) {
        Arrays.sort(points, (p1, p2) ->(p1[0]*p1[0] + p1[1]*p1[1] - p2[0]*p2[0] - p2[1] *p2[1]));
        int[][] res = new int[k][2];
        for (int i = 0; i< k; i++) {
            res[i] = points[i];
        }
        return res;
    }
}

class Solution {
    // M2: maxheap way
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxheap = new PriorityQueue<>((p1,p2) -> (p2[0]*p2[0] + p2[1]*p2[1]-p1[0]*p1[0] - p1[1]*p1[1]));
        int size = 0;
        for (int[] p: points) {
            maxheap.add(p);
            size++;
            if (size > k) {maxheap.poll();}
        }
        int[][] res = new int[k][2];
        while(!maxheap.isEmpty()) {res[k-1] = maxheap.poll(); k--;}
        return res;
    }
}

// another two O(N) way in solution 3 and 4
// https://leetcode.com/problems/k-closest-points-to-origin/solution/