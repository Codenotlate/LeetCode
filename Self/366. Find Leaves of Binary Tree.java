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


// with the help of solution
// actually just find the height of each node and add the value into res to index = height
// again the getHeight function serves two functions, record the height of children as well as add number to the res list
// time O(n) space O(n)
class Solution {
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        getHeight(root, res);
        return res;
    }
    
    private int getHeight(TreeNode root, List<List<Integer>> res) {
        if (root == null) {return 0;}
        int leftHeight = getHeight(root.left, res);
        int rightHeight = getHeight(root.right, res);
        int height = 1 + Math.max(leftHeight, rightHeight);
        if (height - 1 >= res.size()) {
            res.add(new LinkedList<>());
        }
        res.get(height - 1).add(root.val);
        return height;
    }
}