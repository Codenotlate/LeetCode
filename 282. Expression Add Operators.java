class Solution {
    public List<String> addOperators(String num, int target) {
        List<String> res = new LinkedList<>();
        if (num == null || num.length() == 0) {return res;}
        long curEval = 0;
        long multed = 0;
        dfs(res, new StringBuilder(), num, target, curEval, multed, 0);
        return res;
    }

    private void dfs(List<String> res, StringBuilder curStr, String num, int target, long curEval, long multed, int pos) {
    	if (pos == num.length()) {
    		if (curEval == target) {res.add(curStr.toString());}
    		return;
    	}

    	for (int i = pos; i < num.length(); i++) {
    		if (num.charAt(pos) == '0' && i != pos) {break;}
    		long digits = Long.valueOf(num.substring(pos, i + 1));
    		int curStrLen = curStr.length();
    		if (pos == 0) {
    			curStr.append(digits);
    			dfs(res, curStr, num, target, digits, digits, i + 1);
    			curStr.delete(curStrLen, curStr.length());
    		} else {
    			curStr.append("+" + digits);
    			dfs(res, curStr, num, target, curEval + digits, digits, i + 1);
    			curStr.delete(curStrLen, curStr.length());

    			curStr.append("-" + digits);
    			dfs(res, curStr, num, target, curEval - digits, -digits, i + 1);
    			curStr.delete(curStrLen, curStr.length());

    			curStr.append("*" + digits);
    			dfs(res, curStr, num, target, curEval - multed + multed * digits, multed * digits, i + 1);
    			curStr.delete(curStrLen, curStr.length());
    		}
    	}
    }
}

// slightly different with below, using StringBuilder instead of substring
//https://leetcode.com/problems/expression-add-operators/discuss/71895/Java-Standard-Backtrace-AC-Solutoin-short-and-clear

// also another way in solution: to have 4 choices: * + - no action
// in this way we need to track unprocessed char from the previous level dfs









