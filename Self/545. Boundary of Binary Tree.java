/*Thought from discussion, mainly two ways
M1: Do three parts left boundary, right boudary and leaves separately. Each part using recursive way. time O(n) space O(n)
https://leetcode.com/problems/boundary-of-binary-tree/discuss/101280/Java(12ms)-left-boundary-left-leaves-right-leaves-right-boundary

M2: Do it in one preorder traverse, but provide leftBound, rightBound info in the recursive call, to help decide the current node adding case and also determine that info for its children nodes. time O(n) space O(n)
https://leetcode.com/problems/boundary-of-binary-tree/discuss/101288/Java-Recursive-Solution-Beats-94

*/
// M1: 3 separate parts
class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        res.add(root.val);
        addleft(root.left, res);
        addleave(root.left, res);
        addleave(root.right, res);
        addright(root.right, res);
        return res;
    }
    
    private void addleft(TreeNode root, List<Integer> res) {
        if (root == null || (root.left == null && root.right == null)) {return;}
        res.add(root.val);
        if (root.left == null) {addleft(root.right, res);}
        else {addleft(root.left, res);}
    }
    
    private void addleave(TreeNode root, List<Integer> res) {
        if (root == null) {return;}
        if (root.left == null && root.right == null) {
            res.add(root.val);
            return;
        }
        addleave(root.left, res);
        addleave(root.right, res);
    }
    
    private void addright(TreeNode root, List<Integer> res) {
        if (root == null || (root.left == null && root.right == null)) {return;}
        if (root.right == null) {addright(root.left, res);}
        else {addright(root.right, res);}
        res.add(root.val);
    }
}
// M2
class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        res.add(root.val);
        addNode(root.left, res, true, false);
        addNode(root.right, res, false, true);
        return res;
    }
    
    private void addNode(TreeNode root, List<Integer> res, boolean isLeft, boolean isRight) {
        if (root == null) {return;}
        if (root.left == null && root.right == null) {res.add(root.val); return;}
        if (isLeft) {res.add(root.val);}
        addNode(root.left, res, root.left != null && isLeft, root.right == null && isRight);
        addNode(root.right, res, root.left == null && isLeft, root.right != null && isRight);
        if(isRight) {res.add(root.val);}
        
    }
}























/* Wrong trying on BFS
Thought
BFS, we want the first and last node at each level, for the final level, we want every node. Given the final position in the result, can have separate stacks to store first nodes and last nodes. This also requires us to use dfs to find the final level of the tree first.
Time O(n)  space O(n)

bug fix: for some level with only one node, need to find out whether added to first or last. Thus we need to put rightchild or leftchild info into the stack as well. -1 for left and 1 for right.

wrong for example: [1,2,7,3,5,null,6,4]


class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        int maxlevel = getLevel(root);
        List<Integer> first = new LinkedList<>();
        Stack<Integer> last = new Stack<>();
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(root,-1));
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Pair<TreeNode, Integer> pair = queue.poll();
                TreeNode cur = pair.getKey();
                Integer dir = pair.getValue();
                if (level == maxlevel || (i == 0 && dir <0)) {first.add(cur.val);}
                else if (i == size - 1) {last.push(cur.val);}
                if (cur.left != null) {queue.add(new Pair(cur.left,-1));}
                if (cur.right != null) {queue.add(new Pair(cur.right, 1));}
            }
            level++;
        }
        while(!last.isEmpty()) {first.add(last.pop());}
        return first;
    }
    
    private int getLevel(TreeNode root) {
        if (root ==null) {return -1;}
        return 1 + Math.max(getLevel(root.left), getLevel(root.right));
    }
}


*/