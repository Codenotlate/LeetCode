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



// Review Phase3
class Solution {
    /* Initial thought
    Using recursion + memo
    if(p[pi] != *) {return (s[si] == p[pi] || p[pi] == '?') ? recur(si+1, pi+1): false;}
    if (p[pi] == '*') {return recur(si, pi+1)||recur(si+1, pi+1) || recur(si+1, pi); }
    use a memo to store memo[si][pi].
    */
    public boolean isMatch(String s, String p) {
        int slen = s.length();
        int plen = p.length();
        int[][] memo = new int[slen][plen];
        return recur(s, p, 0, 0, memo);
    }
    
    private boolean recur(String s, String p, int si, int pi, int[][] memo) {
        if (si >= s.length() || pi >= p.length()) {
            //note: special case,  when s is done and p has only *.
            if (si >= s.length()) {
                for (int i = pi; i < p.length(); i++) {if (p.charAt(i) != '*') {return false;}}
                return true;
            }
            return false;
        }
        if (memo[si][pi] != 0) {return memo[si][pi] == 1;}
        char pchar = p.charAt(pi);
        char schar = s.charAt(si);
        boolean res;
        if (pchar != '*') {
            res = (schar == pchar || pchar == '?') ? recur(s, p, si+1, pi+1, memo) : false;
        } else {
            res = recur(s, p, si, pi+1, memo) || recur(s, p, si+1, pi, memo);
        }
        
        memo[si][pi] = res ? 1 : -1;
        return res;        
    }
}


class Solution {
    /*     
    Try using DP bottom-up way with space optimization
    dp[i][j]  based on dp[i+1][j+1] or dp[i][j+1] & dp[i+1][j]
    */
    public boolean isMatch(String s, String p) {
        int slen = s.length();
        int plen = p.length();
        boolean[] dp = new boolean[plen + 1];
        // init dp
        dp[plen] = true;
        for (int i = plen-1; i >= 0; i--) {dp[i] = dp[i+1] && p.charAt(i) == '*';}
        boolean prev_i1j1 = true;
        for (int i = slen - 1; i >= 0; i--) {
            prev_i1j1 = dp[plen];
            dp[plen] = false;
            for (int j = plen - 1; j >= 0; j--) {
                boolean temp = dp[j];
                char schar = s.charAt(i);
                char pchar = p.charAt(j);
                if (pchar != '*') {
                    dp[j] = (schar == pchar || pchar == '?') ? prev_i1j1: false;
                } else {
                    dp[j] = dp[j+1] || dp[j];
                }
                prev_i1j1 = temp;
            }
        }
        return dp[0];
    }
    
    
}