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
	// M1: recursivw way: post-order
	// time O(n) space O(height)
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        // base case
       if (root == null) {return null;}
       root.left = removeLeafNodes(root.left, target);
       root.right = removeLeafNodes(root.right, target);
       if (root.left == null && root.right == null && root.val == target) {
       	return null;
       }
       return root;
    }
}


class Solution {
	// M2: iterative postorder way (use double push to do post order)
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {return null;}
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode cur = stack.pop();
        	if (!stack.isEmpty() && cur == stack.peek()) {
        		if (cur.left != null) {
        			stack.push(cur.left);
        			stack.push(cur.left);
        		}
        		if (cur.right != null) {
        			stack.push(cur.right);
        			stack.push(cur.right);
        		}
        	} else {
        		// these steps are important, deal with it using parent node
        		if (cur.left != null && cur.left.val < 0) {cur.left = null;}
        		if (cur.right != null && cur.right.val < 0) {cur.right = null;}
        		if (cur.left == null && cur.right == null && cur.val == target) {
        			cur.val = -1;
        			// can't set cur = null directly, but we can set the val to be negative and deal with it using parent node
        		}
        	}
        }
        return root.val < 0 ? null : root;
    }
}

















