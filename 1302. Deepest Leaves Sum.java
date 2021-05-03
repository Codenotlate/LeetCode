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
	// M1: self phase2: BFS using queue
    public int deepestLeavesSum(TreeNode root) {
        int sum = 0;
        if (root == null) {return sum;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
        	int size = queue.size();
        	sum = 0;
        	while (size > 0) {
        		TreeNode cur = queue.poll();
        		sum += cur.val;
        		if (cur.left != null) {queue.add(cur.left);}
        		if (cur.right != null) {queue.add(cur.right);}
        		size--;
        	}
        }
        return sum;
    }
}




class Solution {
	// M2: recursive dfs way
    public int deepestLeavesSum(TreeNode root) {
        int[] curSum = new int[1];
        int[] maxLevel = new int[1];
        deepestHelper(root, 0, maxLevel, curSum);
        return curSum[0];
    }

    private void deepestHelper(TreeNode root, int curLevel, int[] maxLevel, int[] curSum) {
    	if (root == null) {return;}
    	if (curLevel == maxLevel[0]) {
    		curSum[0] += root.val;
    	} else if (curLevel > maxLevel[0]) {
    		maxLevel[0] = curLevel;
    		curSum[0] = root.val;
    	}

    	deepestHelper(root.left, curLevel + 1, maxLevel, curSum);
    	deepestHelper(root.right, curLevel + 1, maxLevel, curSum);
    }
}
















