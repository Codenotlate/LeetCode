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



// Phase 3 self review 
/* For all DFS ways:
    time O(n * 2^n)) - n for replicating curList to res, and in total 2^n recursions
    space O(n * 2^n + n) - n * 2^n is for result, n is the recursion stack space. If we don't count the space of the result, the space is O(n).
    For BFS way:
    time O(n * 2^n)
    space also O(n * 2^n + n) the former part is for result space, the 'n' part is for the cur/addnew part we polled and created during the BFS.
*/
// M1: DFS, loop each int in nums, at each node have two choices: include that int or not include. Need to backtrack for the include choice. Each dfs represents at each int node in nums array.
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(nums, 0, curList, res);
        return res;
    }
    
    
    private void dfs(int[] nums, int curIdx, List<Integer> curList, List<List<Integer>> res) {
        if (curIdx == nums.length){
            res.add(new LinkedList(curList));
            return;
        }
        
        dfs(nums, curIdx + 1, curList, res);
        curList.add(nums[curIdx]);
        dfs(nums, curIdx + 1, curList, res);
        curList.remove(curList.size() - 1);
    }
}

// M2: DFS, at each int node in nums, there are [curIdx+1, nums.length-1] choices, each choice represent which int(at index i) in the index range[curIdx + 1, nums.length-1] we are going to choose in this dfs level. Then continue the dfs with shrinked range: [i + 1, nums.length - 1]
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        dfs(nums, 0, curList, res);
        return res;
    }
    
    
    private void dfs(int[] nums, int i, List<Integer> curList, List<List<Integer>> res) {
        if (i == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        
        // record curList in res along the way
        res.add(new LinkedList(curList));
        
        for (int j = i; j < nums.length; j++) {
            curList.add(nums[j]);            
            dfs(nums, j + 1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}

// M3: BFS, use the queue as the final res container. For each int node i in nums, we poll out all the list (composed of int with index < i) from queue. add back the polled out list (representing not choose nums[i]), also add the new list equaling to original list + nums[i] (representing nums[i] included).
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        // BFS way: using a queue and convert to list in the end
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(new LinkedList<>());
        for (int i = 0; i < nums.length; i++) {
            int size = queue.size();
            while (size-- > 0) {
                List<Integer> curList = queue.poll();
                List<Integer> incNext = new LinkedList(curList);
                incNext.add(nums[i]);
                queue.add(curList);
                queue.add(incNext);
            }
        }
        
        return (List)queue;
        
    }
}



// Review self
// similar to above M2
class Solution {
    // DFS + backtracking way 2: each level [i,end] choices or don't choose any of them, arr.len of levels
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, 0, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(int[] nums, int i, List<Integer> curList, List<List<Integer>> res) {
        res.add(new LinkedList(curList));
        if (i == nums.length) { return;}
        
        for (int j = i; j < nums.length; j++) {
            curList.add(nums[j]);
            dfs(nums, j+1, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}

// similar to above M3
class Solution {
    // try BFS way
    public List<List<Integer>> subsets(int[] nums) {
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(new LinkedList<>());
        for (int i = 0; i < nums.length; i++) {
            int size = queue.size();
            while (size-- > 0) {
                List<Integer> curList = queue.poll();
                queue.add(curList);
                List<Integer> nextList = new LinkedList(curList);
                nextList.add(nums[i]);
                queue.add(nextList);
            }
        }
        return (List)queue;
    }
}