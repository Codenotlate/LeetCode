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
    public ListNode reverseList(ListNode head) {
        // Recursive way
        // base case
        if (head == null || head.next == null) {
        	return head;
        }
        ListNode newHead = reverseList(head.next);
        ListNode newTail = head.next;
        newTail.next = head;
        head.next = null;
        return newHead;
    }
}

class Solution {
    public ListNode reverseList(ListNode head) {
        // Iterative way
        if (head == null || head.next == null) {
        	return head;
        }

        ListNode newHead = null;
        ListNode cur = head;
        ListNode nextNode = cur.next;

        while (nextNode != null) {
        	cur.next = newHead;
        	newHead = cur;
        	cur = nextNode;
        	nextNode = nextNode.next;
        }
        cur.next = newHead;
        return cur;
    }
}