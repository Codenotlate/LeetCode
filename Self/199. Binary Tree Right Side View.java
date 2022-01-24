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


// Review self
// solution compare BFS and DFS:
// https://leetcode.com/problems/binary-tree-right-side-view/solution/
// M1:BFS
class Solution {
    // initial idea is to do BFS and record the last node of each level, or add BFS node in reverse order and return the first node of each level
    // time O(n) space O(D) - tree diameter
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            res.add(queue.peek().val);
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if(cur.right!= null) {queue.add(cur.right);}
                if(cur.left!= null) {queue.add(cur.left);}
            }
        }
        return res;
    }
}

class Solution {
    // can also think about dfs way: root, right, left. Only add to res when the level of the dfs == current res size
    // time O(n) space O(h)
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        dfs(res, root, 0);
        return res;
    }
    
    private void dfs(List<Integer> res, TreeNode root, int level) {
        if (root == null) {return;}
        if (level == res.size()) {res.add(root.val);}
        dfs(res, root.right, level+1);
        dfs(res, root.left, level+1);
        
    }
}