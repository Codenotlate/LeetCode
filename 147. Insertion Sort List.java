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
	// M1 : self time O(n^2) space O(1)
    public ListNode insertionSortList(ListNode head) {
        ListNode sortedHead = new ListNode(-1);
        ListNode cur = head;
        while (cur != null) {
        	ListNode next = cur.next;
        	sortedHead = addToSorted(sortedHead, cur);
        	cur = next;
        }
        return sortedHead.next;
    }

    private ListNode addToSorted(ListNode sortedHead, ListNode cur) {
    	if (cur == null) {return sortedHead.next;}
    	ListNode p = sortedHead;
    	while (p.next != null) {
    		if (p.next.val > cur.val) {
    			cur.next = p.next;
    			p.next = cur;
    			return sortedHead;
    		}
    		p = p.next;
    	}
    	// cur is the larger than all nodes in sortedList
    	p.next = cur;
    	cur.next = null;
    	return sortedHead;
     }
}



// an improved way based on M1 idea: combine two functions together and only reset p to sortedHead when p.val > cur.val
// https://leetcode.com/problems/insertion-sort-list/discuss/46420/An-easy-and-clear-way-to-sort-(-O(1)-space-)
class Solution {
    public ListNode insertionSortList(ListNode head) {
        ListNode sortedHead = new ListNode(-1);
        ListNode cur = head; 
        ListNode p = sortedHead;
        while (cur != null) {
        	ListNode next = cur.next;
        	if (p.val > cur.val) {p = sortedHead;}
        	while (p.next != null && p.next.val <= cur.val) {p = p.next;}

        	cur.next = p.next;
        	p.next = cur;

        	cur = next;
        }
        return sortedHead.next;
    }
}

// discuss with interviewer, see if he wants you to sort an array or linkedlist only
// https://leetcode.com/problems/insertion-sort-list/discuss/46429/Thoughts-from-a-Google-interviewer



