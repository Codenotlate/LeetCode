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
    public boolean isBalanced(TreeNode root) {
        // worse solution: top-down recursion, O(nlogn)
        // base case
        if (root == null) {return true;}
        // the height calculation here calculates height of each node,
        // which is unnecessary.
        Boolean curLevel = Math.abs(height(root.left) - height(root.right)) <= 1;
        return curLevel && isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode root) {
    	if (root == null) {return 0;}
    	return 1 + Math.max(height(root.left), height(root.right));
    }


}



class Solution {
	// add a class variable
	private boolean result = true;

    public boolean isBalanced(TreeNode root) {
        // best solution: bottom-up recursion, O(n)
    	maxDepth(root);
    	return result;
    }

    private int maxDepth(TreeNode root) {
    	if (root == null) {return 0;}
    	int l = maxDepth(root.left);
    	int r = maxDepth(root.right);

    	if (Math.abs(l - r) > 1) {
    		result = false;
    	}
    	return 1 + Math.max(l, r);
    }


}

/*
FYI for all you suggesting to return once result is set to false to make 
this more efficient, know that this is post-order (bottom-up) traversal, 
meaning by the time you're figuring out if two subtrees are balanced, 
you're on your way back from the recursion stack, not going further into it. 
There is nothing you can do to speed up this implementation once the result 
boolean is falsified.
*/








