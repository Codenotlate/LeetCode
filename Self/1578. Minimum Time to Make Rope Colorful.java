/*Initial thought
We maintain a currenttime(init as neededTime[0]). When we encounter consecutive same colors, colors[i] == colors[i+1], we compare currenttime & neededTime[i+1]. add the smaller one into the result. And update currenttime to be the larger one between them. Move i to next position. 
If colors[i] != colors[i+1], currenttime = neededTime[i+1].

time O(n) - one pass
space O(1)


*/
class Solution {
    public int minCost(String colors, int[] neededTime) {
        int curTime = neededTime[0];
        int res = 0;
        for (int i = 0; i < neededTime.length - 1; i++) {
            if(colors.charAt(i) == colors.charAt(i+1)) {
                res += Math.min(curTime, neededTime[i+1]);
                curTime = Math.max(curTime, neededTime[i+1]);
            } else {
                curTime = neededTime[i+1];
            }
        }
        return res;
    }
}

// also O(n) way, but different idea - For each group of continuous same characters, we need cost = sum_cost(group) - max_cost(group)
// https://leetcode.com/problems/minimum-time-to-make-rope-colorful/discuss/831588/JavaC%2B%2BPython-Straight-Forward