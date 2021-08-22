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

// self review - iterative way
// in fact any preorder way can do it, BFS also works
class Solution {
    // can also do iteratively
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {return null;}
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            TreeNode temp = cur.left;
            cur.left = cur.right;
            cur.right = temp;
            if (cur.right != null) {stack.push(cur.right);}
            if (cur.left != null) {stack.push(cur.left);}
        }
        
        return root;
    }
}