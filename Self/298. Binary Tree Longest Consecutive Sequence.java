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

// M1 self
class Solution {
    public int longestConsecutive(TreeNode root) {
        int[] res = new int[]{0};
        if (root != null) {
             helper(root, root.val, res, 0);
        }
        return res[0];
       
    }
    
    private void helper(TreeNode root, int startNum, int[] res, int curLen) {
        if (root == null) {return;}
        if (root.val == startNum) {
            curLen += 1;
            res[0] = Math.max(res[0], curLen);
            helper(root.left, startNum + 1, res, curLen);
            helper(root.right, startNum + 1, res, curLen);
        } else {
            helper(root, root.val, res, 0);
            /* note this helper above is the same as
            helper(root.left/right, root.val + 1, res, 1);
            thus, can be optimized to below M2
            */

        }
    }
}

// M2 (optimized based on M1)
class Solution {
    public int longestConsecutive(TreeNode root) {
        int[] res = new int[]{0};
        if (root != null) {
             helper(root, root.val, res, 0);
        }
        return res[0];
       
    }
    
    private void helper(TreeNode root, int startNum, int[] res, int curLen) {
        if (root == null) {return;}
        if (root.val == startNum) {
            curLen += 1;
            res[0] = Math.max(res[0], curLen);
        } else {
            curLen = 1;
        }
        helper(root.left, root.val + 1, res, curLen);
        helper(root.right, root.val + 1, res, curLen);
    }
}

// can also use bottom-up way
//https://leetcode.com/problems/binary-tree-longest-consecutive-sequence/solution/