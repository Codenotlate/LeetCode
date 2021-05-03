/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
	// M1 self: dfs + backtracking
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(root, targetSum, 0, res, new LinkedList<Integer>());
        return res;
    }

    private void dfs(TreeNode root, int targetSum, int pathSum, List<List<Integer>> res, List<Integer> curList) {
    	// base case
    	if (root == null) {return;}
    	if (root.val + pathSum == targetSum && root.left == null && root.right == null) {
    		curList.add(root.val);
    		res.add(new LinkedList<>(curList));
    		curList.remove(curList.size() - 1);
    		return;
    	}
    	pathSum += root.val;
    	curList.add(root.val);
    	dfs(root.left, targetSum, pathSum, res, curList);
    	dfs(root.right, targetSum, pathSum, res, curList);
    	curList.remove(curList.size() - 1);
    }
}

class Solution {
	// M1 self: dfs + backtracking - improve to be more precise
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(root, targetSum, res, new LinkedList<Integer>());
        return res;
    }

    private void dfs(TreeNode root, int targetSum, List<List<Integer>> res, List<Integer> curList) {
    	// base case
    	if (root == null) {return;}
    	curList.add(root.val);
    	if (root.val == targetSum && root.left == null && root.right == null) {
    		res.add(new LinkedList<>(curList));
    	}
    	
    	dfs(root.left, targetSum - root.val, res, curList);
    	dfs(root.right, targetSum - root.val, res, curList);
    	curList.remove(curList.size() - 1);
    }
}



// Java 8 LinkedList doc says - All of the operations perform as could be expected for a doubly-linked list. Operations that index into the list will traverse the list from the beginning or the end, whichever is closer to the specified index.
// thus is Java 8, remove the end of a linkedlist is also O(1)


// iterative way
// post order
// https://leetcode.com/problems/path-sum-ii/discuss/36695/Java-Solution%3A-iterative-and-recursive
// python way (can actually build a new class with node, path, curSum, and traverse new class of node iteratively)
// https://leetcode.com/problems/path-sum-ii/discuss/36829/Python-solutions-(Recursively-BFS%2Bqueue-DFS%2Bstack)

















