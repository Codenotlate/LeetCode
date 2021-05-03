class Solution {
	// dp: dp[i] represents # of ways encoding substring s[0:i] inclusive
	// time O(n) space O(n)
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;
        for (int i = 2; i <= n; i++) {
        	int oneDigit = Integer.valueOf(s.substring(i - 1, i));
        	int twoDigit = Integer.valueOf(s.substring(i - 2, i));
        	if (oneDigit <= 9 && oneDigit >= 1) {
        		dp[i] += dp[i - 1];
        	}
        	if (twoDigit <= 26 && twoDigit >= 10) {
        		dp[i] += dp[i - 2];
        	}
        }
        return dp[n];
    }
}