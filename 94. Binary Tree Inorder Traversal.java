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
    public List<Integer> inorderTraversal(TreeNode root) {
        // iterative way using the same framework as 144/145
        List<Integer> resList = new LinkedList<>();
        if (root == null) {return resList;}

        Stack<TreeNode> stack = new Stack<>();
        TreeNode last_pop = root;
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode top = stack.peek();
        	if (top.left != null && top.left != last_pop && top.right != last_pop) {
        		// push left node
        		stack.push(top.left);
        	} else if (top.right != null && top.right != last_pop &&
        		(top.left == null || top.left == last_pop)) {
        		// push right node
        		stack.push(top.right);
        		// for inorder, nodes are visited when (1)it's right node is pushed into stack
        		resList.add(top.val);
        	} else {
        		stack.pop();
        		last_pop = top;
        		// for inorder, nodes are visited when (2) it has noe right node and it's being popped out.
        		if (top.right == null) {resList.add(top.val);}
        	}
        }
        return resList;
    }
}



class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        // another iterative way
        // use a next pointer and only push to stack when it's not null

        List<Integer> resList = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode next = root;
        // root node is push into stack within the while loop
        // this also handls root == null case
        while (next != null || !stack.isEmpty()) {
            if (next != null) {
                stack.push(next);
                next = next.left;
            } else {
                // pop out order in the inorder order
                next = stack.pop();
                // do all the stuff for root level here
                resList.add(next.val);
                // only visit right after root is visited(popped)
                next = next.right;
            }
        }
        return resList;
    }
}



class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        // recursive way
        List<Integer> resList = new LinkedList<>();
        if (root == null) {return resList;}

        resList.addAll(inorderTraversal(root.left));
        resList.add(root.val);
        resList.addAll(inorderTraversal(root.right));

        return resList;
    }
}
















