/*Thought
The greedy way is to always use ladders for biggest diff until now. Thus we need to maintain a sorted diff arrays. Two ways to go: minheap way or maxheap way.
M1: minheap way. We maintain a minheap with size threshold as ladders. As we pass through the diff, if diff >0, we add it to the minheap, and if the heap size is larger than ladders, we pop out the min diff until now, and that needs to be subtracted from bricks. 
time O(nlog(ladders)) = O(nlogn) space O(n)
M2: maxheap way.Similarly we maintain a maxheap to store diffs handled by bricks. As we pass through the diff, if diff > 0, we subtract it from bricks. And if brick goes negative meaning bricks are not enough, we pop out the max diff from the heap and use one ladder for it. Thus ladder--, brick += maxdiff. We do this until ladder == 0 and brick < 0.
time O(nlogn) space O(n)
*/

// M1: minheap way
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int i = 1;
        while (i < heights.length) {
            int curDiff = heights[i] - heights[i-1];
            if (curDiff <= 0) {i++; continue;}
            
            minHeap.add(curDiff);
            if (minHeap.size() > ladders) {
                int popDiff = minHeap.poll();
                bricks -= popDiff;
                if (bricks < 0) {break;}
            }
            i++;
        }
        return i - 1;
    }
}



// M2: maxheap way
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> (b-a));
        int i = 1;
        while (i < heights.length) {
            int curDiff = heights[i] - heights[i-1];
            if (curDiff <= 0) {i++; continue;}
            
            maxHeap.add(curDiff);
            bricks -= curDiff;
            if (bricks < 0) {
                if (ladders <= 0) {break;}
                int popDiff = maxHeap.poll();
                bricks += popDiff;
                ladders--;
            }
            i++;
        }
        return i - 1;
    }
}



// check another two BS ways in solution method3 & 4