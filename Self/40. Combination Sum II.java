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




// phase3 self
// M1: each dfs level has curIdx to len - 1 choices
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new LinkedList<Integer>(), res);
        return res;
    }
    
    
    private void dfs(int[] cand, int target, int curIdx, List<Integer> curList, List<List<Integer>> res) {
        
        if (target == 0) {
            res.add(new LinkedList(curList));
            return;
        }
        
        for (int i = curIdx; i < cand.length && cand[i] <= target; i++) {
            if (i > curIdx && cand[i] == cand[i - 1]) {continue;}
            curList.add(cand[i]);
            dfs(cand, target - cand[i], i + 1, curList, res);
            curList.remove(curList.size() - 1);
        }
        
    }
}


//M2: each dfs level has two choices: include cand[curIdx] or not
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new LinkedList<Integer>(), res, false);
        return res;
    }
    
    
    private void dfs(int[] cand, int target, int curIdx, List<Integer> curList, List<List<Integer>> res, boolean prevChosen) {
        
        if (curIdx == cand.length || target == 0) {
            if (target == 0) {
                res.add(new LinkedList(curList));
            }         
            return;
        }
        
        dfs(cand, target, curIdx + 1, curList, res, false);
        if ((curIdx == 0 || cand[curIdx - 1] != cand[curIdx] || prevChosen) && target >= cand[curIdx]) {
            curList.add(cand[curIdx]);
            dfs(cand, target - cand[curIdx], curIdx + 1, curList, res, true);
            curList.remove(curList.size() - 1);
        }
    }
}


// Review self
class Solution {
    // possible dup in the condidate arr, each element used only once
    // DFS + backtracking
    // require unique comb: sort the arr first, only choose the first element in [i, end] range that has duplicates, skip the rest of that element
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new LinkedList<Integer>(),  res);
        return res;
    }
    
    private void dfs(int[] arr, int t, int i, List<Integer> curList, List<List<Integer>> res) {
        if (t == 0) {res.add(new LinkedList(curList)); return;}
        for (int j = i; j < arr.length; j++) {
            if (j > i && arr[j] == arr[j-1]) {continue;}
            if (arr[j] <= t) {
                curList.add(arr[j]);
                dfs(arr, t-arr[j], j+1, curList, res);
                curList.remove(curList.size() - 1);
            }
        }
    }
}


// time O(2^n) for this problem, why different with 39 time complexity:
/*
The runtime of this problem is bounded by the total number of possible combinations of "candidates", because we can only use the elements found in the list. The number of combinations of a list of size N is 2^N, you can google this.

In Combination Sum 1, you are not bounded by the number of elements in the list. You can use any element in "candidates" an unlimited number of times. This makes the runtime complexity much trickier.

If you think of all possible combinations in that problem as a tree, it has a branching factor of N (unique number of candidates). Additionally, the maximum possible height of the tree is the Target divided by the vale of the smallest Candidate i.e. the longest possible combination.

For instance Target = 6, Candidates = [2,3,6] the longest possible combination is [2, 2, 2]. All valid combinations must be length 3 or shorter.

Thus the height of the tree is limited to (T/M) and the branching factor is N. Thus the max possible nodes in the combinatorial tree is N ^ (T/M). You can google this relation between the height of a tree, branching factor and number of nodes.


*/














