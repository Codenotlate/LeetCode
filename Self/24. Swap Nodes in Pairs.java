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
    public ListNode swapPairs(ListNode head) {
        // Recursive way: space O(n)
        // base case
        if (head == null || head.next == null) {
        	return head;
        }
        ListNode one = head;
        ListNode two = head.next;

        one.next = swapPairs(two.next);
        two.next = one;
        return two;
    }
}

class Solution {
    public ListNode swapPairs(ListNode head) {
        // Iterative way: space O(1)
        // base case
        if (head == null || head.next == null) {
        	return head;
        }
        
        ListNode pre = head, cur = head.next;
        ListNode newHead = new ListNode(-1);
        ListNode newTail = newHead;

        while (pre != null && cur != null)  {
        	pre.next = cur.next;
        	cur.next = pre;
        	newTail.next = cur;
        	
        	// move three pointers
        	newTail = pre;
        	pre = pre.next;
        	if (pre != null) {
        		cur = pre.next;
        	} // for odd number in the end
        }

        return newHead.next;

    }
}




// Phase 2 self solution: same idea as M2 above
class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        head = cur;
        ListNode tail = new ListNode();
        while (cur != null) {
            pre.next = cur.next;
            cur.next = pre;
            tail.next = cur;
            
            tail = pre;
            pre = pre.next;
            if (pre == null) {break;}
            cur = pre.next;
        }
        return head;
    }
}