class Solution {
	// method1 backtracking use recursive DFS
	// time O(4^n)
    public List<String> letterCombinations(String digits) {
        String[] map = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        StringBuilder prefix = new StringBuilder();
        List<String> res = new LinkedList<>();
        if (digits.length() == 0) {return res;}
        combine(prefix, digits, map, res);
        return res; 
    }

    private void combine(StringBuilder prefix, String digits, String[] map, List<String> res) {
    	// base case
    	if (prefix.length() == digits.length()) {
    		res.add(prefix.toString());
    		return;
    	}
    	int i = prefix.length();
    	for (char c: map[digits.charAt(i) - '0'].toCharArray()) {
    		prefix.append(c);
    		combine(prefix, digits, map, res);
    		prefix.deleteCharAt(prefix.length() - 1);
    	}
    }
}



// method2: BFS using queue
// define the result list to be linkedlist, then can use that directly as a queue
class Solution {
    public List<String> letterCombinations(String digits) {
        String[] map = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        // note: here if we define it as List, then we can't use poll()  or peek() later
        Queue<String> res = new LinkedList<>();
        if (digits.length() == 0) {return (List) res;}
        res.add("");
        while (res.peek().length() != digits.length()) {	
        	String cur = res.poll();
        	for (char c: map[digits.charAt(cur.length()) - '0'].toCharArray()) {
        		res.add(cur + c);
        	}
        }
        return (List) res;
    }
}


// Phase 3 self
// M1: DFS + backtracking
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new LinkedList<>();
        String[] map = new String[]{"0","1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        if (digits.length() == 0) {return res;}
        StringBuilder current  = new StringBuilder();
        dfs(current, 0, digits, map, res);
        return res;
    }
    
    private void dfs(StringBuilder current, int i, String digits, String[] map, List<String> res) {
        //  base case
        if (i == digits.length()) {
            res.add(current.toString());
            return;
        }
        // backtracking
        for (char c: map[digits.charAt(i) - '0'].toCharArray()) {
            current.append(c);
            dfs(current, i + 1, digits, map, res);
            current.deleteCharAt(i);
        }
    }
}

// M2: BFS
class Solution {
    public List<String> letterCombinations(String digits) {
        Queue<String> queue = new LinkedList<>();
        if (digits.length() == 0) {return (List) queue;}
        String[] map = new String[]{"0","1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        queue.add("");
        while (queue.peek().length() < digits.length()) {
            String cur = queue.poll();
            for (char c: map[digits.charAt(cur.length()) - '0'].toCharArray()) {
                queue.add(cur + c);
            }
        }
        return (List) queue;
    }
}



























