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
    // recursive way

   	public boolean isSymmetric(TreeNode root) {
        if (root == null) {return true;}
        return isSymPair(root.left, root.right);

    }
    private boolean isSymPair(TreeNode t, TreeNode s) {
    	if (t == null && s == null) {return true;}
    	if (t == null || s == null) {return false;}
        // if (p1 == null || p2 == null) {return p1 == p2;}

    	if (t.val != s.val) {return false;}
    	return isSymPair(t.left, s.right) && isSymPair(t.right, s.left);
	}	
}


class Solution {
    // iterative way using a stack

   	public boolean isSymmetric(TreeNode root) {
        if (root == null) {return true;}
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root.left);
        stack.push(root.right);

        while (!stack.isEmpty()) {
        	TreeNode n1 = stack.pop();
        	TreeNode n2 = stack.pop();
        	if (n1 == null && n2 == null) {continue;}
        	if (n1 == null || n2 == null || n1.val != n2.val) {
        		return false;
        	}
        	stack.push(n1.left);
        	stack.push(n2.right);
        	stack.push(n1.right);
        	stack.push(n2.left);
        }
        return true;

    }
    	
}


// iterative way phase2 self using queue
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {return true;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            TreeNode first = queue.poll();
            TreeNode second = queue.poll();
            if (first == null || second == null) {
                if (first != second) {return false;}
            } else {
                if (first.val != second.val) {return false;}
                queue.add(first.left);
                queue.add(second.right);
                queue.add(first.right);
                queue.add(second.left);
            }
        }
        return true;
    }
}



























