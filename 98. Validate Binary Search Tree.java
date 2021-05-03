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
	// recursive way: postorder
	// time O(n) space O(H)
    public boolean isValidBST(TreeNode root) {
    	// use Integer.MIN and MAX won't work for edge case with val = MIN and MAX
    	return isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isBST(TreeNode root, long min, long max) {
    	if (root == null) {return true;}
        if (root.val <= min || root.val >= max) {return false;}
        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }
}

// https://leetcode.com/problems/validate-binary-search-tree/discuss/32109/My-simple-Java-solution-in-3-lines
// another way to deal with MIN/MAX edge case, use Integer and null
class Solution {
	// recursive way: postorder
	// time O(n) space O(H)
    public boolean isValidBST(TreeNode root) {
    	// use Integer.MIN and MAX won't work for edge case with val = MIN and MAX
    	return isBST(root, null, null);
    }

    private boolean isBST(TreeNode root, Integer min, Integer max) {
    	if (root == null) {return true;}
        if ((min != null && root.val <= min) || (max != null && root.val >= max)) {return false;}
        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }
}


// M2: iterative way (inorder traverse, valid BST should be in order)
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {return true;}
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        Integer lastVal = null;
        while (cur != null || !stack.isEmpty()) {
        	while (cur != null) {
        		stack.push(cur);
        		cur = cur.left;
        	}
        	cur = stack.pop();
        	if (lastVal != null && cur.val <= lastVal) {return false;}
        	lastVal = cur.val;
        	cur = cur.right;
        }
        return true;
    }
}

// inorder iterative way for other problems, also how to deal with duplicates in comment
// https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution)



















