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
	// time O(n) space O(n)
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leaf1 = getLeaf(root1);
        List<Integer> leaf2 = getLeaf(root2);
        /*
        if (leaf1.size() != leaf2.size()) {return false;}
        for (int i = 0; i < leaf1.size(); i++) {
        	if (leaf1.get(i) != leaf2.get(i)) return false;
        }
        return true;*/
        return leaf1.equals(leaf2);
    }

    private List<Integer> getLeaf(TreeNode root) {
    	// base case
    	List<Integer> res = new LinkedList<>();
    	if (root == null) {return res;}
    	if (root.left == null && root.right == null) {res.add(root.val);}
    	res.addAll(getLeaf(root.left));
    	res.addAll(getLeaf(root.right));
    	return res;
    }
}




// another way: one leaf node from each tree and compare them one after the other.
// https://leetcode.com/problems/leaf-similar-trees/discuss/152329/C%2B%2BJavaPython-O(H)-Space