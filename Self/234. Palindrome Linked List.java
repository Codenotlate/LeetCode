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


// Phase3 self
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        ListNode p1 = head;
        ListNode p2 = reverse(slow.next);
        while (p2 != null) {
            if (p1.val != p2.val) {
                reverse(slow.next);
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        reverse(slow.next);
        return true;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nxt;
        }
        return prev;
    }
}