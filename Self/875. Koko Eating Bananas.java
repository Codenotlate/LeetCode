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





// Review
/*Thought
the possible range for answer would be [sum/h,n*max/h + 1] or simply [1,max]. Do binary search on this range and for each target number, we check the hours needed for each element in piles. And decide which half of the range to be eliminated. 
time O(n* log(max)) space O(1)
*/

class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int max = 0;
        for (int p: piles) {max = Math.max(max, p);}
        int min = 1;
        while (min < max) {
            int mid = min + (max-min) /2;
            int hours = getHours(piles, mid);
            if (hours <= h) {max = mid;}
            else {min = mid + 1;}
        }
        return min;
    }
    
    private int getHours(int[] piles, int mid) {
        int hours = 0;
        for(int p: piles) {
            int num = p / mid;
            hours += p % mid == 0 ? num : num+1;
        }
        return hours;
    }
}




// 2024.10.15
// Similar as above way. The answer k ranges from [1, maxP]. Thus do binary search on this range, each check time O(n) time . Thus O(nlog(maxP)) time
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int maxP = piles[0];
        for (int p: piles) {maxP = Math.max(maxP, p);}
        if (h==piles.length) {return maxP;}
        int left = 1;
        int right = maxP;
        while (left < right) {
            int mid = left + (right-left)/2;
            if (timeNeed(mid, piles) <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int timeNeed(int k, int[] piles) {
        int time = 0;
        for (int p: piles) {
            time += p / k + (p%k==0?0:1);
        }
        return time;

    }
}