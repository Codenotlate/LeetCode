// Phase3 self
// used String.join("delimiter", List<String>) again
// traditional dfs + backtracking way
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new LinkedList<>();
        List<String> curStr = new LinkedList<>();
        Set<String> wordSet = new HashSet<>();
        // convert wordDict to a string and track the longest len of word in the wordDict
        int k = 0;
        for (String w: wordDict) {
            k = Math.max(k, w.length());
            wordSet.add(w)
        }
        
        dfs(s, 0, k, wordSet, curStr, res);
        return res;
    }
    
    
    private void dfs(String s, int curIdx, int k, Set<String> wordSet, List<String> curStr, List<String> res) {
        if (curIdx == s.length()) {
            res.add(String.join(" ", curStr));
            return;
        }
        
        for (int i = 0; i <= k && i < s.length() - curIdx; i++) {
            String sub = s.substring(curIdx, curIdx + i + 1);ß
            if (wordSet.contains(sub)) {
                curStr.add(sub);
                dfs(s, curIdx + i + 1, k, wordSet, curStr, res);
                curStr.remove(curStr.size() - 1);
            }
        }
    }
}

/*
maybe can improve using memo: map<curIdx, List<String>>, but there's no way to improve time from O(2^n) to O(n^2).
see the Dico reply in this post: https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS

Just read through the discussion board, here is my thought. There are several ways to improve the naive dfs method:

* memo using hashmap (like the one above)
* DP
* preprocess the string using word break I DP array to determine whether to go on or not
* precompute the max length of all words in the dictionary to reduce the number of recursive calls.

These are all good approaches when not all combinations are valid, but won't change the time complexity O(2^n) in the worse case scenario where all combinations of the string are correct (e,g, s=aaa, dic=[a, aa, aaa]). Some might argue that they reduce the number of recursive/iterative calls to n^2 using memo or DP just like word break I. However, the time complexity of each recursive call in this approach is not linear anymore. Imagine the length of sublist is 2^(n-1). Optimization only happens when the return value is a integer or boolean. This is why we don't use DP/memo to solve subsets/permutation problem because all combinations are valid. The code below combines (1) and (4) and beats 99% as the solution above suffers the problem that the dictionary size might be too large. Hope it helps.

    int maxLen = 0;
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> hs = new HashSet<>();
        for(String w: wordDict)
        {
            hs.add(w);
            if(w.length()>maxLen) maxLen = w.length(); //get the maxLen of words
        }
        Map<Integer, List<String>> map = new HashMap<>();
        return helper(hs, s, 0, map);      
    }
    
    public List<String> helper(Set<String> hs, String s, int start, Map<Integer, List<String>> map)
    {
        if(map.containsKey(start)) return map.get(start);
        List<String> list = new ArrayList<>(); 
        if(start==s.length())  list.add("");
        //reduce the # of iterations using maxLen
        for(int i=start; i<start+maxLen&&i<s.length(); i++)
        {
            if(hs.contains(s.substring(start, i+1)))
            {
                List<String> nexts = helper(hs, s, i+1, map);
                for(String next:nexts)
                {
                    if(next=="")//reaches the end
                        list.add(s.substring(start, i+1)+next);
                    else
                        list.add(s.substring(start, i+1) + " " + next);
                }
            }
        }
        map.put(start, list); 
        return list;
    }




*/