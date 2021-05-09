/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p = headA;
        ListNode intersect = null;

        while (p != null) {
        	p.val = -p.val;
        	p = p.next;
        }

        p = headB;
        while (p != null) {
        	if (p.val < 0) {
        		intersect = p;
        		break;
        	}
        	p = p.next;
        }
        p = headA;
        while (p != null) {
        	p.val = -p.val;
        	p = p.next;
        }
        return intersect;

    }
}

public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode curA = headA;
        ListNode curB = headB;

        while (curA != curB) {
        	curA = (curA == null)? headB : curA.next;
        	curB = (curB == null)? headA : curB.next;
        }
        return curB;

    }
}














