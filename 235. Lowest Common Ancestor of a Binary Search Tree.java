/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // recursive way： use BST to cut search range
        // time: O(n), space: O(n)
        if (p.val > root.val && q.val > root.val) {return lowestCommonAncestor(root.right, p, q);}
        if (p.val < root.val && q.val < root.val) {return lowestCommonAncestor(root.left, p, q);}
        return root;

    }
}



class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // iterative way： walk down until p and q are not in same subtree, return that cur node
        // time: O(n), space: O(1)
        // worth mention: (p.val - root.val) * (q.val - root.val) may cause overflow
        // could do (root.val - (long)p.val) * (root.val - (long)q.val))
        while ((p.val - root.val) * (q.val - root.val) > 0) {
        	root = p.val > root.val? root.right : root.left;
        }
        return root;

    }
}


// phase2 self
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {return null;}
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur.val < p.val && cur.val < q.val) {
                if (cur.right != null) {stack.push(cur.right);}
            } else if (cur.val > p.val && cur.val > q.val) {
                if (cur.left != null) {stack.push(cur.left);}
            } else {
                return cur;
            }            
        }
        return null;
    }
}