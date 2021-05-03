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
	// M1: recursive way
	// time O(h) space O(h)
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {return new TreeNode(val);}
        if (root.val < val) {root.right = insertIntoBST(root.right, val);}
        else {root.left = insertIntoBST(root.left, val);}
        return root;
    }
}


class Solution {
	// M2: iterative way
	// time O(H) space O(1)
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {return new TreeNode(val);}
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode cur = stack.pop();
        	if (cur.val < val) {
        		if (cur.right != null) {
        			stack.push(cur.right);
        		} else {
        			cur.right = new TreeNode(val);
        		}
        	} else {
        		if (cur.left != null) {
        			stack.push(cur.left);
        		} else {
        			cur.left = new TreeNode(val);
        		}
        	}
        }
        return root;
    }
}

// actually no need to use stack, cause stack size won't exceeds 1
// https://leetcode.com/problems/insert-into-a-binary-search-tree/discuss/150757/java-iterative-100