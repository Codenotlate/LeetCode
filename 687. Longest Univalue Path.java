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
	private int maxLen = 0;

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {return 0;}
        // dfs returns the longest length of single side path 
        // that has the same value as root.val
        dfs(root);
        return maxLen;
    }

    private int dfs(TreeNode cur) {
    	if (cur == null) {return 0;}
    	int leftLen = dfs(cur.left);
    	int rightLen = dfs(cur.right);
    	if (cur.left != null && cur.left.val == cur.val) {
    		leftLen += 1;
    	} else {
    		leftLen = 0;
    	}
    	if (cur.right != null && cur.right.val == cur.val) {
    		rightLen += 1;
    	} else {
    		rightLen = 0;
    	}
    	//update maxLen
    	maxLen = Math.max(maxLen, leftLen + rightLen);
    	return Math.max(leftLen, rightLen);
    }

    /**
    Basically, if every node can tell how far the path from it can extend on the left and on the right, we are done!
	As part of traversal of the tree, we touch every node so we can keep updating max with that answer at each node.
	Now, what do we mean by extending? Simply compare the values of the current node and its children. Then any path starting at the child can be extended to our node.

	If we cannot extend our path either to left or right, we simply return 0.
	If we can extend only one side, we add 1 to that side path and return to the caller.
	If we can extend to both sides, max will be the total path extended to the left side and right side combined. But to our caller, we just pick the max of the two sides and return.

    */




}