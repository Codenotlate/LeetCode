class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        res.add(new LinkedList());
        dfs(res, curList, nums, 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> curList, int[] nums, int curNum) {
    	for (int i = curNum; i < nums.length; i++) {
    		curList.add(nums[i]);
    		res.add(new LinkedList<>(curList));
    		dfs(res, curList, nums, i + 1);
    		curList.remove(curList.size() - 1);
    	}
    }
}

// all solutions have same time O(2^n * n) space O(2^n * n)

// BFS way
// https://leetcode.com/problems/subsets/discuss/122645/3ms-easiest-solution-no-backtracking-no-bit-manipulation-no-dfs-no-bullshit


// another dfs way
/*
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> subsets = new ArrayList<>();
    List<Integer> tempSubset = new ArrayList<>();
    for (int size = 0; size <= nums.length; size++) {
        backtracking(0, tempSubset, subsets, size, nums); // 不同的子集大小
    }
    return subsets;
}

private void backtracking(int start, List<Integer> tempSubset, List<List<Integer>> subsets,
                          final int size, final int[] nums) {

    if (tempSubset.size() == size) {
        subsets.add(new ArrayList<>(tempSubset));
        return;
    }
    for (int i = start; i < nums.length; i++) {
        tempSubset.add(nums[i]);
        backtracking(i + 1, tempSubset, subsets, size, nums);
        tempSubset.remove(tempSubset.size() - 1);
    }
}
*/

// bit manipulating way
// https://leetcode.com/problems/subsets/discuss/27288/My-solution-using-bit-manipulation

// phase 2 self
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(nums, 0, curList, res);
        return res;
    }
    
    private void dfs(int[] nums, int curIdx, List<Integer> curList, List<List<Integer>> res) {
        if (curIdx == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        dfs(nums, curIdx + 1, curList, res);
        curList.add(nums[curIdx]);
        dfs(nums, curIdx + 1, curList, res);
        curList.remove(curList.size() - 1);
    }
}

// BFS way
// https://leetcode.com/problems/subsets/discuss/122645/3ms-easiest-solution-no-backtracking-no-bit-manipulation-no-dfs-no-bullshit

// phase2 self BFS way:
// actually no need to use queue, use List directly and then use list.get(i) to create addnew and add to list
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(new LinkedList<>());
        int i = 0;
        while (i < nums.length) {
            int size = queue.size();
            int count = 0;
            while (count < size) {
                List<Integer> cur = queue.poll();
                count++;
                List<Integer> addnew = new LinkedList(cur);
                addnew.add(nums[i]);
                queue.add(cur);
                queue.add(addnew);
            }
            i++;
        }
        return (List) queue;
    }
}

