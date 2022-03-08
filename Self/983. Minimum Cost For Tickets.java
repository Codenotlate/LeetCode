/* Initial thought
considering using dp. At each day, there are three choices. use dp[i] to represent min cost starting day at position i. For each curday in days, we need to find the first day pos in days that has days[pos] >= curday + 1/7/30. (using binary search, which will be O(log30) time, since the largest window size is 30) Then dp[i] = min(dp[pos_k]+costs[k]) where k  == 0, 1, 2
init: dp[n-1] = costs[0]. dp[-1] == 0.
time O(n*k*logn) = O(nlogn) since k = 3. And n represents the days.len
space O(n). note n can be optimized to 30 since the largest window size is 30.

Here we do backwards. We can also do forwards.

*/
class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        int[] dp = new int[days.length];
        for (int i = days.length - 1; i >= 0; i--) {
            int curday = days[i];
            int pos0 = search(days, i, curday + 1);
            int pos1 = search(days, i, curday + 7);
            int pos2 = search(days, i, curday + 30);
            int cost0 = pos0 < days.length ? dp[pos0] + costs[0]: costs[0];
            int cost1 = pos1 < days.length ? dp[pos1] + costs[1]: costs[1];
            int cost2 = pos2 < days.length ? dp[pos2] + costs[2]: costs[2];
            dp[i] = Math.min(cost0, Math.min(cost1, cost2));
        }
        return dp[0];       
    }
    
    
    // return the position where days[pos] is the first one >= target
    private int search(int[] days, int start, int target) {
        int end = days.length-1;
        if(target > days[end]) {return end+1;}
        while(start < end) {
            int mid = start + (end - start) / 2;
            if (days[mid] >= target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}


/* Additional note
The solution uses linear search instead of binary search. Because here days.length is at most 365, thus even with linear search, it's still O(1) for the search, but if days.length grows larger, binary search way would be much better.
"In approach 2, for every day, it takes up to 30 loop to find j1, j7, j30. so the time complexity is actually O(30 * N)
If we change the time to 100 years, with 10 year pass, it might not be a good idea to just loop to find the day.
a better way is using binary search to find j, and hence i think it's more accurate to say time complexity should be O(NlogN);"



Another optimization is that space can be O(30) instead of O(days.length). 
https://leetcode.com/problems/minimum-cost-for-tickets/discuss/226659/Two-DP-solutions-with-pictures



*/




















