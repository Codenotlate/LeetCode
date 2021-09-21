class Solution {
	// M1: dp knapsack problem (complete with order requirement of the item)
    // time O(s.len * wordDict.len * ave(word.len in wordDict))
    // space O(s.len)
    public boolean wordBreak(String s, List<String> wordDict) {
    	boolean[] dp = new boolean[s.length() + 1];
    	dp[0] = true;
    	for (int w = 0; w <= s.length(); w++) {
    		for (String word: wordDict) {
    			int l = word.length();
    			if (w >= l && word.equals(s.substring(w - l, w))) {
    				dp[w] = dp[w] || dp[w - l];
    			}
    		}
    	}
    	return dp[s.length()];
        
    }
}


class Solution {
	// M2: DFS + memoization (time space same as above)
    public boolean wordBreak(String s, List<String> wordDict) {
        int[] memo = new int[s.length()];
        Arrays.fill(memo, -1); // to distinguish between unvisited or 0 & 1 as visted status
        return dfs(s, wordDict, memo, 0);
    }

    private boolean dfs(String s, List<String> wordDict, int[] memo, int curPos) {
    	// base case
    	if (curPos == s.length()) {return true;}
    	// memo part
    	if (memo[curPos] != -1) {return memo[curPos] == 0 ? false : true;}
    	// dfs part
    	for (String word: wordDict) {
    		int len = word.length();
    		if (curPos + len <= s.length() && word.equals(s.substring(curPos, curPos + len))) {
    			if (dfs(s, wordDict, memo, curPos + len)) {
    				memo[curPos] = 1;
    				return true;
    			}
    		}
    	}
    	memo[curPos] = 0;
    	return false;
    }
}




// good comment about time complexity 
// https://leetcode.com/problems/word-break/discuss/43790/Java-implementation-using-DP-in-two-ways


// Phase3 self
// slightly different version of dfs + memo
// time O(n^3) space O(n) where n = s.len
// this way similar to the above "Java-implementation-using-DP-in-two-ways" posts second way
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>();
        int maxLen = 0;
        for (String w: wordDict) {
            maxLen = Math.max(maxLen, w.length());
            wordSet.add(w);
        }
        int[] memo = new int[s.length()];
        return dfs(s, wordSet, 0, memo, maxLen);
    }
    
    
    private boolean dfs(String s, Set<String> wordSet, int curIdx, int[] memo, int maxLen) {
        if (curIdx == s.length()) {return true;}
        if (memo[curIdx] != 0) {return memo[curIdx] == 1 ? true : false;}
        for (int i = 1; i + curIdx <= s.length() && i <= maxLen; i++) {
            String sub = s.substring(curIdx, curIdx + i);
            if (wordSet.contains(sub)) {
                if (dfs(s, wordSet, curIdx + i, memo, maxLen)) {
                    memo[curIdx] = 1;
                    return true;
                }
            }
        }
        memo[curIdx] = -1;
        return false;
    }
}





// M2: still a little bit confused

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // knapsack problem DP: and since we require dp[w-len(item i)] must be updated and finalized before dp[w], thus we need to loop w first then i
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        
        for (int w = 1; w <= s.length(); w++) {
            for (String word: wordDict) {
                if (w >= word.length() && word.equals(s.substring(w - word.length(), w))) {
                    dp[w] = dp[w] || dp[w - word.length()];
                }               
            }
        }
        return dp[s.length()];
        
    }
}