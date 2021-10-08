// Phase3 self
// dp[i][j] here represents the length of edge of the maximum 1 square use [i][j] as its bottom right cell.
// time O(m * n) space O(m * n)
class Solution {
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[][] dp = new int[m][n];
        int maxlen = 0;
        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0] - '0';
            if (dp[i][0] == 1) {maxlen = 1;}
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j] - '0';
            if (dp[0][j] == 1) {maxlen = 1;}
        }
        
        
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == '0') {dp[i][j] = 0; continue;}
                dp[i][j] = Math.min(Math.min(dp[i-1][j-1], dp[i][j-1]), dp[i-1][j]) + 1;
                maxlen = Math.max(maxlen, dp[i][j]);
            }
        }
        return maxlen * maxlen;
    }
}



// M2: optimized into space O(n)
class Solution {
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[] dp = new int[n];
        int maxlen = 0;
        for (int j = 0; j < n; j++) {
            dp[j] = matrix[0][j] - '0';
            if (dp[j] == 1) {maxlen = 1;}
        }
        
        int dp_i1j1;  
        for (int i = 1; i < m; i++) {
            dp_i1j1 = dp[0];
            // remebr to update maxlen after set dp[0], otherwise won't work for [["0"],["1"]]
            dp[0] = matrix[i][0] - '0';
            maxlen = Math.max(maxlen, dp[0]);
            for (int j = 1; j < n; j++) {
                int temp = dp[j];
                if (matrix[i][j] == '0') {
                    dp[j] = 0;
                } else {
                    dp[j] = Math.min(Math.min(dp_i1j1, dp[j-1]), dp[j]) + 1;
                    maxlen = Math.max(maxlen, dp[j]);
                }
                
                dp_i1j1 = temp;
            }
        }
        return maxlen * maxlen;
    }
}