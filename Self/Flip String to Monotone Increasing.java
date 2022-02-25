/* Initial thought
DP way. can either consider forward or backward. If we consider forward way: dp[i] represents the result of string[i,n-1]. Then if dp[i] == 0, dp[i] = dp[i+1]; if dp[i] == 1. then we have two choices, either we change it to 0, and dp[i] = 1 + dp[i+1], or we have it remain 1, then all the 0's in string[i+1,n-1] needs to be changed to 1. Thus if dp[i] == 1, dp[i] = min(dp[i+1]+1, countZeros(i+1)).
In order to have O(n) time, we need to have an array storing the countZeros for each position(i) till end in advance. This will take O(n) time before hand. (actually this can be combined with the dp process)
And since dp[i] depends on dp[i+1], we need to start the dp from n-1.

time O(n) space O(n)
*/
class Solution {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int[] countZero = new int[n+1];
        int[] dp = new int[n+1];
        char[] chars = s.toCharArray();
        
        for (int i = n-1; i >= 0; i--) {
            if (chars[i] == '1') {
                countZero[i] = countZero[i+1];
                dp[i] = Math.min(dp[i+1] +1, countZero[i+1]);
            } else {
                countZero[i] = countZero[i+1] + 1;
                dp[i] = dp[i+1];
            }
        }
        return dp[0];
    }
}

/* improved thought
The above DP way can be optimized to O(1) space, since dp[i] only depends on dp[i+1], and count[i] only depends on count[i+1].

time O(n) space O(1)
*/
class Solution {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int countZero = 0;
        int dp = 0;
        
        for (int i = n-1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '1') {
                dp= Math.min(dp+1, countZero);
            } else {
                countZero++;;
            }
        }
        return dp;
    }
}


// another preSum way
// https://leetcode.com/problems/flip-string-to-monotone-increasing/discuss/183851/C%2B%2BJava-4-lines-O(n)-or-O(1)-DP
// Basically, try to convert all 1 to 0 before and all 0 to 1 after current index respectively. Store the minimum as result


