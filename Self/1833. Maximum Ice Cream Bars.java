/*Thoughts
In greedy way, we want to start buying from the lowest price ones to the higher ones.
Meaning we need to sort the cost array first, since it mentioned to solve it using counting sort, we will go this way.
First find the min and max of the array. If coins < min, directly return 0.
Then setup the counting array with len = max-min+1, the position in counting array will be num - min.
After the sorting, we iterate the counting array from left to right. If coins / (idx + min) >= count[idx], then res += count[idx], coins - (idx + min) * count[idx]; Otherwise, res += coins / (idx + min) and return res directly.

Time O(n) space O(max-min)
*/


class Solution {
    public int maxIceCream(int[] costs, int coins) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int c: costs) {
            min = Math.min(c, min);
            max = Math.max(c, max);
        }
        int[] counts = new int[max-min+1];
        for (int c: costs) {
            counts[c - min]++;
        }
        int res = 0;
        int idx = 0;
        while(coins > 0 && idx < counts.length) {
            int removeCount = Math.min(coins / (idx + min), counts[idx]);
            res += removeCount;
            coins -= removeCount * (idx + min);
            idx++;
        }
        return res;
    }
}