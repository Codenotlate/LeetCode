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


// Phase3 self

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resHead = new ListNode(-1, null);
        ListNode res = resHead;
        ListNode p1 = l1;
        ListNode p2= l2;
        int add = 0;
        while (p1 != null || p2 != null) {
            int val1 = p1 == null? 0 : p1.val;
            int val2 = p2 == null? 0 : p2.val;
            int sum = val1 + val2 + add;
            add = sum /10;
            res.next = new ListNode(sum % 10, null);
            res = res.next;
            p1 = p1 == null? p1 : p1.next;
            p2 = p2 == null? p2 : p2.next;      
        }
        if (add != 0) {
            res.next = new ListNode(add, null);
        }
        return resHead.next;
    }
}