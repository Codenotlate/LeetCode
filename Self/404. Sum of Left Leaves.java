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
    public int sumOfLeftLeaves(TreeNode root) {
        // recursive way
        if (root == null) {return 0;}
        int curLeft = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {
        	curLeft = root.left.val;
        }
        return curLeft + sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
    }
}


class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        // iterative  way using stack
        if (root == null) {return 0;}
        int res = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode n = stack.pop();
        	// when n.left != null and isleaf, we add the n.left.val into res
        	// we push n.left to stack when it's not null and not leaf
        	if (n.left != null) {
        		if (n.left.left == null && n.left.right == null) {
        			res += n.left.val;
        		} else {
        			stack.push(n.left);
        		}
        	}
        	// we push n.right to stack only when it's not null and not isleaf
        	if (n.right != null && (n.right.left != null || n.right.right != null)) {
        		stack.push(n.right);
        	}
        }
        return res;
    }
}



















