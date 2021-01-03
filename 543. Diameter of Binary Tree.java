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

    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return maxLen;
    }

    private int depth(TreeNode root) {
    	if (root == null) {
    		return 0;
    	}
    	int left = depth(root.left);
    	int right = depth(root.right);
    	maxLen = Math.max(maxLen, left + right);
    	return Math.max(left, right) + 1;
    }
}