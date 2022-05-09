// time O(n) space O(height)
class Solution {
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {return 0;}
        int res = 0;
        if (root.val < low) {
            res += rangeSumBST(root.right, low, high);
        } else if (root.val > high) {
            res += rangeSumBST(root.left, low, high);
        } else {
            res += root.val + rangeSumBST(root.right, low, high) + rangeSumBST(root.left, low, high);
        }
        return res;
    }
}