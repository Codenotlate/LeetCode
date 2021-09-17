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



// Phase3 self
/* always view pre formula as a + b, assume curVal s = a + b, sumPrePart = a
then for a + b * x : 
    in M1: it == s - b + b * x
    in M2: it == (a + b) * x - a * x + a = (s - a) * x + a
*/
// diff from above method, M1 track lasted added effect(b), M2 track sumprepart (a).
class Solution {
    public List<String> addOperators(String num, int target) {
        List<String> res = new LinkedList<>();
        StringBuilder curStr = new StringBuilder();
        long curVal = 0;
        long sumPrePart = 0;
        dfs(num, target, 0, sumPrePart, curVal, curStr, res);
        return res;
    }
    
    
    private void dfs(String num, int target, int curIdx, long sumPrePart, long curVal, StringBuilder curStr, List<String> res) {
        // base case
        if (curIdx == num.length()) {
            if (curVal == target) {
                res.add(curStr.toString());
            }
            return;
        }
        
        for (int i = curIdx; i < num.length(); i++) {
            long digits = Long.valueOf(num.substring(curIdx, i + 1));
            // skip digits start with 0 but more then one digit
            if (num.charAt(curIdx) == '0' && i != curIdx) {continue;}
            // special case for the very first digit
            int curStrLen = curStr.length();
            if (curIdx == 0) {
                curStr.append(digits);
                dfs(num, target, i + 1, 0, digits, curStr, res);
                curStr.delete(curStrLen, curStr.length());
            } else {
                curStr.append("+" + digits);
                dfs(num, target, i+1, curVal, curVal + digits, curStr, res);
                curStr.delete(curStrLen, curStr.length());
                
                curStr.append("-" + digits);
                dfs(num, target, i+1, curVal, curVal - digits, curStr, res);
                curStr.delete(curStrLen, curStr.length());
                
                curStr.append("*" + digits);
                dfs(num, target, i+1, sumPrePart, sumPrePart + (curVal - sumPrePart) * digits, curStr, res);
                curStr.delete(curStrLen, curStr.length());
                
            }
        }
    }
}





