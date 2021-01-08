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
    public int findBottomLeftValue(TreeNode root) {
        // BFS way
        if (root == null) {return 0;} // can't return null, since the return type is int
        List<Integer> leftList = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();

        q.offer(root);
        while (!q.isEmpty()) {
        	int levelSize = q.size();
        	for (int i = 0; i < levelSize; i++) {
        		TreeNode n = q.poll();
        		// add the left most value for this level
        		// no need to check other values in thie level
        		if (i == 0) {leftList.add(n.val);}
        		if (n.left != null) {q.offer(n.left);}
        		if (n.right != null) {q.offer(n.right);}
        	}        	
        }
        // now leftList holds all the leftmost val for each level, return the last one
        return leftList.get(leftList.size() - 1);
    }
}

class Solution {
    public int findBottomLeftValue(TreeNode root) {
    	// DFS way
		if (root == null) {return 0;} 
		List<Integer> leftList = new ArrayList<>();
		dfs(root, leftList, 0);
		return leftList.get(leftList.size() - 1); 
    }

    private void dfs(TreeNode root, List<Integer> list, int level) {
    	if (root == null) {return;}
    	if (level == list.size()) {
    		// the start of the new level
    		list.add(root.val);
    	} // else do nothing 

    	dfs(root.left, list, level + 1);
    	dfs(root.right, list, level + 1);
    }
}



class Solution {
    public int findBottomLeftValue(TreeNode root) {
		// BFS way: right to left
		// simply return the last node, cause it's the leftmost one
		// and no need to track levels or the first node for each level.
		if (root == null) {return 0;} 
        Queue<TreeNode> q = new LinkedList<>();

        q.offer(root);
        while (!q.isEmpty()) {
        	root = q.poll();
        	// add right note first
        	if (root.right != null) {q.offer(root.right);}	
    		if (root.left != null) {q.offer(root.left);}
        }        	
        return root.val;
    }
}
































