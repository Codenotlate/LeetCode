// Phase3 self - similar to problem 10

class Solution {
    // M1 top-down dfs + memo
    // time O(nm) space O(mn)
    public boolean isMatch(String s, String p) {
        int[][] memo = new int[s.length()][p.length()];
        return matchHelp(s, p, 0, 0, memo);
    }
    
    private boolean matchHelp(String s, String p, int i, int j, int[][] memo) {
        // base case
        if (i == s.length() || j == p.length()) {
            if (j == p.length()) {return i == s.length();}
            while ( j < p.length()) {
                if (p.charAt(j) != '*') {return false;}
                j++;
            }
            return true;
        }
        
        if (memo[i][j] != 0) {return memo[i][j] == 1;}
        
        char cs = s.charAt(i);
        char cp = p.charAt(j);
        boolean res = false;
        if (cs == cp || cp == '?') {
            res = matchHelp(s, p, i+1, j+1, memo);
        } else {
            if (cp == '*') {
                if (matchHelp(s, p, i, j+1, memo)) {res = true;}
                if (matchHelp(s, p, i+1, j, memo)) {res = true;}
            }
        }
        memo[i][j] = res ? 1 : -1;
        return res;
    }
}



class Solution {
    // M2: bottom-up dp
    // time O(mn) space O(mn) - can be optimized with status compress
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        // init
        dp[m][n] = true;
        // dp[i][n] = false & dp[m][j] = dp[m][j+1] && p[j] == '*'
        for (int j = n-1; j >= 0; j--) {
            dp[m][j] = dp[m][j+1] && p.charAt(j) == '*';
        }
        
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                char cs = s.charAt(i);
                char cp = p.charAt(j);
                if (cs == cp || cp == '?') {
                    dp[i][j] = dp[i+1][j+1];
                } else if (cp == '*') {
                    dp[i][j] = dp[i+1][j] || dp[i][j+1];
                }
            }
        }
        return dp[0][0];             
    }    
}


// another way - but actually time still O(mn)
// https://leetcode.com/problems/wildcard-matching/discuss/17810/Linear-runtime-and-constant-space-solution