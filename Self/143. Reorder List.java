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


// Phase3 self: close idea to M1
class Solution {
    /* initial thought
    find firsthead, firsttail, secondhead; reverse the secondhead till tail
    insert the second half into the first half of the list
    time O(n) space O(1) if use iterative reverse, O(n) is use recursive reverse
    */
    // try iterative reverse
    public void reorderList(ListNode head) {
        if (head.next == null || head.next.next == null) {return;}
        ListNode firsttail = head;
        ListNode secondhead = head;
        while(secondhead.next != null && secondhead.next.next!= null) {
            firsttail = firsttail.next;
            secondhead = secondhead.next.next;
        }
        secondhead = firsttail.next;
        firsttail.next = null;
        secondhead = reverse(secondhead); // reverse iteratively
        // combine first and second half
        ListNode firsthead = head;
        while(firsthead != null) {
            ListNode firstnext = firsthead.next;
            firsthead.next = secondhead;
            if (secondhead == null) {break;}
            ListNode secondnext = secondhead.next;
            secondhead.next = firstnext;
            secondhead = secondnext;
            firsthead = firstnext;
        }        
    }
    
    // inplace reverse
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}