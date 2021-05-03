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
	// M1 : inorder using stack iteratively
	// space O(n) time O(n)
    public void recoverTree(TreeNode root) {
        TreeNode prev = null;
        TreeNode first = null;
        TreeNode last = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
        	while (cur != null) {
        		stack.push(cur);
        		cur = cur.left;
        	}
        	cur = stack.pop();
        	if (prev != null && prev.val > cur.val) {
        		if (first == null) {first = prev;}
        		last = cur;
        	}
        	prev = cur;
        	cur = cur.right;
        }
        if (first != null && last != null) {
        	int temp = first.val;
        	first.val = last.val;
        	last.val = temp;
        }
    }
}


// M2: Morris inorder way
// time O(n) space O(1)
class Solution {
    public void recoverTree(TreeNode root) {
        TreeNode cur = root;
        TreeNode first = null;
        TreeNode last = null;
        TreeNode prev = null;
        while (cur != null) {
        	if (cur.left == null) {
        		// visit cur
        		if (prev != null && prev.val > cur.val) {
		    		if (first == null) {first = prev;}
		    		last = cur;
		    	}
		    	prev = cur;
        		cur = cur.right;
        	} else {
        		TreeNode rightMost = cur.left;
        		while (rightMost.right != null && rightMost.right != cur) {
        			rightMost = rightMost.right;
        		}
        		if (rightMost.right == null) {
        			rightMost.right = cur;
        			cur = cur.left;
        		} else {
        			rightMost.right = null;
        			// visit cur
        			if (prev != null && prev.val > cur.val) {
			    		if (first == null) {first = prev;}
			    		last = cur;
			    	}
			    	prev = cur;
        			cur = cur.right;
        		}
        	}
        }
        swapVal(first, last);
    }


    private void swapVal(TreeNode first, TreeNode last) {
    	if (first != null && last != null) {
    		int temp = first.val;
    		first.val = last.val;
    		last.val = temp;
    	}
    }
}

























