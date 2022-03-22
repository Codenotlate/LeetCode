class Solution {
	// dfs + backtracking
	// time O(n!)
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        int[] visited = new int[nums.length];
        dfs(nums, visited, res, curList);
        return res;
    }

    private void dfs(int[] nums, int[] visited, List<List<Integer>> res, List<Integer> curList) {
    	if (curList.size() == nums.length) {
    		// not need to add a new list, otherwise they all point to the same list object
    		// and it will be emptied by later operations
    		res.add(new ArrayList<>(curList));
    		return;
    	}

    	for (int i = 0; i < nums.length; i++) {
    		if (visited[i] != 1) {
    			visited[i] = 1;
    			curList.add(nums[i]);
    			dfs(nums, visited, res, curList);
    			visited[i] = 0;
    			curList.remove(curList.size() - 1);
    		}
    	}
    }
}


// general backtesting templates summary
// https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning)


// another idea
// https://leetcode.com/problems/permutations/discuss/18436/Java-Clean-Code-Two-recursive-solutions



// phase 3 self
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, new LinkedList<Integer>(), new HashSet<Integer>(), res);
        return res;
    }
    
    
    private void dfs(int[] nums, List<Integer> curList, Set<Integer> visited, List<List<Integer>> res) {
        if (curList.size() == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        
        for (int n: nums) {
            if (visited.contains(n)) {continue;}
            visited.add(n);
            curList.add(n);
            dfs(nums, curList, visited, res);
            curList.remove(curList.size() - 1);
            visited.remove(n);
        }
    }
}


// Review self
class Solution {
    // DFS + backtracting + visited
    public List<List<Integer>> permute(int[] nums) {
        Set<Integer> visited = new HashSet<>();
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, visited, new LinkedList<Integer>(), res);
        return res;
    }
    
    
    private void dfs(int[] nums, Set<Integer> visited, List<Integer> curList, List<List<Integer>> res) {
        if (curList.size() == nums.length) {res.add(new LinkedList(curList)); return;}
        for (int n:nums) {
            if (visited.contains(n)) {continue;}
            curList.add(n);
            visited.add(n);
            dfs(nums, visited, curList, res);
            curList.remove(curList.size() - 1);
            visited.remove(n);
        }
    }
}




// Review
/*Initial thought
dfs + backtracking
for each position in the result, n choices (skip visited ones).

*/
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        int[] visited = new int[nums.length];
        dfs(nums, 0, visited, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int[] nums, int i, int[] visited, List<Integer> curList, List<List<Integer>> res) {
        if (i >= nums.length) {res.add(new LinkedList(curList)); return;}
        for (int j = 0; j < nums.length; j++) {
            if (visited[j] != 0 ) {continue;}
            curList.add(nums[j]);
            visited[j] = 1;
            dfs(nums, i+1, visited, curList, res);
            visited[j] = 0;
            curList.remove(curList.size() - 1);
        }
        
    }
}




