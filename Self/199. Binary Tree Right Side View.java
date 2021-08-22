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


// self DFS
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        dfs(root, res, 0);
        return res;
    }
    
    private void dfs(TreeNode root, List<Integer> res, int level) {
        if (root == null) {return;}
        if (level >= res.size()) {
            res.add(root.val); 
        } else {
            res.set(level, root.val);
        }
        dfs(root.left, res, level + 1);
        dfs(root.right, res, level + 1);
    }
}

// self BFS
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while(size > 0) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {queue.add(cur.left);}
                if (cur.right != null) {queue.add(cur.right);}
                if (size == 1) {res.add(cur.val);}
                size--;
            }
        }
        return res;
        
    }
}