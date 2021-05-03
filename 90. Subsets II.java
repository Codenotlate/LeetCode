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







