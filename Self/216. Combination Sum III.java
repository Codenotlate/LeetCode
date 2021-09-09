class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(res, curList, k, n, 1);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> curList, int k, int n, int curNum) {
    	// base case
    	// actually curNum > 9 can be omid here, since k >=2, meaning i <= 8, thus i + 1 will be always <=9
    	if (n == 0 || k == 0 || curNum > 9) {
    		if (n == 0 && k == 0) {res.add(new LinkedList<>(curList));}
    		return;
    	}

    	for (int i = curNum; i <= 9 - k + 1; i++) {
    		curList.add(i);
    		dfs(res, curList, k - 1, n - i, i + 1);
    		curList.remove(curList.size() - 1);
    	}
    }
}


// phase2 self
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(n, k, res, curList, 1);
        return res;
    }
    
    private void dfs(int n, int k, List<List<Integer>> res, List<Integer> curList, int curNum) {
        if (n <= 0 || k <= 0 || curNum > Math.min(9, n)) {
            if (n == 0 && k == 0) {res.add(new LinkedList(curList));}
            return;                                  
        }
        
        for (int i = curNum; i <= 9 && i <= n; i++) {
            curList.add(i);
            dfs(n - i, k - 1, res, curList, i + 1);
            curList.remove(curList.size() - 1);
        }
    }
}



// phase3 self
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(n, k, 1, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int n, int k, int curNum, List<Integer> curList, List<List<Integer>> res) {
        if (n == 0 || curList.size() == k) {
            if (n == 0 && curList.size() == k) {
                res.add(new LinkedList(curList));
            }
            return;
        }
        for (int i = curNum; i <= 9 && i <= n; i++) {
            curList.add(i);
            dfs(n - i, k, i + 1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
} 

// or slightly diff one
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(n, k, 1, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int n, int k, int curNum, List<Integer> curList, List<List<Integer>> res) {
        if (n == 0 || k == 0) {
            if (n == 0 && k == 0) {
                res.add(new LinkedList(curList));
            }
            return;
        }
        for (int i = curNum; i <= 9 && i <= n; i++) {
            curList.add(i);
            dfs(n - i, k - 1, i + 1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}