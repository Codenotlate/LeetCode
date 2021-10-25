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
    // Phase 3 self: M1 - O(1) space way, time O(n) same as recursive way
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {return head;}
        ListNode dummy = new ListNode(-1, head);
        ListNode prevTail = dummy;
        ListNode p1 = head;
        
        while (p1 != null) {
            //find the nextP1 and set its prev node.next = null
            ListNode nextp1 = p1;
            ListNode prevnextp1 = p1;
            int i = 0;
            while (i < k && nextp1 != null) {
                prevnextp1 = nextp1;
                nextp1 = nextp1.next;
                i++;
            }
            if (i != k) {break;}// no need to reverse the following list given the remaining len < k.
            prevnextp1.next = null;
            
            // reverse the list with p1 as head and update prevTail
            prevTail = reverse(p1, prevTail);
            
            p1 = nextp1;         
        }
        
        // check the loop is breaked because following list len < k
        if (p1 != null) {prevTail.next = p1;}
        return dummy.next;   
    }
    
    
    private ListNode reverse(ListNode p1, ListNode prevTail) {
        ListNode prev = null;
        ListNode newTail = p1;
        while (p1 != null) {
            ListNode next = p1.next;
            p1.next = prev;
            prev = p1;
            p1 = next;
        }
       
        prevTail.next = prev;
        return newTail;    
    }
}