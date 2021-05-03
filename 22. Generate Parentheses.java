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











