class Solution {
	// dp way: knapsack with multi-dim weight constraints
	// time O(len of strs * m * n) space O(m * n)
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String s: strs) {
        	int zeros = 0, ones = 0;
        	for (char c : s.toCharArray()) {
        		if (c == '0') {
        			zeros++;
        		} else {
        			ones++;
        		}
        	}

        	for (int curM = m; curM >= zeros; curM--) {
        		for (int curN = n; curN >= ones; curN--) {
        			dp[curM][curN] = Math.max(dp[curM][curN], dp[curM - zeros][curN - ones] + 1);
        		}
        	}
        }
        return dp[m][n];
    }
}