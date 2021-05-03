/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
	// two pointers: fast and slow
	// if there's a circle, fast will definitely catch slow before slow finish one circle
	// time O(n) space O(1)
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
        	fast = fast.next.next;
        	slow = slow.next;
        	if (fast == slow) {
        		return true;
        	}
        }
        return false;
    }
}