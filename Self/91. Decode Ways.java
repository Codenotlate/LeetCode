class Solution {
	// dp: dp[i] represents # of ways encoding substring s[0:i] inclusive
	// time O(n) space O(n)
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;
        for (int i = 2; i <= n; i++) {
        	int oneDigit = Integer.valueOf(s.substring(i - 1, i));
        	int twoDigit = Integer.valueOf(s.substring(i - 2, i));
        	if (oneDigit <= 9 && oneDigit >= 1) {
        		dp[i] += dp[i - 1];
        	}
        	if (twoDigit <= 26 && twoDigit >= 10) {
        		dp[i] += dp[i - 2];
        	}
        }
        return dp[n];
    }
}



// Phase3 self
class Solution {
    // M1: Top down: dfs + memo  
    public int numDecodings(String s) {
        int n = s.length();
        int[] memo = new int[n];
        Arrays.fill(memo, -1);
        Set<String> codeSet = new HashSet<>();
        for (int i = 1; i <= 26; i++) {
            codeSet.add(Integer.toString(i));
        }
        return dfs(s, 0, memo, codeSet);
    }
    
    private int dfs(String s, int curIdx, int[] memo, Set<String> codeSet) {
        if(curIdx >= s.length()) {
            return 1;
        }
        if (memo[curIdx] != -1) {return memo[curIdx];}
        
        memo[curIdx] = 0;
        for (int i = 1; i <= 2 && curIdx + i <= s.length(); i++) {
            String sub = s.substring(curIdx, curIdx + i);
            if (codeSet.contains(sub)) {
                memo[curIdx] += dfs(s, curIdx + i, memo, codeSet);
            }
        }
        return memo[curIdx];
        
    }
} 


class Solution {
    // M2: Bottom-up: dp space O(n) can be optimized
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        Set<String> codeSet = new HashSet<>();
        for (int i = 1; i <= 26; i++) {
            codeSet.add(Integer.toString(i));
        }
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <=2; k++) {
                if(i - k >= 0 && codeSet.contains(s.substring(i - k, i))) {
                    dp[i] += dp[i - k];
                }ø
            }
        }
        
        
        return dp[n];
    }
} 



// review self
class Solution {
    // M1: recursion + memo time O(n) space O(n)
    // recur(s,i) = recur(s, i+1) + recur(s,i+2)
    public int numDecodings(String s) {
        int[] memo = new int[s.length()];
        Arrays.fill(memo, -1);
        return numHelper(s, 0, memo);
    }
    
    
    private int numHelper(String s, int i, int[] memo) {
        if (i == s.length()) {return 1;}
        if (memo[i] != -1) {return memo[i];}
        int count1 = 0;
        int count2 = 0;
        if (s.charAt(i) >= '1' && s.charAt(i) <= '9') {
            count1 = numHelper(s, i+1, memo);
        }
        
        if (i + 2 <= s.length()) {
            String substr = s.substring(i, i+2);
            if (substr.compareTo("1") >= 0 && substr.compareTo("26") <= 0) {
                count2 = numHelper(s, i+2, memo);
            }
        }
        memo[i] = count1 + count2;
        return memo[i];       
    }
}

class Solution {
    // M2: DP way
    //dp[i] represents s[0:i] number result
    // dp[i] = dp[i-1] + dp[i-2] if substring meets requirement
    public int numDecodings(String s) {
        Set<String> validSet = new HashSet<>();
        for (int i = 1; i <= 26; i++) {validSet.add(Integer.toString(i));}
        int[] dp = new int[s.length()];
        dp[0] = validSet.contains(s.substring(0,1))? 1: 0;
        for (int i = 1; i < s.length(); i++) {
            int prev1 = validSet.contains(s.substring(i, i+1)) ? dp[i-1] : 0;
            int prev2 = 0;
            if(validSet.contains(s.substring(i-1, i+1))) {
                prev2 = i == 1 ? 1: dp[i-2];               
            }
            dp[i] = prev1 + prev2;
        }
        return dp[s.length() - 1];
        
    }
}



class Solution {
    // M2.1: DP way; optimize space to O(1)
    //dp[i] represents s[0:i] number result
    // dp[i] = dp[i-1] + dp[i-2] if substring meets requirement, since only need dp[i-1] and dp[i-2], thus can simply use two vars to record
    public int numDecodings(String s) {
        Set<String> validSet = new HashSet<>();
        for (int i = 1; i <= 26; i++) {validSet.add(Integer.toString(i));}
        int prev2  = 1; // represents dp[-1]
        int prev1 = validSet.contains(s.substring(0,1))? 1: 0; // represents dp[0]
        for (int i = 1; i < s.length(); i++) {
            int cur1 = validSet.contains(s.substring(i, i+1)) ? prev1 : 0;
            int cur2 = validSet.contains(s.substring(i-1, i+1)) ? prev2 : 0;
            prev2= prev1;
            prev1 = cur1 + cur2;
        }
        return prev1;
        
    }
}


// Review
/*Initial thought
DP way. for each position, there are two choices.transfer to char using current one digit or use two digits together(in the range of [1,26]). One special case is if current digit is 0, the answer is just 0. If two digits are in valid range, then the formula is dp[i] = dp[i+1] + dp[i+2]. Otherwise dp[i] = dp[i+1], where dp[i] represents the number of ways for s[i:end].
Since we only need two info, the space can be optimized to O(1) by moving backwards. And dp[s.len] and dp[s.len - 1] init as 1.
time O(n) space O(1)
*/
class Solution {
    public int numDecodings(String s) {
        int dp_prev2 = 1;
        int dp_prev1 = s.charAt(s.length() -1) == '0' ? 0 : 1;
        for (int i = s.length() - 2; i>= 0; i--) {
            int temp = dp_prev1;
            int num = (s.charAt(i) - '0') * 10 + (s.charAt(i+1) - '0');
            if (s.charAt(i) == '0') {dp_prev1 = 0;}
            else if(num >= 10 && num <= 26) {
                dp_prev1 += dp_prev2;
            }
            dp_prev2 = temp;
        }
        return dp_prev1;
    }
}





// Review 23/2/10 - mostly the same as above way(above way is more precise)
/* Thoughts
This is a dp problem. At each position, if it's 0, it will be zero starting this pos. Otherwise we check s[i] and s[i:i+1]. Assume dp[i] stands for number of ways for s[i:], then dp[i] = dp[i+1] + (if i+1 and i+2 exists and s[i:i+1] in [10,26] and dp[i+2]). dp[n] init to 1.
Since dp[i] only relies on dp[i+1] and dp[i+2], space can be optimized to O(1)

time O(n) space O(1)

 */
class Solution {
    public int numDecodings(String s) {
        int dp_i = s.charAt(s.length() - 1)=='0'? 0:1;
        int dp_i1 = 1;
        for (int i = s.length() - 2; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                dp_i1 = dp_i;
                dp_i = 0;
                continue;
            }
            int val = 10 * (s.charAt(i) - '0') + (s.charAt(i+1) - '0');
            if (val >= 10 && val <= 26) {
                int temp = dp_i1;
                dp_i1 = dp_i;
                dp_i += temp;
            } else { // don't forget this line
                dp_i1 = dp_i;
            }
        }
        return dp_i;
    }
}