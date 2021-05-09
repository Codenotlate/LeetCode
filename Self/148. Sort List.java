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
class Solution {
	//self: O(1) space bottom-up merge sort
    public ListNode sortList(ListNode head) {
        if (head == null) {return null;}
        int interval = 1;
        int n = size(head);

        ListNode newHead = new ListNode(-1, head);
        ListNode l1 = head;
        while (interval <= n) {
        	ListNode newTail = newHead;
        	while (l1 != null) {
        		ListNode l2 = split(l1, interval);
	        	ListNode l3 = split(l2, interval);
	        	// since head might change after each merge, we choose to append to tail and keep a dummy head
	        	newTail = mergeAndConnect(l1, l2, newTail);
	        	l1 = l3;
        	}
        	interval *= 2;
        	l1 = newHead.next; // reset l1 to the head of the sorted list after iterate all for one interval
        }
        return newHead.next;
    }

    // return the size of the list
    private int size(ListNode head) {
    	int count = 0;
    	while (head != null) {
    		count++;
    		head = head.next;
    	}
    	return count;
    }

    // split the list to head with interval # of nodes, 
    // set the end of the split part to null and return the next node after the split part
    private ListNode split(ListNode head, int interval) {
    	ListNode p = head;
    	while (interval > 1 && p != null) {
    		p = p.next;
    		interval--;
    	}
    	if (p == null) {
    		return null;
    	} else {
    		ListNode next = p.next;
    		p.next = null;
    		return next;
    	}
    }


    // merge sorted l1 and l2 and append to newTail of previous sorted part
    // return the newTail of the sorted list
    private ListNode mergeAndConnect(ListNode l1, ListNode l2, ListNode newTail) {
    	while (l1 != null && l2 != null) {
    		if (l1.val < l2.val) {
    			newTail.next = l1;
    			l1 = l1.next;
    		} else {
    			newTail.next = l2;
    			l2 = l2.next;
    		}
    		newTail = newTail.next;
    	}
    	newTail.next = l1 == null ? l2 : l1;
    	while (newTail.next != null) {
    		newTail = newTail.next;
    	}
    	return newTail;
    }
}




// almost same idea, slightly concise way
// https://leetcode.com/problems/sort-list/discuss/46712/Bottom-to-up(not-recurring)-with-o(1)-space-complextity-and-o(nlgn)-time-complextity


// can also try top-down with space O(logn) next time















