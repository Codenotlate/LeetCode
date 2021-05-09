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
// M1: greedy way, bottom-up, if has leaf node, it's always better to place the camera at its parent node.
// remove the covered nodes(view as null), then repeat step 1
// three status: 0 -> leaf node; 1 -> place camera; 2-> covered no camera
// time O(n) space O(H)
class Solution {
	static enum Type {
		LEAF, CAMCOVERED, NOCAMCOVERED;
	}
	// class var to record res;
	private int res = 0; 

    public int minCameraCover(TreeNode root) {
        return dfs(root) == Type.LEAF ? res + 1 : res;
    }

    Type dfs(TreeNode root) {
    	if (root == null) {return Type.NOCAMCOVERED;}
    	Type left = dfs(root.left);
    	Type right = dfs(root.right);
    	if (left == Type.LEAF || right == Type.LEAF) {
    		res++;
    		return Type.CAMCOVERED;
    	} else if (left == Type.CAMCOVERED || right == Type.CAMCOVERED) {
    		return Type.NOCAMCOVERED;
    	} else {
    		return Type.LEAF;
    	}
    }
}


// M2: DP way, return a int[3] array, int[i] represent the count to reach status[i].
/* 3 status: 
0 : cur node uncovered, all other nodes in subtree covered
1 : cur node covered and placed a camera, all other nodes in subtree covered
2 : cur node covered without a camera, all other nodes in subtree covered
cur state 0 <-- both children state2 
cur state 1 <-- both children state 0/1/2
cur state 2 <-- (left child state1 and right child state1/2) or (right child state1 and left child state1/2)

*/
class Solution {
    public int minCameraCover(TreeNode root) {
        int[] res = dp(root);
        return Math.min(res[1], res[2]);
    }

    private int[] dp(TreeNode root) {
    	// 99999 meaning null node impossible to get state 1
    	if (root == null) {return new int[]{0, 99999, 0};}
    	int[] left = dp(root.left);
    	int[] right = dp(root.right);
    	// min number of cameras to have subtree of left/right all covered
    	int minLeft = Math.min(left[1], left[2]);
    	int minRight = Math.min(right[1], right[2]);
    	int dp0 = left[2] + right[2];
    	int dp1 = 1 + Math.min(left[0], minLeft) + Math.min(right[0], minRight);
    	int dp2 = Math.min(left[1] + minRight, right[1] + minLeft);
    	return new int[]{dp0, dp1, dp2};
    }
}


// https://leetcode.com/problems/binary-tree-cameras/discuss/211290/Java-DFS-%2B-DP-5ms-Solution-with-Explanation
// https://leetcode.com/problems/binary-tree-cameras/discuss/211180/JavaC%2B%2BPython-Greedy-DFS








