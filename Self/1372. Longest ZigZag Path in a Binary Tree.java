/*Thought
Using DFS/Recursion. The recursion of each node provides two result, the path length using its left subtree, and the length using its right subtree. Each node's result is based on the left child node's right result and right child node's left result. Update the max path length along the way, which is the final result. (stored as the third result in res[])
time O(n)
space O(n) = O(height)
*/
class Solution {
    public int longestZigZag(TreeNode root) {
        int[] res = dfsHelper(root);
        return res[2];
        
    }
    
    private int[] dfsHelper(TreeNode root) {
        int[] res = new int[3];
        if (root.left != null) {
            int[] resleft = dfsHelper(root.left);
            res[0] = 1 + resleft[1];
            res[2] = Math.max(resleft[2], res[2]);
        }
        if (root.right != null) {
            int[] resright = dfsHelper(root.right);
            res[1] = 1 + resright[0];
            res[2] = Math.max(resright[2], res[2]);
        }
        res[2] = Math.max(res[2],Math.max(res[0], res[1]));
        return res;
    }
}

// same idea, more concise code
// https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/discuss/531867/JavaPython-DFS-Solution