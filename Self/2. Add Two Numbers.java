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
	// M1: iterative way
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode newHead = new ListNode(-1);
        int carry = 0;
        ListNode cur = newHead;
        while (l1 != null || l2 != null) {
        	int val1 = l1 == null ? 0 : l1.val;
        	int val2 = l2 == null ? 0 : l2.val;
        	int sum = val1 + val2 + carry;
        	cur.next = new ListNode(sum % 10);
        	cur = cur.next;
        	carry = sum / 10;
        	l1 = l1 == null ? l1 : l1.next;
        	l2 = l2 == null ? l2 : l2.next;
        }
        if (carry != 0) {
        	cur.next = new ListNode(carry);
        }
        return newHead.next;
    }
}