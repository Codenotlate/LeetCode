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
    // recusive way (preorder)
    // time O(n) space O(n)
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {return true;}
        return preOrder(root, root.val);
    }
    
    private boolean preOrder(TreeNode root, int value) {
        if (root == null) {return true;}
        return (root.val == value && preOrder(root.left, value) && preOrder(root.right, value));
    }
}


class Solution {
    // iterative way (try using inorder, only for review purpose)
    // we can actually use any kind of traversal way: preorder with stack, inorder with stack, 
    // postorder with last_pop and stack or double push, levelorder with queue
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {return true;}
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        int univalue = root.val;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (cur.val != univalue) {return false;}
            cur = cur.right;
        }
        return true;
    }
}


























