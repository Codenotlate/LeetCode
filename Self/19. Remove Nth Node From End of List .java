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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // in two pass
        // base case
        if (head == null || head.next == null) {
        	return null;
        }

        // first pass to get length of the list
        int size = 0;
        ListNode p = head;
        while (p != null) {
        	p = p.next;
        	size += 1;
        }
        // remove the first element
        if (size == n) {
        	return head.next;
        }
        // normal cases
        int i = 1;
        p = head;
        while (i < size - n) {
        	p = p.next;
        	i += 1;
        }
        p.next = p.next.next;
        return head;
    }
}



class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // use two pointers to do it in one pass
        ListNode fast = head;
        while (n-- > 0) {
        	fast = fast.next;
        }
        if (fast == null) { // meaning delete the first node
        	return head.next;
        }
        ListNode slow = head;
        while (fast.next != null) {
        	fast = fast.next;
        	slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }
}



// Phase3 self one pass way
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode( -1, head);
        ListNode prev = dummy;
        ListNode slow = head;
        ListNode fast = head;
        while (n > 0) {
            fast = fast.next;
            n--;
        }
        while (fast!= null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next;
        }
        
        prev.next = slow.next;
        return dummy.next;
    }
}

















