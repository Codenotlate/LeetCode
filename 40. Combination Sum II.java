class Solution {
	// similar as 39  && 47 with duplicates
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        Arrays.sort(candidates);
        //int[] visited = new int[candidates.length];
        //dfs(candidates, res, target, curList, 0, visited);
        dfs(candidates, res, target, curList, 0);
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, int target, List<Integer> curList, int curIdx) {
    	// base case
		if (target == 0) {
			res.add(new LinkedList<>(curList));
			return;
		}
		
    	for (int i = curIdx; i < nums.length; i++) {
    		if (nums[i] > target || (i > curIdx && nums[i - 1] == nums[i])) {
    			continue;
    		}
			curList.add(nums[i]);
			//visited[i] = 1;
			dfs(nums, res, target - nums[i], curList, i + 1);
			curList.remove(curList.size() - 1);
			//visited[i] = 0;
    	}
    }
}

// time complexity O(2^n + nlogn) = O(2^n) space O(n)
// https://leetcode.com/problems/combination-sum-ii/discuss/16871/Time-Complexity-Analysis-of-Recursive-Approach


// phase 2 self
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(candidates, target, 0, curList, res);
        return res;
    }
    
    private void dfs(int[] candidates, int target, int curIdx, List<Integer> curList, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new LinkedList(curList));
            return;
        }
        for (int i = curIdx; i < candidates.length; i++) {
            if (i > curIdx && candidates[i] == candidates[i - 1]) {continue;}
            if (target >= candidates[i]) {
                curList.add(candidates[i]);
                dfs(candidates, target - candidates[i], i + 1, curList, res);
                curList.remove(curList.size() - 1);
            }
        }
    }
}