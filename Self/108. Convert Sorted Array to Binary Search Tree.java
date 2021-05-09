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
    public TreeNode sortedArrayToBST(int[] nums) {
        int len = nums.length;
        if (len == 0) {return null;}
        return helper(nums, 0, len - 1);
    }

    private TreeNode helper(int[] nums, int start, int end) {
    	if (start > end) {return null;}

    	int mid = (end - start) / 2 + start; // use this to avoid overflow
    	TreeNode root = new TreeNode(nums[mid]);
    	root.left = helper(nums, start, mid - 1);
    	root.right = helper(nums, mid + 1, end);
    	return root;
    }
}



class Solution {   
    // iterative way: 
    /**
    imagine we alrealy have a balanced tree structure but each node val is now empty, 
    now we need to traverse each node in the tree and update its val based on nums[].
    In 108, we choose to use preorder(by contrast, we need to use inorder in 109 for linkedlist.)
    all types of traversals can be used here, since we decide the val in nums using index.
    which index to choose in num is determined by start and end,
    index mid = (start + end) / 2, 
    the start and end num will be updated based on its parent node's start and end nums.
    building a class to pair TreeNode with its start and end num.
    using a stack to implement dfs iteratively.
    */
    private class Node {
    	TreeNode node;
    	int start, end;
    	public Node(TreeNode n, int s, int e) {
    		node = n;
    		start = s;
    		end = e;
    	}
    }

    public TreeNode sortedArrayToBST(int[] nums) {   
        int len = nums.length;
        if (len == 0) {return null;}

        Stack<Node> stack = new Stack<>();
        TreeNode root = new TreeNode(0);
        stack.push(new Node(root, 0, len - 1));
        while (!stack.isEmpty()) {
        	Node n = stack.pop();
        	int mid = (n.end - n.start) / 2 + n.start;
        	n.node.val = nums[mid];

        	// now have updated the val of n, 
        	// then push n.node.left and n.node.right into stack
        	if (n.start < mid) { // same as start>end check in recursive way
        		n.node.left = new TreeNode(0);
        		stack.push(new Node(n.node.left, n.start, mid - 1));
        	}
        	if (n.end > mid) { // same as start>end check in recursive way
        		n.node.right = new TreeNode(0);
        		stack.push(new Node(n.node.right, mid + 1, n.end));
        	}
        }
        return root;
    }
}







