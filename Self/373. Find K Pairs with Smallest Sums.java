class Solution {
    // Phase3 from solution
    // view it as a 2d matrix, then it's a similar problem as 378: merge n sorted arrays using minHeap
    // time O(klogk) space O(k)
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
/*
I was asked this question in a top company's onsite interview. I presented this approach specifically the index pair approach like @EddieCarrillo 's and the interviewer didn't buy it. He thought this will not work as when you poll index pair [i, j] out, the next could be [i + 1, j] or [i, j + 1], why did you only add [i, j + 1] to the queue? I tried to explain it in different ways, like thinking it as multiple linked lists, showing an example, and so on. He didn't agree and still considered this as a wrong approach.

I came back and kept asking myself, how could I have explained this better? Here is they way I would have done:

When index pair [i, j] is polled out from the Priority Queue, it's guaranteed that there is a pair [i + 1, x] is in the queue. (except its the last pair which we don't need next pair)
Why is it? because we added all the smallest possible pairs [0, 0], [1, 0].....[Min(k, n), 0].
Now for x, there are three cases:

if x < j, that means [i +1, x] is smaller than [i + 1, j]. Thus, [i + 1, j] won't be a candidate. And it will be reached later by increasing x in [i + 1, x].
if x > j, that means [i + 1, x] is larger than [i + 1, j]. Thus, [i + 1, j] is already in the result, we should not consider it again.
if x = j, that's the same pair. We should not add it to queue.
*/


// Review: still from solution
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res  = new LinkedList<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a,b) -> (nums1[a[0]]+nums2[a[1]]-nums1[b[0]]-nums2[b[1]]));
        // add the first row to the heap first
        for (int j = 0; j < nums2.length; j++) {
            minHeap.add(new int[]{0,j});
        }
        
        while (res.size() < k && !minHeap.isEmpty()) {
            int[] cur = minHeap.poll();
            res.add(Arrays.asList(nums1[cur[0]], nums2[cur[1]]));
            if (cur[0] < nums1.length - 1) {minHeap.add(new int[]{cur[0]+1, cur[1]});}
        }
        return res;
    }
}







// Review - same as above idea
/*Thought
Most naive way: get all l1 * l2 pairs and put into a size k heap. time O(l1l2logk) space O(min(k, l1*l2))
use a heap with size num1.len. View it as below matrix, and each time the top cell of each column will be put into the heap, we pop out the smallest one, and put another cell following the popped cell on the same column into the heap again. Until we popped out k elements.
1+2 7+2 11+2
1+4 7+4 11+4
1+6 7+6 11+6

Time O(min(l1*l2, k)*log(min(m,k))
*/
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((p1, p2) -> (nums1[p1[0]] + nums2[p1[1]]) - (nums1[p2[0]]+nums2[p2[1]]));
        for (int i = 0; i < nums1.length && i < k; i++) {
            pq.add(new int[]{i, 0});
        }
        
        List<List<Integer>> res = new LinkedList<>();
        while (!pq.isEmpty() && res.size() != k) {
            int[] cur = pq.poll();
            res.add(Arrays.asList(nums1[cur[0]], nums2[cur[1]]));
            if(cur[1] < nums2.length - 1) {pq.add(new int[]{cur[0], cur[1]+1});}
        }
        return res;
    }
}