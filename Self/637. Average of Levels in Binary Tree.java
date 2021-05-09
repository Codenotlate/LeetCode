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
    public List<Double> averageOfLevels(TreeNode root) {
        // BFS using queue
        // base case (although it's defined non-empty)
        Queue<TreeNode> q = new LinkedList<>();
        List<Double> resList = new ArrayList<>();
        if (root == null) {return resList;}

        q.offer(root);
        while (!q.isEmpty()) {
        	// double type also make sure no overflow for 2-32bit test case
        	double levelSum = 0.0;
        	int levelSize = q.size();
        	for (int i = 0; i < levelSize; i++) {
        		TreeNode n = q.poll();
        		levelSum += n.val;
        		if (n.left != null) {q.offer(n.left);}
        		if (n.right != null) {q.offer(n.right);}
        	}
        	// calculate average for current level and add to resList
        	resList.add(levelSum / levelSize);
        }
        return resList;
    }
}



class Solution {
    // DFS version, using a temp List to store the node representing each level
    // node at position l stores the sum and the count of level l.
    // seems like DFS is a little bit quicker than BFS
    class Node {
        double sum;
        int count;
        Node (double d, int c) {
            sum = d;
            count = c;
        }
    }
    public List<Double> averageOfLevels(TreeNode root) {
        List<Node> temp = new ArrayList<>();
        helper(root, temp, 0);
        List<Double> result = new LinkedList<>();
        for (int i = 0; i < temp.size(); i++) {
            result.add(temp.get(i).sum / temp.get(i).count);
        }
        return result;
    }
    public void helper(TreeNode root, List<Node> temp, int level) {
        if (root == null) return;
        if (level == temp.size()) {
            Node node = new Node((double)root.val, 1);
            temp.add(node);
        } else {
            temp.get(level).sum += root.val;
            temp.get(level).count++;
        }
        helper(root.left, temp, level + 1);
        helper(root.right, temp, level + 1);
    }
    
}
























