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




// Review
/*Initial thought
1. If can use O(n) space, then it's simple, just do inorder traverse of the tree and store all the result in an array.
2. To achieve O(h) space, we can consider only store the path from root to leftmost node and add into a stack.
For hasNext, simply check whether stack is empty.
For next, pop out the stack, and if that node has right node, add the path from that right node till the leftmost node into the stack.
In this way, time O(1) on average for each node, space O(h)
3. similar to 2, but just use the regular inorder iterative traverse, and separate it into each step.

*/
// M2
class BSTIterator {
    Stack<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        while(root != null) {stack.add(root); root = root.left;}
    }
    
    public int next() {
        TreeNode pop = stack.pop();
        TreeNode next = pop.right;
        while(next != null) {stack.add(next); next = next.left;}
        return pop.val;
    }
    
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
// M3
class BSTIterator {
    Stack<TreeNode> stack;
    TreeNode cur;

    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        cur = root;
    }
    
    public int next() {
        while(cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        TreeNode pop = stack.pop();
        cur = pop.right;
        return pop.val;
    }
    
    public boolean hasNext() {
        return !stack.isEmpty() || cur != null;
    }
}





