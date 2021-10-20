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

// Phase3 self
// M1: reverse and add 1 and reverse back
// time O(n), space O(1)
class Solution {
    public ListNode plusOne(ListNode head) {
        ListNode revhead = reverse(head);
        int add = 1;
        ListNode prev = null;
        ListNode cur = revhead;
        while(cur != null) {
            if (cur.val != 9) {
                cur.val += 1;
                add = 0;
                break;
            }
            cur.val = 0;
            prev = cur;
            cur = cur.next;
        }
        if (add != 0) {
            prev.next = new ListNode(1, null);
        }
        return reverse(revhead);
    }
    
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {return head;}
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}


// another way without reverse show in the solution
// https://leetcode.com/problems/plus-one-linked-list/solution/
// basically using a dummy node, find the mostright node not equal to 9 and add 1 to that val, set remaining 9 to 0. return dummy node if the val is set to be 1.
// this way, it's one pass. time still O(n)
class Solution {
    public ListNode plusOne(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode not9 = dummy;
        while (head != null) {
            if (head.val != 9) {not9 = head;}
            head = head.next;
        }
        
        not9.val += 1;
        not9 = not9.next;
        
        while (not9 != null) {
            not9.val = 0;
            not9 = not9.next;
        }
        
        return dummy.val == 1 ? dummy : dummy.next;
    }
}