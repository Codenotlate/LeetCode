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
    public boolean hasPathSum(TreeNode root, int sum) {
        // base case: [], sum=0 -> false
        if (root == null) {
        	return false;
        }
        if (root.left == null && root.right == null) {
        	return root.val == sum;
        }
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);

    }
}

class Solution {
    // try iterative way, build a new class, record TreeNode as well as value sum starting from root(not include current node val)
    private class Node {
        public TreeNode tNode;
        public int sum;
        Node(TreeNode t, int sum) {
            tNode = t;
            this.sum = sum;
        }
    }
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {return false;}
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(root, 0));
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            if (cur.tNode.left == null && cur.tNode.right == null && cur.sum + cur.tNode.val == targetSum) {return true;}
            if (cur.tNode.left != null) {stack.add(new Node(cur.tNode.left, cur.sum + cur.tNode.val));}
            if (cur.tNode.right != null) {stack.add(new Node(cur.tNode.right,cur.sum + cur.tNode.val));}
        }
        return false;
    }
}

// can also do iterative way using two stacks, one store nodes, one store sum.
// https://leetcode.com/problems/path-sum/discuss/36534/My-java-no-recursive-method
// comment1 and comment2
