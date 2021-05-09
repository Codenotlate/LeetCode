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
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
       //base case
    	if (t1 == null) {return t2;}
    	if (t2 == null) {return t1;}

    	int val = t1.val + t2.val;
    	TreeNode left = mergeTrees(t1.left, t2.left);
    	TreeNode right = mergeTrees(t1.right, t2.right);
    	return new TreeNode(val, left, right);
    }
}