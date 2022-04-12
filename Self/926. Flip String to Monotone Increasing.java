/* Initial thought
DP way. can either consider forward or backward. If we consider forward way: dp[i] represents the result of string[i,n-1]. Then if dp[i] == 0, dp[i] = dp[i+1]; if dp[i] == 1. then we have two choices, either we change it to 0, and dp[i] = 1 + dp[i+1], or we have it remain 1, then all the 0's in string[i+1,n-1] needs to be changed to 1. Thus if dp[i] == 1, dp[i] = min(dp[i+1]+1, countZeros(i+1)).
In order to have O(n) time, we need to have an array storing the countZeros for each position(i) till end in advance. This will take O(n) time before hand. (actually this can be combined with the dp process)
And since dp[i] depends on dp[i+1], we need to start the dp from n-1.

time O(n) space O(n)
*/
class Solution {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int[] countZero = new int[n+1];
        int[] dp = new int[n+1];
        char[] chars = s.toCharArray();
        
        for (int i = n-1; i >= 0; i--) {
            if (chars[i] == '1') {
                countZero[i] = countZero[i+1];
                dp[i] = Math.min(dp[i+1] +1, countZero[i+1]);
            } else {
                countZero[i] = countZero[i+1] + 1;
                dp[i] = dp[i+1];
            }
        }
        return dp[0];
    }
}

/* improved thought
The above DP way can be optimized to O(1) space, since dp[i] only depends on dp[i+1], and count[i] only depends on count[i+1].

time O(n) space O(1)
*/
class Solution {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int countZero = 0;
        int dp = 0;
        
        for (int i = n-1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '1') {
                dp= Math.min(dp+1, countZero);
            } else {
                countZero++;;
            }
        }
        return dp;
    }
}


// another preSum way
// https://leetcode.com/problems/flip-string-to-monotone-increasing/discuss/183851/C%2B%2BJava-4-lines-O(n)-or-O(1)-DP
// Basically, try to convert all 1 to 0 before and all 0 to 1 after current index respectively. Store the minimum as result








// Review
/*Thought
This can be a dp problem. Or recursive way to solve. 
M1: Recursive way(slow). At each position we have several choices. If cur = 0, and hasOne = true, then return 1 + recur(s[cur+1:], hasOne = true). If cur = 0 and hasOne = false, then return recur(s[cur+1:], false). If cur = 1, and hasOne = true, then return recur(s[cur+1:], true); else cur = 1 and hasOne = false, then return min(recur(s[cur+1:], true), 1 + recur(s[cur+1:], false). The base case is if lastchar = 1, return 0. If lastchar = 0, return hasOne ? 1 : 0.
Since there's repetitive work, adding memo memo[i][0/1] represents the result for s[i:] with hasOne = false/true.
M2: DP way. dp[i] represents the result of s[i:]. Then if s[i] = 0, dp[i] = dp[i+1]. if s[i] = 1, then dp[i] = min(dp[i+1] + 1, countZeros(i+1)). i.e. change to zero, or not remain as 1 then all the following 0's need to be changed to 1. 
We process backwards, and udpate countZeros along the way to make it O(1) for finding countZeros.
time O(n)  space O(n) for both M1 and M2 way.

M2.1: DP space improved way. The space can be optimized to O(1).
*/
// M1: recusive way - slow
class Solution {
    public int minFlipsMonoIncr(String s) {
        int[][] memo = new int[s.length()][2];
        for (int[] r: memo) {Arrays.fill(r, -1);}
        return helper(s, 0, 0, memo);
    }
    
    private int helper(String s, int cur, int prevHasOne, int[][] memo) {
        if (memo[cur][prevHasOne] != -1) {return memo[cur][prevHasOne];}
        // base case
        if (cur == s.length() - 1) {
            if (s.charAt(cur) == '1') {memo[cur][0] = 0; memo[cur][1] = 0; return 0;}
            memo[cur][0] = 0;
            memo[cur][1] = 1;
            return prevHasOne;
        }
        char c = s.charAt(cur);
        int res = 0;
        if ( c == '0' && prevHasOne == 1) {res = 1+ helper(s, cur+1, 1, memo);}
        else if ( c== '0' && prevHasOne == 0) {res = helper(s, cur+1, 0, memo);}
        else if (c == '1' && prevHasOne == 1) {res = helper(s, cur+1, 1, memo);}
        else {res = Math.min(helper(s, cur+1,1, memo), 1+helper(s, cur+1, 0, memo));}
        memo[cur][prevHasOne] = res;
        return res;
    }
}

// M2 way
class Solution {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int[] dp = new int[n+1];
        int[] countZeros = new int[n+1];
        
        for (int i = n - 1; i >= 0; i--) {
            if(s.charAt(i) == '0') {
                dp[i] = dp[i+1];
                countZeros[i] = 1 + countZeros[i+1];
            } else {
                dp[i] = Math.min(dp[i+1] + 1, countZeros[i+1]);
                countZeros[i] = countZeros[i+1];
            }
        }
        return dp[0];        
    }  
}
// M2.1 way
class Solution {
    public int minFlipsMonoIncr(String s) {
        int n = s.length();
        int dp = 0;
        int countZeros = 0;
        
        for (int i = n - 1; i >= 0; i--) {
            if(s.charAt(i) == '0') {
                countZeros++;
            } else {
                dp = Math.min(dp + 1, countZeros);
            }
        }
        return dp;        
    }  
}

