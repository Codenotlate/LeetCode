class Solution {
	// M1: recursion + memo
	// time O(s1.len * s2.len) space O(s1.len * s2.len)
    public int minDistance(String word1, String word2) {
        int[][] memo = new int[word1.length()][word2.length()];
        for (int[] m : memo) {
        	Arrays.fill(m, -1);
        }
        return minDistanceHelper(word1, 0, word2, 0, memo);
    }

    private int minDistanceHelper(String s1, int i, String s2, int j, int[][] memo) {
    	// base case
    	if (i == s1.length() || j == s2.length()) {
    		return s1.length() - i + s2.length() - j;
    	}
    	if (memo[i][j] != -1) {return memo[i][j];}

    	int res = 0;
    	if (s1.charAt(i) == s2.charAt(j)) {
    		res = minDistanceHelper(s1, i + 1, s2, j + 1, memo);
    	} else {
    		res = Math.min(1 + minDistanceHelper(s1, i + 1, s2, j + 1, memo),
    			Math.min(1 + minDistanceHelper(s1, i + 1, s2, j, memo),
    				1 + minDistanceHelper(s1, i, s2, j + 1, memo)));
    	}
    	memo[i][j] = res;
    	return res;
    }
}



class Solution {
	// M2: bottom-up dp way. dp[i][j] represents # of operations convert s1[:i] to s2[:j] (inclusive)
    public int minDistance(String word1, String word2) {
        int[] dp = new int[word2.length() + 1];
        // initialize dp[-1][j] = j;
        for (int j = 0; j <= word2.length(); j++) {
        	dp[j] = j;
        }
        for (int i = 1; i <= word1.length(); i++) {
        	// pre_ij store dp[i-1][j-1], thus dp[i-1][-1] = i - 1
        	int pre_ij = i - 1;
        	// initialize dp[i][-1] = i;
            dp[0] = i;
        	for (int j = 1; j <= word2.length(); j++) {
        		int temp = dp[j]; // this is dp[i-1][j] for this round, and dp[i-1][j-1] for next j
        		if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
        			dp[j] = pre_ij;
        		} else {
        			dp[j] = Math.min(pre_ij + 1, Math.min(dp[j] + 1, dp[j - 1] + 1));
        		}
        		pre_ij = temp;
        	}
        	
        }
        return dp[word2.length()];
    }
}



















