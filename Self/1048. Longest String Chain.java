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










// Review - bottomup dp way. can also do top-down dfs recursive way
/*Thought
determine whether s is pred of t => eliminate one char from t and compare t' whether equal to s. time O(l)
order by length, the min len group has count = 1, then for each length group, for each string in that group, delete each char to see if that string is in the length-1 group, if it has pred there, its count = max(pred count) + 1. Need to check length group from small to large.
Since l is in range[1,16] we can do counting sort instead of nlogn sort for the length.
time O(n * l * l)
space O(nl) for the map
*/
class Solution {
    public int longestStrChain(String[] words) {
        Map<Integer, Set<String>> groups = new HashMap<>();
        Map<String, Integer> memo = new HashMap<>();
        int minLen = Integer.MAX_VALUE;
        int maxLen = Integer.MIN_VALUE;
        for (String w: words) {
            int len = w.length();
            minLen = Math.min(minLen, len);
            maxLen = Math.max(maxLen, len);
            groups.putIfAbsent(len, new HashSet<String>());
            groups.get(len).add(w);
        }
        
        int res = 1;
        for (int l = minLen; l <= maxLen; l++) {
            Set<String> set = groups.get(l);
            if (l == minLen) {
                for(String s: set) {memo.put(s,1);}
                continue;
            }
            for (String s: set) {
                int curlen = 0;                
                for (int i = 0; i < l; i++) {
                    StringBuilder chars = new StringBuilder(s);
                    String pred = chars.deleteCharAt(i).toString();
                    curlen = Math.max(curlen, memo.getOrDefault(pred, 0) + 1);
                }
                memo.put(s, curlen);
                res = Math.max(res, curlen);
            }
        }
        
        return res;
    }
}