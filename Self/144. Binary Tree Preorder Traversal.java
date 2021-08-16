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
    public List<Integer> preorderTraversal(TreeNode root) {
        // recursive way: trivial
        List<Integer> resList = new ArrayList<>();
        if (root == null) {return resList;}
        resList.add(root.val);
        resList.addAll(preorderTraversal(root.left));
        resList.addAll(preorderTraversal(root.right));
        return resList;
    }
}


class Solution {
	// another recursive way, this way we don't need to new a list for every recursive call
	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> pre = new LinkedList<Integer>();
		preHelper(root,pre);
		return pre;
	}
	public void preHelper(TreeNode root, List<Integer> pre) {
		if(root==null) return;
		pre.add(root.val);
		preHelper(root.left,pre);
		preHelper(root.right,pre);
	}
}


class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        // iterative way using stack
        List<Integer> resList = new ArrayList<>();
        if (root == null) {return resList;}

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode n = stack.pop();
        	resList.add(n.val);
        	if (n.right != null) {stack.push(n.right);}
        	if (n.left != null) {stack.push(n.left);}
        }
        return resList;
    }
}


// morris way (transformed from inorder morris way)
class Solution {
    // morris way: same as inorder morris, only difference is print out the cur when it's first visited instead of the second time
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                res.add(cur.val);
                cur = cur.right;
            } else {
                TreeNode prev = cur.left;
                while (prev.right != null && prev.right != cur) {
                    prev = prev.right;
                }
                if (prev.right == null) {
                    res.add(cur.val);
                    prev.right = cur;
                    cur = cur.left;
                } else {
                    prev.right = null;
                    cur = cur.right;
                }
            }
        }
        return res;
    }
}







