// Phase3 self + solution help
// M1: recursion + memo
// time O(l1 * l2) space O(l1 * L2)
// note: no need to transfer between s1 and s2, due to pigeon hole principle if it can successfully cancat to s3, then |m-n| <= 1 must satisfied.
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length();
        int l2 = s2.length();
        if (l1 + l2 != s3.length()) {return false;}
        int[][] memo = new int[l1][l2];
        return dfs(s1, s2, s3, 0, 0, memo);
    }
    
    
    
    private boolean dfs(String s1, String s2, String s3, int p1, int p2, int[][] memo) {
        int p3 = p1 + p2;
        // base case: one string is used up
        if (p1 == s1.length()) {return s2.substring(p2).equals(s3.substring(p3));}
        if (p2 == s2.length()) {return s1.substring(p1).equals(s3.substring(p3));}
        
        
        if (memo[p1][p2] != 0) {return memo[p1][p2] == 1 ? true : false;}
        if (s1.charAt(p1) == s3.charAt(p3)) {
            if (dfs(s1, s2, s3, p1 + 1, p2, memo)) {memo[p1][p2] = 1; return true;}
        }
        if (s2.charAt(p2) == s3.charAt(p3)) {
            if (dfs(s1, s2, s3, p1, p2 + 1, memo)) {memo[p1][p2] = 1; return true;}
        }
        
        memo[p1][p2] = -1;
        return false;
    }
}


// M2: bottomup DP time and space same as M1, but space can be optimized to O(l2) in below M3
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length();
        int l2 = s2.length();
        if (l1 + l2 != s3.length()) {return false;}
        
        boolean[][] dp = new boolean[l1 + 1][l2 + 1];
        // dp[p1][p2] represents result of s1[0:p1-1] s2[0:p2-1] to s3[0:p1+p2-1]
        
        // init dp[0][0] and dp[0][...], dp[...][0]
        dp[0][0] = true;
        for (int p1 = 1; p1 <= l1; p1++) {
            dp[p1][0] = dp[p1 - 1][0] && s1.charAt(p1 - 1) == s3.charAt(p1-1);
        } 
        for (int p2 = 1; p2 <= l2; p2++) {
            dp[0][p2] = dp[0][p2-1] && s2.charAt(p2 - 1) == s3.charAt(p2-1);
        }
        
        for (int p1 = 1; p1 <= l1; p1++) {
            for (int p2 = 1; p2 <= l2; p2++) {
                int p3 = p1 + p2;
                char c1 = s1.charAt(p1-1);
                char c2 = s2.charAt(p2-1);
                char c3 = s3.charAt(p3-1);
                dp[p1][p2] = (dp[p1][p2 - 1] && c2 == c3) || (dp[p1 - 1][p2] && c1 == c3);
                
            }
        }
        return dp[l1][l2];
    }
}



// M3: status compress, space O(l2)
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length();
        int l2 = s2.length();
        if (l1 + l2 != s3.length()) {return false;}
        
        boolean[] dp = new boolean[l2 + 1];
        // dp[p1][p2] represents result of s1[0:p1-1] s2[0:p2-1] to s3[0:p1+p2-1]
        
        // init dp[0][0] 
        dp[0] = true;
        // init dp[0][p2]
        for (int p2 = 1; p2 <= l2; p2++) {
            dp[p2] = dp[p2-1] && s2.charAt(p2 - 1) == s3.charAt(p2-1);
        }
        
        for (int p1 = 1; p1 <= l1; p1++) {
            // init dp[p1][0]
            dp[0] = dp[0] && s1.charAt(p1 - 1) == s3.charAt(p1-1);
            for (int p2 = 1; p2 <= l2; p2++) {
                int p3 = p1 + p2;
                char c1 = s1.charAt(p1-1);
                char c2 = s2.charAt(p2-1);
                char c3 = s3.charAt(p3-1);
                dp[p2] = (dp[p2 - 1] && c2 == c3) || (dp[p2] && c1 == c3);
                
            }
        }
        return dp[l2];
    }
}



