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
    public int maxDepth(TreeNode root) {
        // recusive way
        // base case
        if (root == null) {
        	return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}


class Solution {
    // iterative way: BFS 
    public int maxDepth(TreeNode root) {
        int level = 0;
        if (root == null) {return level;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            level += 1;
            while (size > 0) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {queue.add(cur.left);}
                if (cur.right != null) {queue.add(cur.right);}
                size--;
            }            
        }
        return level;
    }
}