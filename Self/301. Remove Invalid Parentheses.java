// both BFS and DFS time O(2^n) space O(2^n)


class Solution {
    public List<String> removeInvalidParentheses(String s) {
        // one pass to get misleft and misright
        int misleft = 0;
        int misright = 0;
        for (char c: s.toCharArray()) {
        	if (c == '(') {misleft++;}
        	else if (c == ')') {
        		if (misleft > 0) {misleft--;}
        		else {misright++;}
        	}
        }
        // now get misleft and misright

        Set<String> res = new HashSet<>();
        StringBuilder curList = new StringBuilder();
        dfs(res, s.toCharArray(), curList, 0, 0, 0, misleft, misright);

        return new ArrayList<String>(res);
    }



    private void dfs(Set<String> res, char[] s, StringBuilder curList, int curPos, int curleft, int curright, int misleft, int misright) {
    	// base case
    	if (curPos == s.length) {
    		if (misleft == 0 && misright == 0) {res.add(curList.toString());}
    		return;
    	}

    	char c = s[curPos];
    	// cases we can skip the current char
    	if ((c == '(' && misleft > 0) || (c == ')' && misright > 0)) {
    		if (c == '(') {
    			dfs(res, s, curList, curPos + 1, curleft, curright, misleft - 1, misright);
    		} else {
    			dfs(res, s, curList, curPos + 1, curleft, curright, misleft, misright - 1);
    		}
    	}
    	// other than the cases above where we could skip, we append current char
    	curList.append(c);
    	if (c != '(' && c != ')') {
    		dfs(res, s, curList, curPos + 1, curleft, curright, misleft, misright);
    	} else if (c == '(') {
    		dfs(res, s, curList, curPos + 1, curleft + 1, curright, misleft, misright);
    	} else if (curleft > curright) {
    		dfs(res, s, curList, curPos + 1, curleft, curright + 1, misleft, misright);
    	}
    	// backtracking, remove from curList
    	curList.deleteCharAt(curList.length() - 1);

    }
}


// solution summary page
// https://leetcode.com/problems/remove-invalid-parentheses/discuss/75038/Evolve-from-intuitive-solution-to-optimal-a-review-of-all-solutions
// 1. can also do it in BFS: BFS guarantee the shortest pass, we improve DFS by first to get misleft and misright in above method
// 2. to avoid duplicates, instead of using a set to add result, we can use similar idea as combination -- n branches and only skip first occurance starting from curPos



// one BFS way: not optimal: still added invalid string into queue, but easier to understand
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new LinkedList<>();
        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(s, 0));
        while (!queue.isEmpty()) {
        	Pair<String, Integer> cur = queue.poll();
        	String curS = cur.getKey();
        	int pos = cur.getValue();
        	if (isValid(curS)) {res.add(curS);}
        	else if (res.isEmpty()){ // if res is not empty, meaning we have find at least one with min removal, thus can't remove more
        		for (int i = pos; i < curS.length(); i++) {
        			if ((curS.charAt(i) == '(' || curS.charAt(i) == ')') && (i == pos || curS.charAt(i) != curS.charAt(i - 1))) {
        				queue.add(new Pair(curS.substring(0, i) + curS.substring(i + 1, curS.length()), i));
        			}
        		}
        	}
        }
        return res;
    }


    private boolean isValid(String s) {
    	int count = 0;
    	for (char c: s.toCharArray()) {
    		if (c != '(' && c != ')') {continue;}
    		if (c == '(') {count++;}
    		else {
    			if (count == 0) {return false;}
    			count--;
    		}
    	}
    	return count == 0;
    }
}







