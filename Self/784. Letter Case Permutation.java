class Solution {
    // M1: one branch per dfs level
    public List<String> letterCasePermutation(String S) {
        List<String> res = new LinkedList<>();
        char[] s = S.toCharArray();
        dfs(s, res, 0);
        return res;
    }
    
    private void dfs(char[] s, List<String> res, int curIdx) {
        if (curIdx >= s.length) {
            res.add(new String(s));
            return;
        }
        dfs(s, res, curIdx + 1);
        char c = s[curIdx];
        if (c <= 'z' && c >= 'a' || (c <= 'Z' && c >= 'A')) {
            if (c <= 'z' && c >= 'a') {
                s[curIdx] = Character.toUpperCase(c);
                
            } else {
                s[curIdx] = Character.toLowerCase(c);
            }
            dfs(s, res, curIdx + 1);
            s[curIdx] = c;
        }
    }
}




class Solution {
    // M2: n branches per dfs level
    public List<String> letterCasePermutation(String S) {
        List<String> res = new LinkedList<>();
        char[] s = S.toCharArray();
        dfs(s, res, 0);
        return res;
    }

    private void dfs(char[] s, List<String> res, int curIdx) {
        res.add(new String(s));
        for (int i = curIdx; i < s.length; i++) {
            char c = s[i];
            if (c <= 'z' && c >= 'a' || (c <= 'Z' && c >= 'A')) {
                if (c <= 'z' && c >= 'a') {
                    s[i] = Character.toUpperCase(c);
                    
                } else {
                    s[i] = Character.toLowerCase(c);
                }
                dfs(s, res, i + 1);
                s[i] = c;
            }
        }       
    }

}



class Solution {
    // BFS way
    // if we don't count res as space cost, then BFS way uses O(1) extra space
    public List<String> letterCasePermutation(String S) {
        List<String> res = new LinkedList<>();
        if (S.isEmpty()) {return res;}
        int curIdx = 0;
        res.add(S);
        while (curIdx < S.length()) {
            int size = res.size();
            for (int i = 0; i < size; i++) {
                char[] cur = res.get(i).toCharArray();
                char c = cur[curIdx];
                if (c <= 'z' && c >= 'a' || (c <= 'Z' && c >= 'A')) {
                    if (c <= 'z' && c >= 'a') {
                        cur[curIdx] = Character.toUpperCase(c);
                        
                    } else {
                        cur[curIdx] = Character.toLowerCase(c);
                    }
                    res.add(new String(cur));
                }
            }
            curIdx++;
        }
        return res;
    }
}




























