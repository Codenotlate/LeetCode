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


// self review
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        if (root == null) {return res;}
        dfs(root, targetSum, curList, res);
        return res;
    }
    
    private void dfs(TreeNode root, int targetSum, List<Integer> curList, List<List<Integer>> res) {
        if((root.left == null && root.right == null)) {
            if (targetSum - root.val == 0) {
                curList.add(root.val);
                res.add(new LinkedList(curList));
                curList.remove(curList.size() - 1);
            }
            return;
        }
        curList.add(root.val);
        if (root.left != null) {dfs(root.left, targetSum - root.val, curList, res);}
        if (root.right != null) {dfs(root.right, targetSum - root.val, curList, res);}
        curList.remove(curList.size() - 1);
    }
}




// review simialr to above
/* initial thought:
dfs + backtracking.
time O(n^2) space O(h)
Time complexity is not O(N), sadly. DFS recursion is O(N) by itself, but appending the answer isn't.
For this problem time complexity is purely governed by the maximum number of nodes we are going to return + O(N) traversal. It's O(N^2) for space and time.
*/
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(root, targetSum, new LinkedList<Integer>(), res);
        return res;
    }
    
    private void dfs(TreeNode root, int target, List<Integer> curList, List<List<Integer>> res) {
        if (root == null) {return;}
        curList.add(root.val);
        if (root.left == null && root.right== null && root.val == target) {
            res.add(new LinkedList(curList));
            curList.remove(curList.size() - 1);
            return;
        }
        dfs(root.left, target - root.val, curList, res);
        dfs(root.right, target - root.val, curList, res);
        curList.remove(curList.size() - 1);
    }
}


// from solution:
/*
Why Breadth First Search is bad for this problem?

We did touch briefly on this in the intuition section. BFS would solve this problem perfectly. However, note that the problem statement actually asks us to return a list of all the paths that add up to a particular sum. Breadth first search moves one level at a time. That means, we would have to maintain the pathNodes lists for all the paths till a particular level/depth at the same time.

Say we are at the level 10 in the tree and that level has e.g. 20 nodes. BFS uses a queue for processing the nodes. Along with 20 nodes in the queue, we would also need to maintain 20 different pathNodes lists since there is no backtracking here. That is too much of a space overhead.

The good thing about depth first search is that it uses recursion for processing one branch at a time and once we are done processing the nodes of a particular branch, we pop them from the pathNodes list thus saving on space. At a time, this list would only contain all the nodes in a single branch of the tree and nothing more. Had the problem statement asked us the total number of paths that add up to a particular sum (root to leaf), then breadth first search would be an equally viable approach.
*/












