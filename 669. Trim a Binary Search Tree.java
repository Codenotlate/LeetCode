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
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // base case
        if (root == null) {return null;}

        if (root.val <= high && root.val >= low) {
        	root.left = trimBST(root.left, low, high);
        	root.right = trimBST(root.right, low, high);
        } else if (root.val < low) {
        	root = trimBST(root.right, low, high);
        } else {
        	root = trimBST(root.left, low, high);
        }
        return root;
    }
}


class Solution {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // try iterative way
        // find a valid root node first
        while (root != null && (root.val < low || root.val > high)) {
        	if (root.val < low) {root = root.right;}
        	if (root.val > high) {root = root.left;}
        }
        if (root == null) {return null;}

        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        while (!s.isEmpty()) {
        	TreeNode top = s.peek();
        	boolean childValid = true;
        	if (top.left != null && top.left.val < low) {
        		top.left = top.left.right;
        		childValid = false;
        	}
        	if (top.right != null && top.right.val > high) {
        		top.right = top.right.left;
        		childValid = false;
        	}
        	if (childValid) {
        		// pop out only when child is valid, 
        		// because in this case, there's no need to track their parent
        		s.pop();
        		// only push valid node into stack 
        		if (top.left != null) {s.push(top.left);}
        		if (top.right != null) {s.push(top.right);}
        	}
        }
        return root;
    }
}





