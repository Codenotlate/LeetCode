class Solution {
	// M1: dfs. for combination, we can avoid repetitive work by always change the branches to [curNum to end].
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(candidates, res, target, curList, 0);
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, int target, List<Integer> curList, int curIdx) {
    	// base case
    	if (target <= 0) {
    		if (target == 0) {
    			res.add(new LinkedList<>(curList));
    		}
    		return;
    	}

    	for (int i = curIdx; i < nums.length; i++) {
    		if (nums[i] <= target) {
    			curList.add(nums[i]);
    			dfs(nums, res, target - nums[i], curList, i);
    			curList.remove(curList.size() - 1);
    		}
    	}
    }
}

// summary for backtracking problem solutions
// https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

// time O(k * 2 ^ n') where k is the average length of each solution, since you need to deep copy
// space O(C(n, k))
// detail discuss see:
// https://leetcode.com/problems/combination-sum/discuss/16634/If-asked-to-discuss-the-time-complexity-of-your-solution-what-would-you-say



// iterative DP way (not read yet)
// https://leetcode.com/problems/combination-sum/discuss/16509/Iterative-Java-DP-solution
/* No need to use DP in this problem
this isn't really meant for dp. this dp solution involves finding EVERY possible way of getting EVERY number between 0 and t, with the given set of numbers and dp table. Time complexity is exponential. Whereas with backtracking, you can almost picture a tree; by subtracting what you know from the list, the only nodes you have would be t - something in the list.
*/


// phase2: self
// DP way: actually a knapsack problem. items are the candidates, and weight is the target
// dp[i][w] represents number of combinations to have weight w using the first i items
// dp[i][w] = dp[i - 1][w] + dp[i][w - candidats[i]]
// optimize to 1d
// but this problem ask for combination not the # of combinations, so not applied here.

// phase3 self
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(candidates, target, 0, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int[] cand, int target, int curIdx, List<Integer> curList, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new LinkedList(curList));
            return;
        }
        
        for (int i = curIdx; i < cand.length; i++) {
            if (target < cand[i]) {continue;}
            curList.add(cand[i]);
            dfs(cand, target - cand[i], i, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}














