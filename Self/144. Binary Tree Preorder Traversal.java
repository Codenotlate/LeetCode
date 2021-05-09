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
    public List<Integer> preorderTraversal(TreeNode root) {
        // recursive way: trivial
        List<Integer> resList = new ArrayList<>();
        if (root == null) {return resList;}
        resList.add(root.val);
        resList.addAll(preorderTraversal(root.left));
        resList.addAll(preorderTraversal(root.right));
        return resList;
    }
}


class Solution {
	// another recursive way, this way we don't need to new a list for every recursive call
	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> pre = new LinkedList<Integer>();
		preHelper(root,pre);
		return pre;
	}
	public void preHelper(TreeNode root, List<Integer> pre) {
		if(root==null) return;
		pre.add(root.val);
		preHelper(root.left,pre);
		preHelper(root.right,pre);
	}
}


class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        // iterative way using stack
        List<Integer> resList = new ArrayList<>();
        if (root == null) {return resList;}

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode n = stack.pop();
        	resList.add(n.val);
        	if (n.right != null) {stack.push(n.right);}
        	if (n.left != null) {stack.push(n.left);}
        }
        return resList;
    }
}

class Solution { 
    public List<Integer> postorderTraversal(TreeNode root) {
        //iterative way: a general one fits for preorder/inorder/postorder
        // last_pop and the conditions are all same for 3 types
        // only diff is when the node is visited, represents by adding to resList in this problem
        
        List<Integer> resList = new LinkedList<>();
        if (root == null) return resList;

        Stack<TreeNode> stack = new Stack<>();
		// use last_pop to check the last element being popped
		// and can use it to determine push left/ push right / pop
        TreeNode last_pop = root; 
        stack.push(root);
        // for preorder, the node is visited when it's pushed into stack.
		resList.add(root.val);
        while(!stack.isEmpty()) {
        	TreeNode top = stack.peek();
        	// condition to push left
        	if (top.left != null && top.left != last_pop && top.right != last_pop) {
        		stack.push(top.left);
        		// for preorder, the node is visited when it's pushed into stack.
        		resList.add(top.left.val);
        	} else if (top.right != null && top.right != last_pop 
        				&& (top.left == null || top.left == last_pop)) {
        				// condition to push right
        		stack.push(top.right);
        		// for preorder, the node is visited when it's pushed into stack.
        		resList.add(top.right.val);
        	} else {// pop out
        		stack.pop();
        		last_pop = top;
        		
        	}       	
        }
        return resList;
    }
}







