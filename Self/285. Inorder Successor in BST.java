/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// Binary search way time O(h)
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode cur = root;
        TreeNode potential = null;
        while (cur != null) {
            if (cur.val > p.val) {
                potential = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return potential;
    }
}

// skipped the traditional way - which iterates the tree inorder and return the successor.