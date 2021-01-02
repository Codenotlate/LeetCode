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