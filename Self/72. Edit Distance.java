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



// phase3 self
class Solution {
    public int minDistance(String word1, String word2) {
        // M1 dfs + memo -  Top-down
        // time O(l1 * l2) space O(l1 * l2);
        // memo[p1][p2] represents the result of w1[p1, l1] and w2[p2, l2].
        int l1 = word1.length();
        int l2 = word2.length();
        int[][] memo = new int[l1][l2];
        for (int[] row: memo) {
            Arrays.fill(row, -1);
        }
        return dfs(word1, word2, 0, 0, l1, l2, memo);
        // return memo[0][0]; this can't deal with ("", "")
    }
    
    private int dfs(String w1, String w2, int p1, int p2, int l1, int l2, int[][] memo) {
        if (p1 == l1 || p2 == l2) {
            if (p2 != l2) {return l2 - p2;}
            return l1 - p1;
        }
        
        if (memo[p1][p2] != -1) {return memo[p1][p2];}
        int r1 = dfs(w1, w2, p1, p2+1, l1, l2, memo) + 1;
        int r2 = dfs(w1, w2, p1 + 1, p2, l1, l2, memo) + 1;
        int r3 = dfs(w1, w2, p1 + 1, p2+1, l1, l2, memo);
        if (w1.charAt(p1) != w2.charAt(p2)) {r3 += 1;}
        memo[p1][p2] = Math.min(Math.min(r1, r2), r3);
        return memo[p1][p2];
    }
}




class Solution {
    public int minDistance(String word1, String word2) {
        // M2: bottom-up: dp[p1][p2] represents the result of w1[0, p1] and w2[0, p2];
        // can campress status to have O(l2) space
        int l1 = word1.length();
        int l2 = word2.length();
        int[] dp = new int[l2 + 1];
        // init dp[0][p2] = p2
        for (int p2 = 0; p2 <= l2; p2++) {
            dp[p2] = p2;
        }
        int prev;
        for (int p1 = 1; p1 <= l1; p1++) {
            dp[0] = p1;
            prev = p1 - 1;
            for (int p2 = 1; p2 <= l2; p2++) {
                int temp = dp[p2];
                int isreplace = word1.charAt(p1 - 1) == word2.charAt(p2 - 1) ? 0 : 1;
                dp[p2] = Math.min(Math.min(dp[p2 - 1], dp[p2]) + 1, prev + isreplace);
                prev = temp;
            }
        }
        return dp[l2];
    }
}














