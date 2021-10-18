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
    public ListNode oddEvenList(ListNode head) {
        // base case
        if (head == null) {return head;}
        ListNode oddPre = head;
        ListNode evenLast = head.next;

        while (evenLast != null && evenLast.next != null) {
        	ListNode oddNext = evenLast.next;
        	// adjust connection
        	evenLast.next = oddNext.next;
        	oddNext.next = oddPre.next;
        	oddPre.next = oddNext;
        	// move pointers
        	oddPre = oddNext;
        	evenLast = evenLast.next;
        }
        return head;
    }
}



// Phase3 self
class Solution {
    public ListNode oddEvenList(ListNode head) {
        // Iterative way
        if (head == null || head.next == null) {return head;}
        ListNode dummyeven = new ListNode(-1, head.next);
        ListNode prevodd = head;
        ListNode curodd = head;
        ListNode cureven = head.next;
        while (curodd != null && cureven != null) {
            curodd.next = cureven.next;
            prevodd = curodd;
            curodd = curodd.next;
            if (curodd == null) {break;}
            cureven.next = curodd.next;
            cureven = cureven.next;
        }
        if (curodd != null) {prevodd = curodd;}
        prevodd.next = dummyeven.next;
        return head;
    }
}