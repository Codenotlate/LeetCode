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
    public TreeNode invertTree(TreeNode root) {
        // base case
        if (root == null) {
        	return root;
        }
        TreeNode leftInvert = invertTree(root.left);
        TreeNode rightInvert = invertTree(root.right);
        root.left = rightInvert;
        root.right = leftInvert;
        return root;
    }
}