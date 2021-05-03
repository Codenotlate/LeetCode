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
	// M1 : recursive way
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {return null;}
        if (root.val == val) {return root;}
        if (root.val < val) {return searchBST(root.right, val);}
        return searchBST(root.left, val);
    }
}


class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {return null;}
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode cur = stack.pop();
        	if (cur.val == val) {return cur;}
        	if (cur.left != null) {stack.push(cur.left);}
        	if (cur.right != null) {stack.push(cur.right);}
        }
        return null;
    }
}