class Solution {
    /* from hint
    we want to find a mink in range[1, maxp] that ceil(p[i] / k) sum up to h. We can do this by using binary search between [1,maxp]. if the sumup hour > h, meaning current speed is too small, start = mid+1; if sumup hour <= h, this speed is a potential answer, and we can try a slower speed, thus end = mid.
    time O(n * log(maxp))
    space O(1)
    */
    public int minEatingSpeed(int[] piles, int h) {
        int maxp = 0;
        for (int p: piles) {maxp = Math.max(p, maxp);}
        // binary search
        int start = 1;
        int end = maxp;
        while (start < end) {
            int mid = start + (end-start) / 2;
            int hours = 0;
            for (int p: piles) {
                // don't forget to convert to double here
                hours += Math.ceil((double)p / mid);
            }
            if (hours > h) {start = mid + 1;}
            else {end = mid;}
        }
        return start;
    }
}




// TLE way
// This way is too slow. A similar way using pririrty queue but with further optimization: https://leetcode.com/problems/koko-eating-bananas/solution/1226924
class Solution {
    /* Initial thought
    [3,6,7,11], h=8
    [3,6,7,6], h=7
    [3,6,4,6], h=6
    [3,3,4,6], h=5
    [3,3,4,3], h=4 -> base case, return max(number in piles)
    At each round, we need to find the largest number in that round of array, and convert to max / 2 +1. Use maxheap.
    time O((h-n)*logn)  space O(n)
    */
    public int minEatingSpeed(int[] piles, int h) {
        if(piles.length == 1) {return piles[0] / h + 1;}
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b)->(b-a));
        int n = piles.length;
        for(int p:piles) {maxHeap.add(p);}
        while(h-- > n) {
            maxHeap.add(maxHeap.poll() /2 + 1);
        }
        return maxHeap.peek();
    }
}