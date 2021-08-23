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
	// recursive way: postorder
	// time O(n) space O(H)
    public boolean isValidBST(TreeNode root) {
    	// use Integer.MIN and MAX won't work for edge case with val = MIN and MAX
    	return isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isBST(TreeNode root, long min, long max) {
    	if (root == null) {return true;}
        if (root.val <= min || root.val >= max) {return false;}
        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }
}

// https://leetcode.com/problems/validate-binary-search-tree/discuss/32109/My-simple-Java-solution-in-3-lines
// another way to deal with MIN/MAX edge case, use Integer and null
class Solution {
	// recursive way: postorder
	// time O(n) space O(H)
    public boolean isValidBST(TreeNode root) {
    	// use Integer.MIN and MAX won't work for edge case with val = MIN and MAX
    	return isBST(root, null, null);
    }

    private boolean isBST(TreeNode root, Integer min, Integer max) {
    	if (root == null) {return true;}
        if ((min != null && root.val <= min) || (max != null && root.val >= max)) {return false;}
        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }
}


// M2: iterative way (inorder traverse, valid BST should be in order)
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {return true;}
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        Integer lastVal = null;
        while (cur != null || !stack.isEmpty()) {
        	while (cur != null) {
        		stack.push(cur);
        		cur = cur.left;
        	}
        	cur = stack.pop();
        	if (lastVal != null && cur.val <= lastVal) {return false;}
        	lastVal = cur.val;
        	cur = cur.right;
        }
        return true;
    }
}

// inorder iterative way for other problems, also how to deal with duplicates in comment
// https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution)


// self review
class Solution {
    public boolean isValidBST(TreeNode root) {
        long[] res = dfs(root);
        return res[2] == 1;
    }
    
    
    private long[] dfs(TreeNode root) {
        // reminder: to use Long.min and Long.max instead of doing -1 and +1 with int.min and int.max, that will cause overflow error, i.e. int.max + 1 = int.min
        if (root == null) {return new long[]{Long.MAX_VALUE, Long.MIN_VALUE, 1};}
        long[] left = dfs(root.left);
        long[] right = dfs(root.right);
        if (left[2] == 0 || right[2] == 0 || left[1] >= root.val || right[0] <= root.val) {
            return new long[]{0, 0, 0};
        }
        long curMin = left[0] == Long.MAX_VALUE ? root.val : left[0];
        long curMax = right[1] == Long.MIN_VALUE ? root.val : right[1];
        return new long[]{curMin, curMax, 1};
    }
}

















