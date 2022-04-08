/*
two ways of adjusting trees
M1: simply connect the whole left subtree to be the left child of rightmost node in right subtree.
M2: replace original node with leftmost node in right subtree, keep the height change minimum.
similar idea as morris
time O(H) space O(1)
*/





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



// Phase3 self
// Difference with above M1 is:
// In M1, once we find the leftmost node, (assume target is the left child of its parent)we remove all target.leftsubtree as the left subtree of leftmost node, then parent.left = target.right.
// In below method, we move leftmost node to replace the target, and adjust the right subtree of leftmost to connect to leftmost's parent. This way the height change of the BST is minimum.
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        // step 1 find node with key and its parent
        TreeNode parent = null;
        TreeNode target = root;
        while(target != null) {
            if (target.val == key) {break;}
            parent = target;
            if (target.val < key) {target = target.right;}
            else{target = target.left;}            
        }
        if (target == null) {return root;}
        
        // find the leftmost node in the right subtree of target
        TreeNode leftmost = target.right;
        TreeNode leftmostParent = target;
        // if no right subtree
        if (leftmost == null) {
            if (parent == null) {return target.left;} // meaning target is the root itself
            if (parent.left == target) {parent.left = target.left;}
            else {parent.right = target.left;}
            return root;
        } 
        // has right subtree
        while(leftmost.left != null) {
            leftmostParent = leftmost;
            leftmost = leftmost.left; 
        }
        
        if(leftmostParent.left == leftmost) {
            leftmostParent.left = leftmost.right;
        } else {
            leftmostParent.right = leftmost.right;
        }
        if(parent != null) {
            if (parent.left == target) {parent.left = leftmost;}
            else {parent.right = leftmost;}
        }
        leftmost.left = target.left;
        leftmost.right = target.right;
        return parent == null ? leftmost : root;
   }
}



// Review
/*Thought
First use the BST attributes to find the node and also track its parent node (time O(height of target)). If turen null (can't find), retur current root directly. If find the target node with val = key. We will find its next node in inorder traverse. There are two cases:
case1: target node has right child. Then find the leftmost node of its right subtree. Along the way we also track the parent of that leftmost node. For the deletion, we update the target node val with the val of the leftmost node val. And leftMostParent.left/right = leftMost.right.(based on parent.val & leftmost.val). The right case is when leftmostparent is target node itself). return root.
case2: target node doesn't have right child. Then if it's root, retur root.left. Otherwise, targetparent.left/right = target.left(based on targetparent.val && target.val)
time O(height) space O(1)
*/

// similar to above M2: make the height change minimum
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode target = root;
        TreeNode targetParent = null;
        while(target != null && target.val != key) {
            targetParent = target;
            if (target.val < key) {target = target.right;}
            else {target = target.left;}
        }
        if (target == null) {return root;}
        if (target.right == null) {
            if (target == root) {return root.left;}
            if (targetParent.val < target.val) {targetParent.right = target.left;}
            else {targetParent.left = target.left;}
        } else {
            TreeNode leftMostParent = target;
            TreeNode leftMost = target.right;
            while(leftMost.left != null) {leftMostParent = leftMost; leftMost = leftMost.left;}           
            if (leftMostParent.val < leftMost.val) {leftMostParent.right = leftMost.right;}
            else {leftMostParent.left = leftMost.right;}
            target.val = leftMost.val; // node change the value agfter above comparison
        }
        return root;
    }
}