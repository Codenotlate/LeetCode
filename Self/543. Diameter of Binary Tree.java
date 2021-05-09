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

    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return maxLen;
    }

    private int depth(TreeNode root) {
    	if (root == null) {
    		return 0;
    	}
    	int left = depth(root.left);
    	int right = depth(root.right);
    	maxLen = Math.max(maxLen, left + right);
    	return Math.max(left, right) + 1;
    }
}



// self phase 2
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        int[] maxLen = new int[1];
        oneSideLen(root, maxLen);
        return maxLen[0] == 0 ? 0 : maxLen[0] - 1;
    }
    
    private int oneSideLen(TreeNode root, int[] maxLen) {
        if (root == null) {return 0;}
        int left = oneSideLen(root.left, maxLen);
        int right = oneSideLen(root.right, maxLen);
        maxLen[0] = Math.max(maxLen[0], 1 + left + right);
        return Math.max(left, right) + 1;
    }
}



//phase2: iterative way
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {return 0;}
        Stack<TreeNode> stack = new Stack<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        stack.push(root);
        int maxLen = 0;
        while (!stack.isEmpty()) {
            TreeNode cur = stack.peek();
            if (cur.left != null && !map.keySet().contains(cur.left)) {
                stack.push(cur.left);
            } else if (cur.right != null && !map.keySet().contains(cur.right)) {
                stack.push(cur.right);
            } else {
                cur = stack.pop();
                int left = map.getOrDefault(cur.left, 0);
                int right = map.getOrDefault(cur.right, 0);
                maxLen = Math.max(maxLen, left + right);
                map.put(cur, Math.max(left, right) + 1);
            }
        }
        return maxLen;
    }
}















