class Solution {
    /*Initial thought
    similar to a range problem. We want to find the max passenger numbers in overlapping ranges and compare it with capacity.
    use a map to track the passenger number changes at each time. if it's start time, map.get(time)+ curNum, if it's end time, map.get(time) - curNum.
    Sort map based on timestamp, the max of accumSum represents the max capacity needed.
    We can use a treeMap to automatically sort the key(timeStamp) for us.
    time O(nlogn)
    space O(n)
    
    */
    public boolean carPooling(int[][] trips, int capacity) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] trip: trips) {
            map.putIfAbsent(trip[1],0);
            map.put(trip[1], map.get(trip[1]) + trip[0]);
            map.putIfAbsent(trip[2],0);
            map.put(trip[2], map.get(trip[2]) - trip[0]);
        }
        
        int cumSum = 0;
        for (int num: map.values()){
            cumSum += num;
            if (cumSum > capacity) {return false;}
        }
        return true;
    }
}

// M2 in solution: another bucket sort way with time O(max(N,1001))
// https://leetcode.com/problems/car-pooling/solution/
/* Some discussions
I think the second approach might not be a good choice during a real interview because I rarely see interviewers give me those constraints that you see for every leetcode problem in the constraints section. You could ask, but they may not answer what you want to hear. It could be a good follow-up though if your interviewer says there may be billions of elements, but only 1001 stops.

The intuition behind solution 1 is quite useful in many places where ranges are involved. If you intend to mark some property for range [i,j], then put map[i] += property, map[j+1] -= property, instead of storing a value for each index between i,j.

https://leetcode.com/problems/maximum-sum-obtained-of-any-permutation/discuss/854206/JavaC%2B%2BPython-Sweep-Line


*/




