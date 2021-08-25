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
    // recursive way phase2 self without global var
    public int kthSmallest(TreeNode root, int k) {
        int[] val = new int[1];
        int[] count = new int[1];
        inorder(root, k, val, count);
        return val[0];
    }
    
    private void inorder(TreeNode root, int k, int[] val, int[] count) {
        if (root == null) {return;}
        inorder(root.left, k, val, count);
        count[0] += 1;
        if (k == count[0]) {
            val[0] = root.val; 
            return;
        }
        inorder(root.right, k, val, count);
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


class Solution {
    // iterative way phase2 self
    public int kthSmallest(TreeNode root, int k) {
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while(cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            count += 1;
            if (count == k) {return cur.val;}
            cur = cur.right;            
        }
        return -1;
    }
}




// possible followup discussion
//(https://leetcode.com/problems/kth-smallest-element-in-a-bst/discuss/63743/Java-divide-and-conquer-solution-considering-augmenting-tree-structure-for-the-follow-up)
// followup and time space detail see solution page

// time of iterative way is O(H + k)
/*
O(H) to reach the smallest node in the BST.
O(k) to find the kth smallest from there doing an inorder search.
*/










