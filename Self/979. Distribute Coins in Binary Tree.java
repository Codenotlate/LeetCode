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
	// dfs: bottom-up
	// the tricky part is here we use abs(# of coins - # of nodes) in left / right subtree to calculate the total move number
	// the recursion is again 2-func, update move # and return excess of current node
	// time O(n) space O(H)
    public int distributeCoins(TreeNode root) {
        int[] res = new int[1];
        excess(root, res);
        return res[0];
    }

    private int excess(TreeNode root, int[] res) {
    	if (root == null) {return 0;}
    	int left = excess(root.left, res);
    	int right = excess(root.right, res);
    	res[0] += Math.abs(left) + Math.abs(right);
    	return left + right + root.val - 1;
    }
}



// Phase3 self
class Solution {
    // The key idea is recursion and get 2 results from bottom-up. For root node, we want to know the movement number of both left subtree and rightsubtree. But the recursion on left/right subtree requires there's no excess/deficit of coins in that subtree. Thus we also need to know the excess and deficit condition from both subtrees, and add the extra number the movements needed to make root node has excess = 0 as well as both subtrees' excess == 0.
    // That's why we want two info returned by recursion
    public int distributeCoins(TreeNode root) {
        int[] res = helper(root);
        // res[0] records movement number, res[1] records excess/deficit number of coins, which should be zero for root node.
        return res[0];
    }
    
    
    private int[] helper(TreeNode root) {
        if (root == null) {return new int[]{0, 0};}
        int rootexcess = root.val - 1;
        int[] left = helper(root.left);
        int[] right = helper(root.right);
        int leftmove = left[0];
        int rightmove = right[0];
        int leftexcess = left[1];
        int rightexcess = right[1];
        rootexcess += leftexcess + rightexcess;
        int rootmove = Math.abs(leftexcess) + Math.abs(rightexcess) + leftmove + rightmove;
        return new int[]{rootmove, rootexcess};
    }
}