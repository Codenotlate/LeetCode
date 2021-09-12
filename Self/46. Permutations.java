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







