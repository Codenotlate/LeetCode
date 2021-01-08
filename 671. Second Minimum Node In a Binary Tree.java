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
    public int findSecondMinimumValue(TreeNode root) {
        // dfs: recursive way
        // base case
        if (root == null || (root.left == null && root.right == null)) {return -1;}

        int leftVal = root.val == root.left.val? findSecondMinimumValue(root.left) : root.left.val;
        int rightVal = root.val == root.right.val? findSecondMinimumValue(root.right) : root.right.val;

        // summarize for 4 conditions: rightVal/leftVal ==/!= -1
        if (leftVal != -1 && rightVal != -1) {return Math.min(leftVal, rightVal);}
        if (leftVal != -1) return leftVal;
        return rightVal;

    }
}


class Solution {
    public int findSecondMinimumValue(TreeNode root) {
        // BFS: iterative way using queue (as fast as dfs above)
        // base case
        if (root == null || (root.left == null && root.right == null)) {return -1;}

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        Integer secondMax = null; // null can't be convert to int
        while (!q.isEmpty()) {
        	TreeNode n = q.poll();
        	if (n.val != root.val) {
        		if (secondMax == null) {secondMax = n.val;}
        		else if (secondMax > n.val) {return n.val;}
        	} else {
        		if (n.left != null) {q.offer(n.left);}
        		if (n.right != null) {q.offer(n.right);}
        	}
        }
        return secondMax == null? -1 : secondMax;

    }
}











