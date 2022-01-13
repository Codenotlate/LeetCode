class Solution {
	// do this one by one
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(res, curList, n, k, 1);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> curList, int n, int k, int curNum) {
    	// base case
    	if (curNum > n || curList.size() == k) {
    		if (curList.size() == k) {
    			res.add(new ArrayList(curList));
    		}
    		return;
    	}

    	dfs(res, curList, n, k, curNum + 1);
    	curList.add(curNum);
    	dfs(res, curList, n, k, curNum + 1);
    	curList.remove(curList.size() - 1);
    } 
}


class Solution {
	// do this for all neighbors from curNum to n
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(res, curList, n, k, 1);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> curList, int n, int k, int curNum) {
    	// base case
		if (curNum > n || curList.size() == k) {
    		if (curList.size() == k) {
    			res.add(new ArrayList(curList));
    		}
    		return;
    	}
		// change i <= n to n - k + curList.size() + 1, largely increase the speed
		for (int i = curNum; i <= n - k + curList.size() + 1; i++) {
			curList.add(i);
	    	dfs(res, curList, n, k, i + 1);
	    	curList.remove(curList.size() - 1);
		}
    	
    } 
}


// phase2 self
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(n, k, 1, curList, res);
        return res;
    }
    
    private void dfs(int n, int k, int curNum, List<Integer> curList, List<List<Integer>> res) {
        if (k == 0) {
            res.add(new LinkedList(curList));
            return;
        }
        
        for (int i = curNum; i <= n - k + 1; i++) {
            curList.add(i);
            dfs(n, k - 1, i + 1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}


// another way based on C(n,k)=C(n-1,k-1)+C(n-1,k), DP way
// https://leetcode.com/problems/combinations/discuss/27019/A-short-recursive-Java-solution-based-on-C(nk)C(n-1k-1)%2BC(n-1k)
// https://leetcode.com/problems/combinations/discuss/27090/DP-for-the-problem

// phase3 self
// M1: choices: for each one in range [cur, n - k + 1]
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(n, 1, new LinkedList<Integer>(), res, k);
        return res;
    }
    
    private void dfs(int n, int cur, List<Integer> curList, List<List<Integer>> res, int k) {
        if (k == 0) {res.add(new LinkedList(curList)); return;}
        for (int i = cur; i <= n-k+1; i++) {
            curList.add(i);
            dfs(n, i+1, curList, res, k-1);
            curList.remove(curList.size() -1);
        }
    }
}

// M2: choices: two: include cur or not

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(n, 1, new LinkedList<Integer>(), res, k);
        return res;
    }
    
    private void dfs(int n, int cur, List<Integer> curList, List<List<Integer>> res, int k) {
        if (k == 0 || cur > n) {
            if (k == 0) {res.add(new LinkedList(curList));}
            return;
        }
        
        dfs(n, cur + 1, curList, res, k);
        curList.add(cur);
        dfs(n, cur + 1, curList, res, k-1);
        curList.remove(curList.size() -1);

    }
}

// Review self
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(1, n, k, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int start, int end, int k, List<Integer> curList, List<List<Integer>> res) {
        if (k == 0) {res.add(new LinkedList(curList)); return;}
        if (end - start + 1 < k) {return;}
        for (int i = start; i <= end; i++) {
            curList.add(i);
            dfs(i+1, end, k-1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}










