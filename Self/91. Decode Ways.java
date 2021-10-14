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
    // M2: Bottom-up: dp 
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
                }
            }
        }
        
        
        return dp[n];
    }
} 