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
    public int minDepth(TreeNode root) {
        // recursive way: dfs. (should be slower than BFS)
        if  (root == null) {return 0;}
        if (root.left == null) {return 1 + minDepth(root.right);}
        if (root.right == null) {return 1 + minDepth(root.left);}
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }
}




class Solution {
    public int minDepth(TreeNode root) {
        // iterative way: BFS using queue
    	if (root == null) {return 0;}
    	int count = 0;
    	Queue<TreeNode> queue = new LinkedList<>();
    	queue.offer(root);
    	while (!queue.isEmpty()) {
    		count += 1;
    		// for BFS, need to poll out all nodes in the same level,
    		// otherwise the count would be wrong.
    		int k = queue.size();
    		while (k > 0) {
    			TreeNode n = queue.poll();
	    		if (n.left == null && n.right == null) {return count;}
	    		if (n.left != null) {queue.offer(n.left);}
	    		if (n.right != null) {queue.offer(n.right);}
	    		k -= 1;
    		}  		
    	}
    	return count;
    }
}




class Solution {
    // Phase2 M2 self: øiterative way using BFS
    public int minDepth(TreeNode root) {
        int level = 0;
        if (root == null) {return level;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            level += 1;
            while (size > 0) {
                TreeNode cur = queue.poll();
                if (cur.left == null && cur.right == null) {return level;}
                if (cur.left != null) {queue.add(cur.left);}
                if (cur.right != null) {queue.add(cur.right);}
                size--;
            }
        }
        return level;
    }
}





























