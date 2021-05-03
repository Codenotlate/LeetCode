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

// M1: recursive way
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {return p == q;}
        return (p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
    }
}


// M2: iterative way
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
    	if (p == null || q == null) {return p == q;}
    	Queue<TreeNode> queue = new LinkedList<>();
    	queue.add(p);
    	queue.add(q);
    	while (!queue.isEmpty()) {
    		TreeNode first = queue.poll();
    		TreeNode second = queue.poll();
    		if (first == null || second == null) {
    			if (first != second) {return false;}
    		} else {
    			if (first.val != second.val) {return false;}
    			queue.add(first.left);
    			queue.add(second.left);
    			queue.add(first.right);
    			queue.add(second.right);
    		}
    	}
    	return true;
    }
}