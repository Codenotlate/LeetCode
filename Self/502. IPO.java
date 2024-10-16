// 2024.10.10
// for w, check all projects with capital <= w, choose the one with largest profit. Similarly, do this process after update w with new capital.
// each project can be selected once, and at most choose k projects.
// sort by c (min) with a pq. Then pop out all c <= w into another pq sort by p (max). pop out the max p, k-=1, w+=max_p. Continue this until either k ==0 or both pq are empty.
// time O((n+k)logn) space O(n)
//-------------
// Note: another case to consider is when mincheap is not empty but maxpheap becomes empty bcs there's no satisfied projects.
// same idea as optimal solution
class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<int[]> mincheap = new PriorityQueue<>((a,b)->(a[0]-b[0]));
        PriorityQueue<int[]> maxpheap = new PriorityQueue<>((a,b)->(b[1]-a[1]));
        for (int i = 0; i < profits.length; i++) {
            mincheap.add(new int[]{capital[i], profits[i]});
        }c
        while (k > 0) {
            while (!mincheap.isEmpty() && mincheap.peek()[0] <= w) {
                maxpheap.add(mincheap.poll());
            }
            if (maxpheap.isEmpty()) {break;}
            int[] maxp = maxpheap.poll();
            k -= 1;
            w += maxp[1];
            if (k==0 || (maxpheap.isEmpty()&& mincheap.isEmpty())) {break;}
        }
        return w;

    }
}