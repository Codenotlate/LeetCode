/*
This problem is essentialy the same as find the len of longest common subsequence in two strings
once we find the length of LCS, result of deletion is s1.length + s2.length - 2 * len of LCS
LCS refer to problem 1143


Note: can also do direct dp without to have length of LCS first.
*/

class Solution {
	// M1: top-down dp : recursion + memo
	// time O(s1.len * s2.len) space O(s1.len * s2.len)
    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] memo = new int[len1][len2];
        // intialize to -1
        for (int[] m: memo) {
        	Arrays.fill(m, -1);
        }
        int LLCS = longestCommonSubsequance(word1, 0, word2, 0, memo);
        return len1 + len2 - 2 * LLCS;
    }

    private int longestCommonSubsequance(String s1, int i, String s2, int j, int[][] memo) {
    	if (i == s1.length() || j == s2.length()) {return 0;}
    	if (memo[i][j] != -1) {return memo[i][j];}
    	int maxLen = 0;
    	if (s1.charAt(i) == s2.charAt(j)) {
    		maxLen = 1 + longestCommonSubsequance(s1, i + 1, s2, j + 1, memo);
    	} else {
    		maxLen = Math.max(longestCommonSubsequance(s1, i + 1, s2, j, memo),
    			longestCommonSubsequance(s1, i, s2, j + 1, memo));
    	}
    	memo[i][j] = maxLen;
    	return maxLen;
    }
}




class Solution {
	// M2: bottom-up dp, dp[i][j] represents len of LCS of s1[:i] and s2[:j] (inclusive)
	// time O(s1.len * s2.len) space O(min(s1.len, s2.len))
    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int LLCS = longestCommonSubsequance2(word1, word2);
        return len1 + len2 - 2 * LLCS;
    }

    private int longestCommonSubsequance2(String s1, String s2) {
    	// already default to be 0, no need to initialize
    	int[] dp = new int[s2.length() + 1];
    	
    	for (int i = 1; i <= s1.length(); i++) {
    		// Note pre_ij need to be reset to 0 for each new i!!!
    		int pre_ij = 0;
    		for (int j = 1; j <= s2.length(); j++) {
    			int temp = dp[j];
    			if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
    				dp[j] = pre_ij + 1;
    			} else {
    				dp[j] = Math.max(dp[j], dp[j - 1]);
    			}
    			pre_ij = temp;
    		}
    	}
    	return dp[s2.length()];
    }
}


























