/** 20240326
The key here is that the pq always only contains one element from each list, making sure it's the minimum valid range. Also since everytime we poll, we want to poll the smallest number, we need a pq for that. (It doesn't make sense to poll out non-smallest element as it won't help smaller the range)
Time O(all numbers count * log(k)) space O(k)

-------------------------------
learning notes:
=== Naive way: get every pair from all the numbers and check if they are valid as range. 
Time O(n^3) space O(1)
=== M1: optimized from naive way, instead of checking all numbers to get count, we use binary search on each list.
Time O(n^2 * log(average list size)) space O(1)
=== M2: non-optimized but similar to the pq way. We still look at one element from each list at a time, but without using a pq, the time to find the smallest one among all lists are O(k). Thus time O(n*k)
--------------------------------
The original way I come up with is similiar to this one: sorted in one list and then do sliding window.
https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/solutions/334343/java-sliding-window-solution/

 */

class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(((int[] a,int[] b)-> (nums.get(a[0]).get(a[1])-nums.get(b[0]).get(b[1]))));
        int endMax = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size();i++) {
            pq.add(new int[]{i,0});
            endMax = Math.max(endMax, nums.get(i).get(0));
        }
        int[] bestRange = new int[]{0, Integer.MAX_VALUE};
        while(!pq.isEmpty()) {
            int[] curIndex = pq.poll();
            int idx1 = curIndex[0];
            int idx2 = curIndex[1];
            if (bestRange[1]-bestRange[0] > endMax - nums.get(idx1).get(idx2)){
                bestRange[0] = nums.get(idx1).get(idx2);
                bestRange[1] = endMax;
            }
            if(curIndex[1]+1 >= nums.get(curIndex[0]).size()) {break;}
            pq.add(new int[]{curIndex[0], curIndex[1]+1});
            endMax = Math.max(endMax, nums.get(curIndex[0]).get(curIndex[1]+1));
        }
        return bestRange;

    }
}