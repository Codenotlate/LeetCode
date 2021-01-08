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
    // slow, time is exponential because of the overlapping in add1 and add2
    public int rob(TreeNode root) {
        // base case
        if (root == null) {return 0;}

        // if root is selected
        int add1 = root.val;
        if (root.left != null) {
        	add1 += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
        	add1 += rob(root.right.left) + rob(root.right.right);
        }
        // if root is not selected
        int add2 = rob(root.left) + rob(root.right);
        return Math.max(add1, add2);

     }
}


class Solution {
    // Dynamic programming way
    // Use HashMap to store the result of visited nodes
    public int rob(TreeNode root) {
    	if (root == null) {return 0;}
    	return robSub(root, new HashMap<>());
    }

    private int robSub(TreeNode root, HashMap<TreeNode, Integer> map) {
    	if (root == null) {return 0;}
    	if (map.containsKey(root)) {return map.get(root);}

    	// for new node not contained in map, do similar as method 1
    	int add1 = root.val;
        if (root.left != null) {
        	add1 += robSub(root.left.left, map) + robSub(root.left.right, map);
        }
        if (root.right != null) {
        	add1 += robSub(root.right.left, map) + robSub(root.right.right, map);
        }
        // if root is not selected
        int add2 = robSub(root.left, map) + robSub(root.right, map);
        int res = Math.max(add1, add2);

        // put the new node result into map
        map.put(root, res);
        return res;
    }
}


class Solution {
	// further improvement
	// redefine robSub to return a int[] storing the info of two cases separately
	// res[0] for max value if root is robbed
	// res[1] for max value if root is not robbed
    public int rob(TreeNode root) {
        if (root == null) {return 0;}
        int[] res = robSub(root);
        return Math.max(res[0], res[1]);
    }

    private int[] robSub(TreeNode root) {
    	if (root == null) {return new int[2];}
    	int[] leftRes = robSub(root.left);
    	int[] rightRes = robSub(root.right);
    	int[] res = new int[2];

    	res[0] = root.val + leftRes[1] + rightRes[1];
    	res[1] = Math.max(leftRes[0], leftRes[1]) + Math.max(rightRes[0], rightRes[1]);
    	return res;
    }
}


// amazing explanation: 
// https://leetcode.com/problems/house-robber-iii/discuss/79330/step-by-step-tackling-of-the-problem































