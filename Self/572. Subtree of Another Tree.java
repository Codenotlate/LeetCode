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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) {return false;}
        return isSame(s, t)|| isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    private boolean isSame(TreeNode s, TreeNode t) {
    	// base case
        if (t == null) {return s == null;}
        if (s == null) {return t == null;}

        if (s.val != t.val) {
        	return false;
        }
        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }
}



/**
If assum m is the number of nodes in the 1st tree and n is the number of nodes in the 2nd tree, then
Time complexity: O(m*n), worst case: for each node in the 1st tree, we need to check if isSame(Node s, Node t). Total m nodes, isSame(...) takes O(n) worst case
Space complexity: O(height of 1str tree)(Or you can say: O(m) for worst case, O(logm) for average case)
*/


// a advanced way with O(S + T) time (don't think we need to know that)
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102741/Python-Straightforward-with-Explanation-(O(ST)-and-O(S%2BT)-approaches)