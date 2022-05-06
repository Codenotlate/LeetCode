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


// Review
/*Initial thought
DFS, pass curNum to each dfs call. curNum = curNum * 10 + root.val.
time O(n) space O(height). We can do it recursively or using a stack to do it iteratively.
Using Morris to achieve time O(2n) = O(n) space O(1)
*/
// recursive way
class Solution {
    public int sumNumbers(TreeNode root) {
        return sumHelper(root, 0);
    }
    
    private int sumHelper(TreeNode root, int curNum) {
        if (root == null) {return 0;}
        if (root.left == null && root.right == null) {return curNum * 10 + root.val;}
        return sumHelper(root.left, curNum * 10 + root.val) + sumHelper(root.right, curNum*10 + root.val);
    }
}

// iterative way
class Solution {
    public int sumNumbers(TreeNode root) {
        if (root == null) {return 0;}
        int res = 0;
        Stack<Pair<TreeNode,Integer>> stack = new Stack<>();
        stack.push(new Pair(root, 0));
        while(!stack.isEmpty()) {
            Pair<TreeNode, Integer> curPair = stack.pop();
            TreeNode curnode = curPair.getKey();
            int curnum = curPair.getValue();
            if (curnode.left == null && curnode.right == null) {res += curnum * 10 + curnode.val;}
            if (curnode.left != null) {stack.push(new Pair(curnode.left, curnum * 10 + curnode.val));}
            if (curnode.right != null) {stack.push(new Pair(curnode.right, curnum * 10 + curnode.val));}
        }
        return res;       
    }
    
    
}


// morris way preorder traverse, diff with inorder is that we change curnum when visit the node the first time, and we add curnum to res when visit node the second time and predecessor is a leaf node. We also need to track the depth from curnode to the leaf, so that we could backtrack curnum.
class Solution {
    public int sumNumbers(TreeNode root) {
        if (root == null) {return 0;}
        int res = 0;
        TreeNode cur = root;
        int curNum = 0;
        int depth = 0;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode rightmost = cur.left;
                depth = 1;
                while(rightmost.right != null && rightmost.right != cur) {rightmost = rightmost.right; depth++;}
                if (rightmost.right == null) { // first time get to cur,update curnum
                    curNum = 10 * curNum + cur.val;
                    rightmost.right = cur;
                    cur = cur.left;
                } else {
                    // check if predecessor is leaf node, add curnum to res and backtrack curnum
                    if(rightmost.left == null) {res += curNum;}
                    while(depth-- > 0) {curNum /= 10;}
                    rightmost.right = null;
                    cur = cur.right;
                }
            } else {
                curNum = 10 * curNum + cur.val;
                if (cur.right == null) {res += curNum;}
                cur = cur.right;
            }
        }
        return res;       
    }
    
    
}



/* About Morris way in interview:
One of the strategies is to list all possible solutions: recursive, iterative and Morris + time/space complexities. Then tell to the interviewer that to secure the interview you'd prefer first to implement not Morris, and implement recursion / or iteration, with all bases cases and checks.

Now you have 5 min left, long enough to discuss the main idea of Morris implementation, and not enough time to drown in details.

*/






// Review
/*Thought
recursive way DFS. time O(n) space O(height) = O(n)
can also do iterative way use stack,  stack element Pair<node, presum>
*/
class Solution {
    public int sumNumbers(TreeNode root) {
        int[] res = new int[1];
        cal(root, 0, res);
        return res[0];
    }
    
    private void cal(TreeNode root, int prevsum, int[] res) {
        if (root.left == null && root.right == null) {
            res[0] += prevsum * 10 + root.val;
            return;
        }
        prevsum = 10 * prevsum + root.val;
        if (root.left != null) {cal(root.left, prevsum, res);}
        if (root.right != null) {cal(root.right, prevsum, res);}
    }
}


















