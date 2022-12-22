/*Thoughts
solve this in a dp way. Have dp[i] representing the min del number to have s[0:i] balanced.
Then at each pos of s, there are two cases:
case 1: s[i] = 'a', in this case s[0:i-1] will be in type a...ab...b (where countb can be zero). And at current pos, we have two options, either remove the current a, i.e. dp[i] = dp[i-1] + 1; or we keep the current 'a' but need to remove all previous b. i.e. dp[i] = countb. Thus dp[i] = Math.min(dp[i-1] + 1, countb);
case 2: s[i] = 'b', in this case as long as s[0:i-1] is balanced, we can append this 'b' behind, thus dp[i] = dp[i-1]
in the end, we return dp[len-1].

time O(n) space O(n) and with optimized way O(1)

 */
// O(n) space way
class Solution {
    public int minimumDeletions(String s) {
        int[] dp = new int[s.length()];
        int bcount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                dp[i] = i == 0? 0 : Math.min(dp[i-1] + 1, bcount);
            } else {
                dp[i] = i== 0? 0: dp[i-1];
                bcount++;
            }
        }
        return dp[s.length() -1];
    }
}

// O(1) space way
class Solution {
    public int minimumDeletions(String s) {
        int dp = 0;
        int bcount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                dp = Math.min(dp + 1, bcount);
            } else {
                bcount++;
            }
        }
        return dp;
    }
}