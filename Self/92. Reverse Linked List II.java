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
// below M2 is similar to solution M2, easier for me to understand

// Phase3 self
/* initial thought
find the position of firstTail before the reversed range and the start of the reverse node, do reverse inplace until prev reaches the end of the reverse range. change firstTail.next.next = cur, firstTail.next = prev.
need to setup a dummy in front, return dummy.next in the end.

*/
//M2
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1, head);
        ListNode firstTail = dummy;
        int step = left;
        while(step-- > 1) {firstTail=firstTail.next;}
        ListNode cur = firstTail.next;
        ListNode prev = null;
        while(right-- > left - 1) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        firstTail.next.next = cur;
        firstTail.next = prev;
        return dummy.next;
    }
}