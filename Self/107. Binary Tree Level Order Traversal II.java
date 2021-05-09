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
	// M1: phase2 self
	// normal BFS but add in reverse order
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
        	int size = queue.size();
        	List<Integer> levelRes = new LinkedList<>();
        	while (size > 0) {
        		TreeNode cur = queue.poll();
        		levelRes.add(cur.val);
        		if (cur.left != null) {queue.add(cur.left);}
        		if (cur.right != null) {queue.add(cur.right);}
        		size--;
        	}
        	res.add(0, levelRes);
        }
        return res;
    }
}





class Solution {
	// M2: normal dfs levelorder way with reverse adding order
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        addLevel(root, res, 0);
        return res;
    }

    private void addLevel(TreeNode root, List<List<Integer>> res, int level) {
    	if (root == null) {return;}
    	if (level >= res.size()) {
    		res.add(0, new LinkedList<Integer>());
    	}
    	res.get(res.size() - 1 - level).add(root.val);
    	addLevel(root.left, res, level + 1);
    	addLevel(root.right, res, level + 1);
    }
}

































