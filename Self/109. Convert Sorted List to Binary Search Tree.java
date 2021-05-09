/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
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
class Solution {
	// method1: recursive
	// using fast and slow pointers to find mid every time
	// time O(nlogn), space O(logn)
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {return null;} 
        if (head.next == null) {return new TreeNode(head.val);}

        ListNode preMid = findPreMid(head);
        ListNode mid = preMid.next;
        TreeNode root = new TreeNode(mid.val);
        preMid.next = null;
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(mid.next);
        return root;
    }

    // this function returns the ListNode before mid node
    // we need preMid to set the first half of the linkedlist to end in null
    private ListNode findPreMid(ListNode head) {
    	if (head == null) {return null;}
    	ListNode slow, fast, pre;
    	slow = head;
    	fast = head;
    	pre = head;
    	while (fast.next != null && fast.next.next != null) {
    		pre = slow;
    		slow = slow.next;
    		fast = fast.next.next;
    	}
    	return pre;
    }
}



class Solution {
	// method2: recursive, better
	// similar as 108 recursive, but this time, since it's linkedlist,
	// need to build an inorder tree, cause we can't use index to easily visit the val in linkedlist.
	// we need to visit the tree the same order as the order of val in linekedlist.
	// and add val from linkedlist along the way
	// need to figure out the size of the linkedlist first, and use it to recursively build the inorder tree.
	// using a private var: nextListNode, 
	// also need to move listnode to next listnode after its val has been added to the tree.
    private ListNode nextListNode;

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {return null;}
        // get the size
        ListNode p = head;
        int size = 0;
        while (p != null) {
        	p = p.next;
        	size += 1;
        }

        nextListNode = head;
        return inorderHelper(0, size - 1);
    }

    // building an inorder tree recursively and add value along the way
    // nextListNode points to the next list node whose val will be added to tree next,
    // start, end here controls separating the treenodes into balanced two parts, 
    // making sure the inorder tree built is balanced.
    private TreeNode inorderHelper(int start, int end) {
    	// base case
    	if (start > end) {return null;}
    	int mid = (end - start) / 2 + start;
    	TreeNode left = inorderHelper(start, mid - 1);

    	TreeNode root = new TreeNode(nextListNode.val);
    	nextListNode = nextListNode.next;
    	root.left = left;

    	TreeNode right = inorderHelper(mid + 1, end);
    	root.right = right;

    	return root;
    }
}



















