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