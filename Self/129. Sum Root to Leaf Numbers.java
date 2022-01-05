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
	// M1: dfs + backtracking
	// can use int dfs as well, then no need for int[] res
	// time O(n) space O(height)
    public int sumNumbers(TreeNode root) {
        int[] sum = new int[1];
        dfs(root, sum, 0);
        return sum[0];
    }

    private void dfs(TreeNode root, int[] sum, int pathSum) {
    	// base case
    	if (root == null) {return;}
    	pathSum = pathSum * 10 + root.val;
    	if (root.left == null && root.right == null) {
    		sum[0] += pathSum;
    	}
    	dfs(root.left, sum, pathSum);
    	dfs(root.right, sum, pathSum);
    }
}


// m2: two iterative ways
// 3rd way: can also use pair
// https://leetcode.com/problems/sum-root-to-leaf-numbers/discuss/41474/Java-iterative-and-recursive-solutions.
// use two stacks
class Solution {
    public int sumNumbers(TreeNode root) {
        int sum = 0;
        if (root == null) {return sum;}
        Stack<TreeNode> stackNode = new Stack<>();
        Stack<Integer> stackSum = new Stack<>();
        stackNode.push(root);
        stackSum.push(0);
        while (!stackNode.isEmpty()) {
        	TreeNode cur = stackNode.pop();
        	int pathSum = stackSum.pop();
        	pathSum = pathSum * 10 + cur.val;
        	if (cur.left == null && cur.right == null) {
        		sum += pathSum;
        	}
        	if (cur.left != null) {
        		stackNode.push(cur.left);
        		stackSum.push(pathSum);
        	}
        	if (cur.right != null) {
        		stackNode.push(cur.right);
        		stackSum.push(pathSum);
        	}
        }
        return sum;
    }
}


// use one self class
class Solution {
	private class Node {
		public TreeNode treeNode;
		public int pathSum;

		public Node (TreeNode t, int s) {
			treeNode = t;
			pathSum = s;
		}
	}

    public int sumNumbers(TreeNode root) {
        int sum = 0;
        if (root == null) {return sum;}
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(root, 0));
        while (!stack.isEmpty()) {
        	Node cur = stack.pop();
        	int pathSum = cur.pathSum * 10 + cur.treeNode.val;
        	if (cur.treeNode.left == null && cur.treeNode.right == null) {
        		sum += pathSum;
        	}
        	if (cur.treeNode.left != null) {
        		stack.push(new Node(cur.treeNode.left, pathSum));
        	}
        	if (cur.treeNode.right != null) {
        		stack.push(new Node(cur.treeNode.right, pathSum));
        	}
        }
        return sum;
    }
}



// Phase3 self
class Solution {
    public int sumNumbers(TreeNode root) {
        int[] res = new int[1];
        dfs(root, 0, res);
        return res[0];
    }
    
    private void dfs(TreeNode root, int pathSum, int[] res) {
        if (root == null) {return;}
        pathSum = pathSum * 10 + root.val;
        if (root.left == null && root.right == null) {res[0] += pathSum; return;}
        dfs(root.left, pathSum, res);
        dfs(root.right, pathSum, res);   
    }
}

class Solution {
    // iterative way of implementing dfs using a stack with pair<Node, pathSum. input
    public int sumNumbers(TreeNode root) {
        int res = 0;
        if (root == null) {return res;}
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair(root, root.val));
        
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> curPair = stack.pop();
            TreeNode node = curPair.getKey();
            int pathSum = curPair.getValue();
            if (node.left == null && node.right == null) {res += pathSum;}
            else {
                if (node.left != null) {stack.push(new Pair(node.left, pathSum * 10 + node.left.val));}
                if (node.right != null) {stack.push(new Pair(node.right, pathSum * 10 + node.right.val));}
            }
        }
        return res;
    }
}









