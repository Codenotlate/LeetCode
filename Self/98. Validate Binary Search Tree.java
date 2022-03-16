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

// Phase3 self
class Solution {
    // using recursion, return 3 info: isBst,minVal, maxVal
    public boolean isValidBST(TreeNode root) {
        long[] res = isValidBSTHelper(root);
        return res[0] == 1;
    }
    
    
    private long[] isValidBSTHelper(TreeNode root) {
        if (root == null) {return new long[]{1, Long.MAX_VALUE, Long.MIN_VALUE};}
        long[] left = isValidBSTHelper(root.left);
        long[] right = isValidBSTHelper(root.right);
        int isBST = 0;
        if (left[0]== 1 && right[0]== 1 && root.val > left[2] && root.val < right[1]) {
            isBST = 1;
        }
        long minval = left[1] == Long.MAX_VALUE? root.val : left[1];
        long maxval = right[2] == Long.MIN_VALUE? root.val : right[2];
        
        return new long[]{isBST, minval, maxval};
    }
}



// Review
/*Initial thought
recursive way. boolean recur(node, min, max). - time O(n) space O(n)
inorder way. inorder traverse the tree, return false if cur element smaller than previous one. If we use normal inorder way, space will be O(n). If we use Morris way, also only to track the prev one, then space could be O(1), time O(n). 
*/
// recursive way
class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean isValid(TreeNode root, long min, long max) {
        if (root == null) {return true;}
        boolean rootRes = root.val > min && root.val < max;
        boolean leftRes = isValid(root.left, min, root.val);
        boolean rightRes = isValid(root.right, root.val, max);
        return rootRes && leftRes && rightRes;
    }
}

// Morris way O(1) space: find the rightmost node in left subtree, and make its right pointing to cur node.
// with the best space O(1) in all above methods
// similar idea: https://leetcode.com/problems/validate-binary-search-tree/discuss/1129042/Python-Morris-O(1)-space-approach
class Solution {
    public boolean isValidBST(TreeNode root) {
        long prev = Long.MIN_VALUE;
        TreeNode cur = root;
        while(cur != null) {
            // find rightmost node of left subtree
            if(cur.left != null) {
                TreeNode rightMost = cur.left;
                while(rightMost.right != null && rightMost.right != cur) {rightMost = rightMost.right;}
                if (rightMost.right == null) { // first time
                    rightMost.right = cur;
                    cur = cur.left;
                } else {
                    long curNum = cur.val;
                    if (curNum <= prev) {return false;}
                    prev = curNum;
                    rightMost.right = null;
                    cur = cur.right;
                }
            } else {
                long curNum = cur.val;
                if (curNum <= prev) {return false;}
                prev = curNum;
                cur = cur.right;
            }
        }
        return true;
        
        
    }   
}













