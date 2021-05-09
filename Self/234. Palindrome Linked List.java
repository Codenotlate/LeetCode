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
    public boolean isPalindrome(ListNode head) {
        // time O(n), space O(1) , reverse the second half and then compare withe the first half
        ListNode slow = head;
        ListNode fast = head;
        ListNode halfHead = null;

        while (fast != null && fast.next != null) {
        	slow = slow.next;
        	fast = fast.next.next;
        }


        // determine based on odd/even
        if (fast == null) { // meaning it's even
        	halfHead = reverse(slow);
        } else if (fast.next == null) { // meaning it's odd
        	halfHead = reverse(slow.next);
        }

        while (halfHead != null) {
        	if (halfHead.val != head.val) {
        		return false;
        	}
        	halfHead = halfHead.next;
        	head = head.next;
        }
        return true;

    }

    private ListNode reverse(ListNode l) {
    	// Iterative way using 3 pointers
    	ListNode preHead = null;
    	while (l != null) {
    		ListNode next = l.next;
    		l.next = preHead;
    		preHead = l;
    		l = next;
    	}
    	return preHead;
    }




}