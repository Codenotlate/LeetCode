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
    public void reorderList(ListNode head) {
        // time O(n) space O(1)
        // reverse the second half first, and then combine first half and second half one by one
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = reverse(slow.next);
        ListNode p1 = head;
        ListNode p2 = slow.next;
        while (p2 != null) {
            // pay attention to this end special case
            ListNode p1_nxt = p1.next == slow.next ? null : p1.next;
            ListNode p2_nxt = p2.next;
            p1.next = p2;
            p2.next = p1_nxt;
            p1 = p1_nxt;
            p2 = p2_nxt;
        }
        // remember to adjust
        if (p1 != null) {p1.next = null;}
    }
    
    private ListNode reverse(ListNode head) {
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