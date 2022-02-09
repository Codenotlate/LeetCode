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

// Phase 3 self
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {return head;}
        // step1: use slow/fast pointers to find target and track prev nodes for later use
        ListNode slow = head;
        ListNode fast = head;
        ListNode dummy = new ListNode(-1, head);
        int len = 0;
        while (k-- > 0) {
            fast = fast.next;
            len++;
            if (fast == null) {
                fast = head;
                k %= len; // this is the key to speed up for cases that k >> len
            } // deal with k > len case
        }
        if (fast == head) {return head;}
        ListNode prevs = dummy;
        ListNode prevf = fast;
        while (fast != null) {
            prevs = slow;
            slow = slow.next;
            prevf = fast;
            fast = fast.next;
        }
        
        
        // step2: use all the tracker nodes to reorder the list
        prevf.next = dummy.next;
        dummy.next = slow;
        prevs.next = null;
        return dummy.next;
    }
}


// Review self
class Solution {
    /* Initial thought
    first, when k is larger than List length, k = k % len. Then we need to find the (k+1)th node prevK from end and the end node. Then end.next = dum.next; dum.next = prevK.next; prevK.next = null. return dum.next.
    The way we find the (k+1)th node from end and the end node is by slow/fast pointers.
    time O(n) space O(1)
    */
    
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {return head;}
        ListNode cur = head;
        int size = 0;
        while(cur!= null) {cur = cur.next; size++;}
        k %= size;
        if (k == 0) {return head;}
        
        ListNode dum = new ListNode(0, head);
        cur = head;
        for(int i = 0; i < k; i++) {cur = cur.next;}
        ListNode prevK = head;
        while(cur.next != null) {prevK = prevK.next; cur = cur.next;}
        
        cur.next = dum.next;
        dum.next = prevK.next;
        prevK.next = null;
        return dum.next;
    }
}