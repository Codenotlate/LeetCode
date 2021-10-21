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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode resHead = new ListNode(-999, null);
        ListNode pRes = resHead;
        ListNode p1, p2;

        p1 = l1;
        p2 = l2;

        while (p1 != null && p2 != null) {
        	if (p1.val <= p2.val) {
        		pRes.next = new ListNode(p1.val, null);
        		p1 = p1.next;
        	} else {
        		pRes.next = new ListNode(p2.val,null);
        		p2 = p2.next;
        	}
        	pRes = pRes.next;
        }
        while (p1 != null) {
        	pRes.next = new ListNode(p1.val, null);
        	p1 = p1.next;
        	pRes = pRes.next;
        }
        while (p2 != null) {
        	pRes.next = new ListNode(p2.val, null);
        	p2 = p2.next;
        	pRes = pRes.next;
        }
        return resHead.next;

    }
}


// self phase2: iterative way more concise version
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode resHead = new ListNode(-999, null);
        ListNode resTail = resHead;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                resTail.next = l1;
                l1 = l1.next;
            } else {
                resTail.next = l2;
                l2 = l2.next;
            }
            resTail = resTail.next;
        }
        resTail.next = l1 == null ? l2 : l1;
        return resHead.next;
    }
}


class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // Recursive way
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val <= l2.val) {
        	l1.next = mergeTwoLists(l1.next, l2);
        	return l1;
        } else {
        	l2.next = mergeTwoLists(l1, l2.next);
        	return l2;
        }

    }
}


// phase 3 self
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // M2: iterative way
        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;
        while (l1 != null || l2 != null) {
            int val1 = l1 == null ? 1000 : l1.val;
            int val2 = l2 == null ? 1000 : l2.val;
            if (val1 <= val2) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        return dummy.next;
    }
}