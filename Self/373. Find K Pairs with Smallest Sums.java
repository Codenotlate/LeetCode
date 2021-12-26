class Solution {
    // Phase3 from solution
    // view it as a 2d matrix, then it's a similar problem as 378: merge n sorted arrays using minHeap
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((p1, p2) -> nums1[p1[0]] + nums2[p1[1]] - nums1[p2[0]] - nums2[p2[1]]);
        List<List<Integer>> res = new LinkedList<>();
        // add the first row of matrix into the pq, representing the min of each n sorted arrays
        for (int j = 0; j < nums2.length; j++) {
            pq.add(new int[]{0, j});
        }
        
        // poll out until get k pairs
        while (res.size() != k && !pq.isEmpty()) {
            int[] polled = pq.poll();
            // poll out one, then add to res, and add the next element in that specific sorted array(column) into the pq
            List<Integer> pairList = new LinkedList<>();
            pairList.add(nums1[polled[0]]);
            pairList.add(nums2[polled[1]]);
            res.add(pairList);
            if (polled[0] + 1 < nums1.length) {
                pq.add(new int[]{polled[0]+1, polled[1]});
            }
            
        }
        return res;
    }
}


// some interesting discussion in the comment below
// https://leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/84551/simple-Java-O(KlogK)-solution-with-explanation