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
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // base case
        if (root == null) {return null;}

        if (root.val <= high && root.val >= low) {
        	root.left = trimBST(root.left, low, high);
        	root.right = trimBST(root.right, low, high);
        } else if (root.val < low) {
        	root = trimBST(root.right, low, high);
        } else {
        	root = trimBST(root.left, low, high);
        }
        return root;
    }
}


class Solution {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // try iterative way
        // find a valid root node first
        while (root != null && (root.val < low || root.val > high)) {
        	if (root.val < low) {root = root.right;}
        	if (root.val > high) {root = root.left;}
        }
        if (root == null) {return null;}

        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        // only valid nodes are pushed into stack
        while (!s.isEmpty()) {
        	TreeNode top = s.peek();
        	boolean childValid = true;
            // since top is valid, there's no possibility top.left.val > high
            // becase then top.val > high, same idea applies to right side
        	if (top.left != null && top.left.val < low) {
        		top.left = top.left.right;
        		childValid = false;
        	}
        	if (top.right != null && top.right.val > high) {
        		top.right = top.right.left;
        		childValid = false;
        	}
        	if (childValid) {
        		// pop out only when child is valid, 
        		// because in this case, there's no need to track their parent
        		s.pop();
        		// only push valid node into stack 
        		if (top.left != null) {s.push(top.left);}
        		if (top.right != null) {s.push(top.right);}
        	}
        }
        return root;
    }
}



// Phase2 self method: iterative way, first find the valid parent node 
// and push it and its not null children into stack
// by double push(the node and its parent pair), we keep track of its parent in the loop
// we make sure all the parent node in the stack is always valid.
// time O(c * n), constant part is slower then above two methods
class Solution {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // find first valid node
        while (root != null && (root.val < low || root.val > high)) {
            if (root.val < low) {root = root.right;}
            if (root.val > high) {root = root.left;}
        }
        if (root == null) {return null;}
        
        Stack<TreeNode> stack = new Stack<>();
        // push the pair into stack for root.left and its parent node, as well as for root.right
        if (root.left != null) {
            stack.push(root);
            stack.push(root.left);
        }
        if (root.right != null) {
            stack.push(root);
            stack.push(root.right);
        }
        
        // start the loop
        while (!stack.isEmpty()) {
            // pop out node and its parent pair
            TreeNode cur = stack.pop();
            TreeNode curParent = stack.pop();
            if (cur.val < low) {curParent.left = cur.right;}
            else if (cur.val > high) {curParent.right = cur.left;}
            else {curParent = cur;}
            // push the new pair into stack
            if (curParent.left != null) {
                stack.push(curParent);
                stack.push(curParent.left);
            }
            if (curParent.right != null) {
                stack.push(curParent);
                stack.push(curParent.right);
            }            
        }
        return root;
    }
}










