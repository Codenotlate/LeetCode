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

// Phase 3 self
// traditional inorder way
// time O(n) space O(n)

// can also do morris to make space O(1)

class Solution {
    // record prev popped out node
    // use regular stack for inorder traverse, space O(n)
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prevPop = null;
        while (root != null || !stack.isEmpty()) {
            while(root!= null) {
                stack.add(root);
                root = root.left;
            }
            root = stack.pop();
            if (prevPop == p) {return root;}
            prevPop = root;
            root = root.right;
            
        }
        return null;
    }
}


// since it's a BST, better utilize its characteristic
// time O(h) space O(1)
class Solution {
    // utilize BST
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode res = null;
        while(root != null) {
            if (root.val > p.val) {
                res = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return res;
    }
}













