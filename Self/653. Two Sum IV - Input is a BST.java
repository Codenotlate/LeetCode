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
	// method 1: uses fact of BST
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {return false;}

        // use sortedList to store the sorted result from tree inorder traversal
        ArrayList<Integer> sortedList = inorder(root);
        int start = 0;
        int end = sortedList.size() - 1;
        while (start < end) {
        	int sum = sortedList.get(start) + sortedList.get(end);
        	if (sum > k) {end -= 1;}
        	else if (sum < k) {start += 1;}
        	else {return true;}
        }
        return false;



    }

    private ArrayList<Integer> inorder(TreeNode root) {
    	ArrayList<Integer> resList = new ArrayList<>();
    	if (root == null) {return resList;}
    	resList.addAll(inorder(root.left));
    	resList.add(root.val);
    	resList.addAll(inorder(root.right));
    	return resList;
    }
}

class Solution {
	// method 2: uses hashSet to store visited value
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {return false;}

        HashSet<Integer> set = new HashSet<>();
        return findTargetHelper(root, k, set);


    }

    private boolean findTargetHelper(TreeNode root, int k, HashSet set) {
    	if (root == null) {return false;}
    	if (set.contains(k - root.val)) {return true;}
    	set.add(root.val);
    	return findTargetHelper(root.left, k, set) || findTargetHelper(root.right, k, set);
    }
}



// https://leetcode.com/problems/two-sum-iv-input-is-a-bst/solution/



