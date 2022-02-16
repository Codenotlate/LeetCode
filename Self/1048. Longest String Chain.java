class Solution {
    /* Initial thought
    dp or dfs+memo idea: dp[s] = max(dp[s remove one char]) + 1
    time O(n * ave(len of word)^2)
    space O(n * ave(len of word)) for the string generated at each level of dfs (official solution space might be wrong)
    If we use dfs + memo, then we don't need to sort the array first. But if we use dp, we need to sort the array based on words length first, since we need to first determine the result for shorter word and then use it for longer word. The sort itself can be optimized from O(nlogn) to O(n) using bucket sort based on count.
    */
    public int longestStrChain(String[] words) {
        Map<String, Integer> memo = new HashMap<>();
        Set<String> wordSet = new HashSet(Arrays.asList(words));
        int[] maxLen = new int[]{0};
        for (String w: words) {
            dfs(w, memo, maxLen, wordSet);
        }
        return maxLen[0];
    }
    
    private int dfs(String w, Map<String, Integer> memo, int[] maxLen, Set<String> wordSet) {
        if (memo.containsKey(w)) {return memo.get(w);}
        int res = 0;
        for (int i = 0; i < w.length(); i++) {
            String pre = w.substring(0,i) + w.substring(i+1, w.length());
            if (wordSet.contains(pre)) {
                res = Math.max(res, dfs(pre, memo, maxLen, wordSet));
            }            
        }
        maxLen[0] = Math.max(maxLen[0], res + 1);
        memo.put(w, res+1);
        return res+1;
    }
}

class Solution {
    // dp way: use regular sort for now, time O(nlogn + n*len^2) space O(n)
    public int longestStrChain(String[] words) {
        Arrays.sort(words,(a,b)->(a.length() - b.length()));
        Map<String, Integer> dp = new HashMap<>();
        int res = 0;
        for (String w: words) {
            if (w.length() == 1) {
                dp.put(w,1);
                res = Math.max(res, 1);
            } else {
                int len = 1;
                for (int i = 0; i < w.length(); i++) {
                    String pre = w.substring(0, i) + w.substring(i+1);
                    len = Math.max(len, dp.getOrDefault(pre, 0) + 1);
                }
                dp.put(w, len);
                res = Math.max(res, len);
            }
        }
        return res;
    }
}

// good solution
// https://leetcode.com/problems/longest-string-chain/discuss/294890/JavaC%2B%2BPython-DP-Solution