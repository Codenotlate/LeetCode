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
	private int maxLen = 0;

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {return 0;}
        // dfs returns the longest length of single side path 
        // that has the same value as root.val
        dfs(root);
        return maxLen;
    }

    private int dfs(TreeNode cur) {
    	if (cur == null) {return 0;}
    	int leftLen = dfs(cur.left);
    	int rightLen = dfs(cur.right);
    	if (cur.left != null && cur.left.val == cur.val) {
    		leftLen += 1;
    	} else {
    		leftLen = 0;
    	}
    	if (cur.right != null && cur.right.val == cur.val) {
    		rightLen += 1;
    	} else {
    		rightLen = 0;
    	}
    	//update maxLen
    	maxLen = Math.max(maxLen, leftLen + rightLen);
    	return Math.max(leftLen, rightLen);
    }

    /**
    Basically, if every node can tell how far the path from it can extend on the left and on the right, we are done!
	As part of traversal of the tree, we touch every node so we can keep updating max with that answer at each node.
	Now, what do we mean by extending? Simply compare the values of the current node and its children. Then any path starting at the child can be extended to our node.

	If we cannot extend our path either to left or right, we simply return 0.
	If we can extend only one side, we add 1 to that side path and return to the caller.
	If we can extend to both sides, max will be the total path extended to the left side and right side combined. But to our caller, we just pick the max of the two sides and return.

    */
}



// Phase 2 self:
// recursive way
class Solution {
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {return 0;}
        int[] maxLen = new int[1];
        oneSidePath(root, maxLen);
        return maxLen[0];
    }
    
    private int oneSidePath(TreeNode root, int[] maxLen) {
        if (root == null) {return 0;}
        int left = oneSidePath(root.left, maxLen);
        int right = oneSidePath(root.right, maxLen);
        // set left / right to 0 if val not same as root
        if (root.left != null && root.left.val != root.val) {
            left = 0;
        }
        if (root.right != null && root.right.val != root.val) {
            right = 0;
        }
        maxLen[0] = Math.max(maxLen[0], left + right);
        return Math.max(left, right) + 1;
    }
}


// phase 2: self : iterative way postorder, use map to store treeNode and one side univalue path length
class Solution {
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {return 0;}
        Stack<TreeNode> stack = new Stack<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        stack.push(root);
        int maxLen = 0;
        while(!stack.isEmpty()) {
            TreeNode cur = stack.peek();
            if (cur.left != null && !map.keySet().contains(cur.left)) {
                stack.push(cur.left);
            } else if (cur.right != null && !map.keySet().contains(cur.right)) {
                stack.push(cur.right);
            } else {
                cur = stack.pop();
                int left = map.getOrDefault(cur.left, 0);
                if (cur.left != null && cur.left.val != cur.val) {
                    left = 0;
                }
                int right = map.getOrDefault(cur.right, 0);
                if (cur.right != null && cur.right.val != cur.val) {
                    right = 0;
                }
                maxLen = Math.max(maxLen, left + right);
                map.put(cur, Math.max(left, right) + 1);
            }
        }
        return maxLen;
    }
}





























