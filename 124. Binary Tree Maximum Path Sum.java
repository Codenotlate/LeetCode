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
    public int maxPathSum(TreeNode root) {
        int[] maxSum = new int[1];
        // remember to initialize to -inf
        maxSum[0] = Integer.MIN_VALUE;
        maxRootPathSum(root, maxSum);
        return maxSum[0];
    }

    private int maxRootPathSum(TreeNode root, int[] maxSum) {
    	if (root == null) {return 0;}
    	// if negative, we use 0 to represent we don't choose any from left/right 
    	int left = Math.max(0, maxRootPathSum(root.left, maxSum));
    	int right = Math.max(0, maxRootPathSum(root.right, maxSum));
    	maxSum[0] = Math.max(maxSum[0], left + right + root.val);
    	return root.val + Math.max(left, right);
    }
}


// https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39775/Accepted-short-solution-in-Java
/*
这题是经典的 "recursion的返回值并不是我们最后要求的值" 的题型。recursion返回的是从下至上包括当前root的单条最大路径（单边subtree）；而我们要求的maxSum的结果是在recursion的过程中不断更新的，也就是跨过当前root包含左子树和右子树的最大路径
Each node actually has two roles when it comes to function maxPathDown. When processing the final result maxValue, the node is treated as the highest point of a path. When calculating its return value, it is only part of a path (left or right part), and this return value will be used to calculate path sum of other paths with some other nodes(above the current one) as their highest point.
*/