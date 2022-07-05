/*Thought
corner case: armor = 0, then return sum+1;
DP problem. Each position have two choices: use armor or not use it.
dp[i][0] = min(dp[i-1][0]+damage[i], dp[i-1][1]+ max(0,damage[i]-armor));
dp[i][1] = dp[i-1][1] + damage[i];

time O(n) space O (n), can be optimized to O(1) space
*/
// O(n) space way
class Solution {
    public long minimumHealth(int[] damage, int armor) {
        long[][] dp = new long[damage.length+1][2];
        for (int i = 1; i < damage.length+1; i++) {
            dp[i][0] = Math.min(dp[i-1][0] + damage[i-1], dp[i-1][1] + Math.max(0, damage[i-1]-armor));
            dp[i][1] = dp[i-1][1] + damage[i-1];
        }
        
        return dp[damage.length][0] + 1;
    }
}
// O(1) space way
class Solution {
    public long minimumHealth(int[] damage, int armor) {
        long dp0 = 0;
        long dp1 = 0;
        for (int d: damage) {
            dp0 = Math.min(dp0 + d, dp1 + Math.max(0, d-armor));
            dp1 += d;
        }
        
        return dp0 + 1;
    }
}


// Another greedy math way: simply use the armor on the level with largest damage
// https://leetcode.com/problems/minimum-health-to-beat-game/discuss/1881706/Simple-Java-Solution-O(n)-time-O(1)-space-with-explanation
// time also O(n)
// greedy math way
class Solution {
    public long minimumHealth(int[] damage, int armor) {
        long res = 1;
        long maxdamage = 0;
        for (int d: damage) {
            res += d;
            maxdamage = Math.max(maxdamage, d);
        }
        
        return res - Math.min(maxdamage,armor);
    }
}