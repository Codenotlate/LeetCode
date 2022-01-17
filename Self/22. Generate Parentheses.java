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









