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
	// method1: using recursive
	// time O(nlogn) - O(n^2), space O(n)
    public List<String> binaryTreePaths(TreeNode root) {
    	List<String> res = new LinkedList<>();
    	if (root == null) {return res;}
    	if (root.left != null) {
    		for (String s: binaryTreePaths(root.left)) {
    			res.add(String.valueOf(root.val) + "->" + s);
    		}
    	}
    	if (root.right != null) {
    		for (String s: binaryTreePaths(root.right)) {
    			res.add(String.valueOf(root.val) + "->" + s);
    		}
    	}
    	if (root.left == null && root.right == null) {
    		res.add(String.valueOf(root.val));
    	}
    	return res;
        
    }
}


class Solution {
	// method2: using dfs with backtracking
	// time O(n), space O(n)
    public List<String> binaryTreePaths(TreeNode root) {
    	List<String> res = new LinkedList<>();
    	if (root == null) {return res;}
    	StringBuilder prefix = new StringBuilder();
    	dfs(root, prefix, res);
    	return res;       
    }

    private void dfs(TreeNode node, StringBuilder prefix, List<String> res) {
    	// base case
    	if (node.left == null && node.right == null) {
    		prefix.append(String.valueOf(node.val));
    		res.add(prefix.toString());
    		return;
    	}

    	prefix.append(String.valueOf(node.val)).append("->");
    	int oriLen = prefix.length();
    	if (node.left != null) {
    		dfs(node.left, prefix, res);
    		prefix.delete(oriLen, prefix.length());
    	}
    	if (node.right != null) {
    		dfs(node.right, prefix, res);
    		//prefix.delete(oriLen, prefix.length());
    	}
    }
}



// time complexity discussion and also use StringBuilder instead of string
// https://leetcode.com/problems/binary-tree-paths/discuss/68258/Accepted-Java-simple-solution-in-8-lines


// can also use stack for dfs and queue for bfs
// https://leetcode.com/problems/binary-tree-paths/discuss/68272/Python-solutions-(dfs%2Bstack-bfs%2Bqueue-dfs-recursively).



// phase2 self
class Solution {
    // dfs + backtracking using StringBuilder
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new LinkedList<>();
        if (root == null) {return res;}
        StringBuilder curString = new StringBuilder();
        dfs(root, res, curString);
        return res;
    }
    
    private void dfs(TreeNode root, List<String> res, StringBuilder curString) {
        if (root == null) {return;}
        int oriLen = curString.length();
        curString.append(String.valueOf(root.val));
        if (root.left == null && root.right == null) {
            res.add(curString.toString());
        }
        curString.append("->");
        dfs(root.left, res, curString);
        dfs(root.right, res, curString);
        curString.delete(oriLen, curString.length());
    }
}












