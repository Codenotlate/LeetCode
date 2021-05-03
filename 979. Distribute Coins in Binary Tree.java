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
	// dfs: bottom-up
	// the tricky part is here we use abs(# of coins - # of nodes) in left / right subtree to calculate the total move number
	// the recursion is again 2-func, update move # and return excess of current node
	// time O(n) space O(H)
    public int distributeCoins(TreeNode root) {
        int[] res = new int[1];
        excess(root, res);
        return res[0];
    }

    private int excess(TreeNode root, int[] res) {
    	if (root == null) {return 0;}
    	int left = excess(root.left, res);
    	int right = excess(root.right, res);
    	res[0] += Math.abs(left) + Math.abs(right);
    	return left + right + root.val - 1;
    }
}