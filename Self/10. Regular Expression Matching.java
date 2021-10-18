// Phase 3 self
class Solution {
    public boolean isMatch(String s, String p) {
        // M1: recursive way + memo
        int[][] memo = new int[s.length()][p.length()];
        // memo = -1 represents false and 1 represents true
        return matchIdx(s, p, 0, 0, memo);
    }
    
    private boolean matchIdx(String s, String p, int i, int j, int[][] memo) {
        // base case
        if (i >= s.length() || j >= p.length()) {
            if (i >= s.length()) {
                while(j < p.length()) {
                    if (p.charAt(j) == '*') {j++;}
                    else if (j < p.length() - 1 && p.charAt(j+1) == '*'){j+=2;}
                    else {return false;}
                }
                return true;
            }
            return false;
        }
        if (memo[i][j] != 0) {return memo[i][j] == 1? true: false;}
        
        boolean res= false;
        if(j == p.length() - 1 || p.charAt(j+1) != '*') {
            if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') {
                res = matchIdx(s, p, i+1, j+1, memo);
            }
        } else {
            if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') {
                res = matchIdx(s, p, i+1, j, memo);
            }
            if (matchIdx(s, p, i, j+2, memo)) {res = true;}
        }
        
        memo[i][j] = res == true? 1 : -1;
        return res;
    }
}




// more consice top down in solution
// https://leetcode.com/problems/regular-expression-matching/solution/


// also can use bottom-up