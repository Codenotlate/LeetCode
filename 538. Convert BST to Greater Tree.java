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
	// use class var to track cumsum
	// can also use a helper function to pass the cumsum, 
	// and no need for class var
	private int cumsum = 0;

    public TreeNode convertBST(TreeNode root) {
        // recursive way: reverse in-order
        if (root == null) {return null;}
        reverseInOrder(root);
        return root;
    }

    private void reverseInOrder(TreeNode root) {
    	if (root == null) {return;}
    	reverseInOrder(root.right);
    	root.val += cumsum;
    	cumsum = root.val;
    	reverseInOrder(root.left);
    }
}


class Solution {
    public TreeNode convertBST(TreeNode root) {
        // iterative way: reverse in-order using stack
        // use method specific for inorder, not the overall framework for 3 traversals
    	int cumsum = 0;

    	Stack<TreeNode> stack = new Stack<>();
    	TreeNode next = root;
    	while (next != null || !stack.isEmpty()) {
    		if (next != null) {
    			stack.push(next);
    			// reverse inorder
    			next = next.right;
    		} else {
    			next = stack.pop();
    			// do add value and update cumsum in root level
    			next.val += cumsum;
    			cumsum = next.val;
    			// after visiting root, go to its left
    			next = next.left;
    		}
    	}
    	return root;
        
    }

    
}

// Morris traversal method left (explained in solution page)
// (https://leetcode.com/problems/convert-bst-to-greater-tree/discuss/100516/Java-Three-O(n)-Methods%3A-Recursive-Iterative-and-Morris-Traversal)























