class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        Arrays.sort(nums);
        dfs(res, curList, nums, 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> curList, int[] nums, int curNum) {
    	res.add(new LinkedList<>(curList));
    	for (int i = curNum; i < nums.length; i++) {
    		// skip dup
    		if (i > curNum && nums[i] == nums[i - 1]) {continue;}
    		curList.add(nums[i]);
    		dfs(res, curList, nums, i + 1);
    		curList.remove(curList.size() - 1);
    	}
    }
}

// two way of recursive thinking
// one is each level has n branches, another one is each level on has 1 element (like what I used to think before)
// https://leetcode.com/problems/subsets-ii/discuss/169226/Java-Two-Way-of-Recursive-thinking
// one element each level, use a boolean to indicate whether previous same value is chosen or not
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        helper(res,new ArrayList<>(),nums,0,false);
        return res;
    }
    
    public void helper(List<List<Integer>> res, List<Integer> ls, int[] nums, int pos, boolean choosePre) {
        if(pos==nums.length) {
            res.add(new ArrayList<>(ls));
            return;
        }
        helper(res,ls,nums,pos+1,false);
        if(pos>=1&&nums[pos]==nums[pos-1]&&!choosePre) return;
        ls.add(nums[pos]);
        helper(res,ls,nums,pos+1,true);
        ls.remove(ls.size()-1);
    }
}


// phase3 self
// M1
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, 0, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int[] nums, int curIdx, List<Integer> curList, List<List<Integer>> res) {
        if (curIdx == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        
        res.add(new LinkedList(curList));
        for (int i = curIdx; i < nums.length; i++) {
            // skip dup
            if (i > curIdx && nums[i] == nums[i - 1]) {continue;}
            curList.add(nums[i]);
            dfs(nums, i + 1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}

// M2
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, 0, new LinkedList<Integer>(), res, false);
        return res;
    }
    
    private void dfs(int[] nums, int curIdx, List<Integer> curList, List<List<Integer>> res, boolean prevChosen) {
        if (curIdx == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        
        // not choose current
        dfs(nums, curIdx + 1, curList, res, false);
        // choose current only when meet below conditions
        if (curIdx == 0 || nums[curIdx] != nums[curIdx - 1] || prevChosen) {
            curList.add(nums[curIdx]);
            dfs(nums, curIdx + 1, curList, res, true);
            curList.remove(curList.size() - 1);
        }
    }
}

// Review self
// similar to above M2
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, 0, new LinkedList<Integer>(), res, false);
        return res;
    }
    
    private void dfs(int[] nums, int i, List<Integer> curList, List<List<Integer>> res, boolean prevInclude) {   
        if (i == nums.length) {res.add(new LinkedList(curList)); return;}
        dfs(nums, i+1, curList, res, false);
        if (i > 0 && nums[i] == nums[i-1] && !prevInclude) {return;}        
        curList.add(nums[i]);
        dfs(nums, i+1, curList, res, true);
        curList.remove(curList.size() - 1);      
    }
}







