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

// phase2 self
// time O(H) space O(1)
// find the key, if not found, return root
// find the leftmost node in keynode's right subtree. In order to keep BST, have leftMost.left to be the original left subtree of keynode.
// and replace keynode position with keynode.right.
// can also do it using left subtree and rightmost node.
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode parent = null;
        TreeNode cur = root;
        // search the key in the tree and track its parent node
        while (cur != null && cur.val != key) {
        	parent = cur;
        	if (cur.val < key) {
        		cur = cur.right;
        	} else {
        		cur = cur.left;
        	}
        }
        // not find key in tree
        if (cur == null) {return root;}
        // otherwise, cur is the node with key
        if (cur.left == null || cur.right == null) {
        	TreeNode res = cur.left == null ? cur.right : cur.left;
        	if (parent == null) { // root is the node with key
        		root = res;
        	} else {
        		if (parent.val > key) {parent.left = res;}
        		else {parent.right = res;}
        	}
        } else { // both children not null
        	// find leftmost node of right subtree of cur
        	TreeNode leftMost = cur.right;
        	while (leftMost.left != null) {
        		leftMost = leftMost.left;
        	}
        	leftMost.left = cur.left;
        	if (parent == null) {root = root.right;} // root is the node with key
        	else {
        		if(parent.val > key) {parent.left = cur.right;}
        		else {parent.right = cur.right;}
        	}
        }
        return root;


    }
}


// Another way to reconnect node
// https://leetcode.com/problems/delete-node-in-a-bst/discuss/93298/Iterative-solution-in-Java-O(h)-time-and-O(1)-space
/*
As for the case when both children of the node to be deleted are not null, I transplant the successor to replace the node to be deleted, which is a bit harder to implement than just transplant the left subtree of the node to the left child of its successor. The former way is used in many text books too. Why? My guess is that transplanting the successor can keep the height of the tree almost unchanged, while transplanting the whole left subtree could increase the height and thus making the tree more unbalanced.
*/