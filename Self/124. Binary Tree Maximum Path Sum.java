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
// a good explain in https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/603423/Python-Recursion-stack-thinking-process-diagram


// self review
class Solution {
    public int maxPathSum(TreeNode root) {
        // use maxTwoSides to record the highest twoSides path sum along the way, this value can't be used by its parent node, but will need to track it too. 
        int[] maxTwoSides = new int[]{-1001};
        int[] sum = getBothSum(root, maxTwoSides);       
        return Math.max(maxTwoSides[0],Math.max(sum[0], sum[1]));
    }
    
    
    private int[] getBothSum(TreeNode root, int[] maxTwoSides) {
        // pay attention to not initialize to zero, that won't handle the negative numebr case
        if (root == null) {return new int[]{-1001, -1001};}
        
        int[] resLeft = getBothSum(root.left, maxTwoSides);
        int[] resRight = getBothSum(root.right, maxTwoSides);
        
        int incVal = Math.max(Math.max(root.val + resLeft[0], root.val + resRight[0]), root.val);
        int excVal = Math.max(Math.max(resLeft[0], resLeft[1]), Math.max(resRight[0], resRight[1]));
        // update the two sides path sum
        maxTwoSides[0] = Math.max(maxTwoSides[0], root.val + resLeft[0] + resRight[0]);
        
        return new int[]{incVal, excVal};
    }
}

/*
The self review method can be optimized to the above M1. The basic idea is we separate the functions of the recursion into two parts:
1st part: update the maxPathSum with cur node across the two sides for each node
2nd part: pass the max value of PathSum(including cur node) of left single side or right single side

In our self review method, there's actually no need to keep track of excVal. Because if the max sum is produced by excVal, it must be recorded by maxTwoSides[0] in the lower level node recursions when that not is the root node of that recursion round.
*/















