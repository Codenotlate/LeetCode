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
	private int val = -1;
	private int count = 0;
    public int kthSmallest(TreeNode root, int k) {
        // recursive inorder traversal
        if (root == null) {return -1;}
        helper(root, k);
        return val;
    }

    private void helper(TreeNode root, int k) {
    	if (root == null) {return;}
    	helper(root.left, k);
    	count += 1;
    	if (count == k) {
    		val = root.val;
    		return;
    	}
    	helper(root.right, k);
    }
}


class Solution {
    public int kthSmallest(TreeNode root, int k) {
        // iterative inorder traversal
        // when node is visited, count += 1 
        // and when count==k, meaning after visited top node, we have k values.
        if (root == null) {return -1;}
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode last_pop = root;

        while (!stack.isEmpty()) {
        	TreeNode top = stack.peek();
        	if (top.left != null && top.left != last_pop && top.right != last_pop) {
        		stack.push(top.left);
        	} else if (top.right != null && top.right != last_pop 
        		&& (top.left == null || top.left == last_pop)) {
        		stack.push(top.right);
        		count += 1;
        		if (count == k) {return top.val;}
        	} else {
        		stack.pop();
        		last_pop = top;
        		if (top.right == null) {
        			count += 1;
        			if (count == k) {return top.val;}
        		}
        	}
        }
        return -1;
    } 
}






// possible followup discussion
//(https://leetcode.com/problems/kth-smallest-element-in-a-bst/discuss/63743/Java-divide-and-conquer-solution-considering-augmenting-tree-structure-for-the-follow-up)











