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
    public List<Integer> postorderTraversal(TreeNode root) {
        //recursive way
        List<Integer> resList = new LinkedList<>();
        if (root == null) return resList;

        resList.addAll(postorderTraversal(root.left));
        resList.addAll(postorderTraversal(root.right));
        resList.add(root.val);
        return resList;
    }
}




/**
There is an universal idea for preorder traversal inorder traversal and postorder traversal. 
For each node N, we process it as follows:-------
push N in stack -> push left tree of N in stack -> pop left tree of N 
-> push right tree of N in stack -> pop right tree of N -> pop N
---------
For preorder traversal, we visit a node when pushing it in stack. 
For inorder traversal, we visit a node when pushing its right child in stack. 
For postorder traversal, we visit a node when popping it. 

last_pop represents the node which is popped the last time. 

For the top node in stack, it has three choices, 
pushing its left child in stack, or pushing its right child in stack, or being popped. 
If last_pop != top->left, meaning that its left tree has not been pushed in stack, 
then we push its left child. 
If last_pop == top->left, we push its right child. 
Otherwise, we pop the top node.

Top comment in:
(https://leetcode.com/problems/binary-tree-postorder-traversal/discuss/45559/My-Accepted-code-with-explaination.-Does-anyone-have-a-better-idea)
*/

class Solution { 
    public List<Integer> postorderTraversal(TreeNode root) {
        // iterative way: a general one fits for preorder/inorder/postorder
        // last_pop and the conditions are all same for 3 types
        // only diff is when the node is visited, represents by adding to resList in this problem
        
        List<Integer> resList = new LinkedList<>();
        if (root == null) return resList;

        Stack<TreeNode> stack = new Stack<>();
		// use last_pop to check the last element being popped
		// and can use it to determine push left/ push right / pop
        TreeNode last_pop = root; 
        stack.push(root);
        while(!stack.isEmpty()) {
        	TreeNode top = stack.peek();
        	// condition to push left
        	if (top.left != null && top.left != last_pop && top.right != last_pop) {
        		stack.push(top.left);
        	} else if (top.right != null && top.right != last_pop 
        				&& (top.left == null || top.left == last_pop)) {
        				// condition to push right
        		stack.push(top.right);
        	} else {// pop out
        		stack.pop();
        		last_pop = top;
        		// for postorder, the node is visited when it's popped out.
        		resList.add(top.val);
        	}       	
        }
        return resList;
    }
}





class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        //iterative way: do preorder in root -> right -> left order
        // a bit slower than recursive way
        // and then reverse the resList
        List<Integer> resList = new LinkedList<>();
        if (root == null) return resList;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
        	TreeNode n = stack.pop();
        	// put left first
        	if (n.left != null) {stack.push(n.left);}
        	if (n.right != null) {stack.push(n.right);}
        	resList.add(n.val);
        	// if use resList.addfirst(n.val), then no need to reverse below
        	// for linkedlist, addfirst is O(1).
        }
        Collections.reverse(resList);
        return resList;
    }
}

/**
Someone in the comments argues:
This reverse algorithm is not general. Because the order of the node visited is actully in preorder.
If instead of asking for a list, we ask to just "print" the number. You can't print something before already printed number.
*/

/**
But I think since for postorder you need to visit parent node before children node no matter what.
It's acceptable that you put them into a collections first and then reverse it.
*/












