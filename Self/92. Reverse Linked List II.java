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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // one pass way
        if (head == null || head.next == null) {return head;}
        ListNode dummy = new ListNode(-1, head);
        ListNode former = dummy;
        int i = -1;
        while ( i < left - 2) {
            former = former.next;
            i++;
        }
        // now former points to the node in left position
        i += 2;
        ListNode prev = former.next;
        ListNode cur = prev.next;
        while(i <= right - 1) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
            i++;
        }
        former.next.next = cur;
        former.next = prev;
        return dummy.next;
        
        
    }
}


// a more concise way in solution M2
// https://leetcode.com/problems/reverse-linked-list-ii/solution/