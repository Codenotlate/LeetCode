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
    public ListNode partition(ListNode head, int x) {
        // Phase3 self
        // time O(n) space O(1)
        if (head == null || head.next == null) {return head;}
        ListNode shead = new ListNode(-1);
        ListNode lhead = new ListNode(-1);
        ListNode s = shead;
        ListNode l = lhead;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val < x) {
                s.next = cur;
                s = s.next;
            } else {
                l.next = cur;
                l = l.next;
            }
            cur = cur.next;
        }
        s.next = lhead.next;
        l.next = null;
        return shead.next;
    }
}