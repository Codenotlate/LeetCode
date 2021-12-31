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
// self first try - optimal answer: next() and hasNext has average O(1) time and O(h) space
class BSTIterator {
    private TreeNode lastPop;
    private Stack<TreeNode> stack;
    
    
    public BSTIterator(TreeNode root) {
        lastPop = new TreeNode(-1);
        lastPop.right = root;
        stack = new Stack<>();
    }
    
    public int next() {
        // don't forget to add lastPop.right if it's not null
        if (stack.isEmpty() || lastPop.right != null) {
            TreeNode p = lastPop.right;
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
        }
        lastPop = stack.pop();
        return lastPop.val;
    }
    
    public boolean hasNext() {
        return (!stack.isEmpty() || lastPop.right != null);
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */


/*
When analyzing amortized time complexities, I find it easiest to reason that each node gets pushed and popped exactly once in next() when iterating over all N nodes.
That comes out to 2N * O(1) over N calls to next(), making it O(1) on average, or O(1) amortized.
*/
// similar idea: https://leetcode.com/problems/binary-search-tree-iterator/discuss/52526/Ideal-Solution-using-Stack-(Java)

// Phase 3 self
// basically just same as plain inorder traverse, but seperate into each step
// time average O(1) space O(h)

class BSTIterator {
    Stack<TreeNode> stack;
    TreeNode cur;

    public BSTIterator(TreeNode root) {
        cur = root;
        stack = new Stack<>();
    }
    
    public int next() {
        while(cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        TreeNode popped = stack.pop();
        cur = popped.right;
        return popped.val;
            
        
    }
    
    public boolean hasNext() {
        return cur!= null || !stack.isEmpty();
    }
}










