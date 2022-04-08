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







// Review

/*Thought
View it from a bottom-up way. For each cur node, get the diff = curnode.val - 1(target). And also add leftchild diff and rightchild diff passed up the path. Since their children nod is already with val = 1 and no need for any coin to distribute to them (since we are doing bottomup and added the diff from children in), then abs(diff) is the min amount of move that cur node requires to move to/from its parent node. Thus we can add abs(diff) along the way to the result and pass cur diff to its parent node. Implement it in top-down recursion way based on the tree structure.
time O(n) space O(H)
*/
class Solution {
    public int distributeCoins(TreeNode root) {
        int[] res = new int[1];
        getHelper(root, res);
        return res[0];
    }
    
    private int getHelper(TreeNode root, int[] res) {
        if (root == null) {return 0;}
        int diff = root.val - 1;
        diff += getHelper(root.left, res) + getHelper(root.right, res);
        res[0] += Math.abs(diff);
        return diff;
    }
}