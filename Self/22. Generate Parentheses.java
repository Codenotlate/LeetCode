// time complexity for both solutions see solution page


class Solution {
	// M1: dfs + backtracking
    public List<String> generateParenthesis(int n) {
        List<String> res = new LinkedList<>();
        StringBuilder curList = new StringBuilder();
        dfs(n, n, curList, res);
        return res;
    }

    private void dfs(int nleft, int nright, StringBuilder curList, List<String> res) {
    	if (nright == 0) {
    		res.add(curList.toString());
    		return;
    	}

    	if (nleft < nright) {
    		curList.append(')');
    		dfs(nleft, nright - 1, curList, res);
    		curList.deleteCharAt(curList.length() - 1);
    	} 
    	if (nleft > 0) {
    		curList.append('(');
			dfs(nleft - 1, nright, curList, res);
			curList.deleteCharAt(curList.length() - 1);
    	}
    }
}


// dp way
//https://leetcode.com/problems/generate-parentheses/discuss/10127/An-iterative-method.
// https://leetcode.com/problems/generate-parentheses/solution/



// Phase3 self: similar as above
class Solution {
    // at each pos, the current # of left parentheses should be >= right #, and left# should be <= n.
    // dfs + backtracking
    public List<String> generateParenthesis(int n) {
        List<String> res = new LinkedList<>();
        dfs(n, 0, 0, new StringBuilder(), res);
        return res;
    }
    
    
    private void dfs(int n, int leftNum, int rightNum, StringBuilder curList, List<String> res) {
        if (curList.length() == 2 * n) {res.add(curList.toString()); return;}
        if (leftNum > rightNum) {
            curList.append(')');
            dfs(n, leftNum, rightNum+1, curList, res);
            curList.deleteCharAt(curList.length() - 1);
        } 
        if (leftNum < n) {
            curList.append('(');
            dfs(n, leftNum + 1, rightNum, curList, res);
            curList.deleteCharAt(curList.length() - 1);
        }
    }
}





// 2024.11.1
// Typical backtracking
// for each curString, need to know how many "(" is included, if number of ')' exceeds the  number of '(', current string is invalid.
// M1: to check validness when add in the current level
// M2: add regardless of validness, only check validness in the next level. M2 is slower bcs it introduces some unnecessary attempts
// for interview:time O(n * 4^n) should be good enough. Though the accurate one is Catalan number.

// M1:  (above ways using both leftCount and rightCount is more easy-to-understand code)
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> resList = new ArrayList<>();
        StringBuilder curStr = new StringBuilder('(');
        expand(curStr, resList, 0, n);
        return resList;
    }

    private void expand(StringBuilder curStr, List<String> resList, int rightCount, int n) {
        if (rightCount == n) {
            resList.add(curStr.toString());
            return;
        }
        // check if can add )
        if (2 * rightCount < curStr.length()) {
            curStr.append(')');
            expand(curStr, resList, rightCount+1, n);
            curStr.deleteCharAt(curStr.length()-1);
        }
        // check if can add (
        if (curStr.length() - rightCount < n) {
            curStr.append('(');
            expand(curStr, resList, rightCount, n);
            curStr.deleteCharAt(curStr.length()-1);
        }
    }
}
// M2: 
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> resList = new ArrayList<>();
        StringBuilder curStr = new StringBuilder('(');
        expand(curStr, resList, 0, n);
        return resList;
    }

    private void expand(StringBuilder curStr, List<String> resList, int rightCount, int n) {
        if (rightCount * 2 > curStr.length() || curStr.length() > 2*n) {return;}
        if (rightCount == n) {
            resList.add(curStr.toString());
            return;
        }
        // check if can add )
        String s = "()";
        for (char c: s.toCharArray()){
            curStr.append(c);
            if (c == ')') {rightCount++;}
            expand(curStr, resList, rightCount, n);
            curStr.deleteCharAt(curStr.length()-1);
        }
    }
}




