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

// Phase3 self
// M1 time O(n + m) space O(1)
// use negative label same as above M1, but changed the list, not a very ideal way
public class Solution {
    // try negative due to O(1) space req
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != null) {p1.val *= -1; p1 = p1.next;}
        while(p2 != null) {
            if (p2.val < 0) {break;}
            p2 = p2.next;
        }
        // revert back listA
        p1 = headA;
        while (p1 != null) {p1.val *= -1; p1 = p1.next;}
        return p2;
        
    }
}

// better way!
public class Solution {
    // M2: since if they intersect they will share the same tail, thus the key is to start two pointers at the same relative position towards the tail
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while(p1 != p2) {
            p1 = p1 == null? headB : p1.next;
            p2 = p2 == null? headA : p2.next;
        }
        return p1;
    }
}






































