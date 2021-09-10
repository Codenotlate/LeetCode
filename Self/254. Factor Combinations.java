// Phase3 self: M1 very slow

class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new LinkedList<>();
        if (n == 1) {return res;}
        dfs(n, 2, new LinkedList<Integer>(), 1, res);
        return res;
    }
    
    
    private void dfs(int n, int curNum, List<Integer> curList, int curRes, List<List<Integer>> res) {
        if (n <= curRes) {
            if (n == curRes) {
                res.add(new LinkedList(curList));
            }  
            return;
        }
        
        for (int i = curNum; i <= n / curRes && i <= n /2; i++) {
            if (n % i != 0) {continue;}
            curList.add(i);
            dfs(n, i, curList, curRes * i, res);
            curList.remove(curList.size() - 1);
        }
    }
}


// time complexity and better solutions see:
// https://leetcode.com/problems/factor-combinations/discuss/68040/My-Recursive-DFS-Java-Solution

// M2: faster way
// directly add i and n/i,
class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(n, 2, new LinkedList<Integer>(), res);
        return res;
    }
    
    
    private void dfs(int n, int curNum, List<Integer> curList, List<List<Integer>> res) {
        for (int i = curNum; i * i <= n; i++) {
            if (n % i != 0) {continue;}
            curList.add(i);
            curList.add( n / i);
            res.add(new LinkedList(curList));
            curList.remove(curList.size() - 1);
            dfs(n / i, i, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}