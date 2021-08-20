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

// phase3 self
// similar to 124, it's the type of problem when the recursion serves two functions, return boolean value and record count along the way
class Solution {
    public int countUnivalSubtrees(TreeNode root) {
        int[] count = new int[]{0};
        isUnitree(root, count);
        return count[0];
    }
    
    private boolean isUnitree(TreeNode root, int[] count) {
        if (root == null) {return true;}
        boolean l1 = root.left == null? true : root.val == root.left.val;
        boolean l2 = isUnitree(root.left, count);
        boolean r1 = root.right == null? true : root.val == root.right.val;
        boolean r2 = isUnitree(root.right, count);
        boolean isValid = l1 && l2 && r1 && r2;
        if (isValid) {count[0] += 1;}
        return isValid;
    }
}