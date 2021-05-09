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
    public ListNode deleteDuplicates(ListNode head) {
        // iterative way: use two pointers
        // base case
        if (head == null) {return head;}
        ListNode prev = head;
        ListNode cur = head.next;

        while (cur != null) {
        	if (cur.val == prev.val) {
        		cur = cur.next;
        	} else {
        		prev.next = cur;
        		cur = cur.next;
        		prev = prev.next;
        	}
        }
        // for all duplicates case
        prev.next = null;
        return head;
    }
}


class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // Recursive way: space O(n), thus not recommended
        if (head == null) {
        	return head;
        }
        head.next = deleteDuplicates(head.next);
        return head.next != null && head.val == head.next.val? head.next : head;
    }
}
















