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
	// M1: time O(n) space O(n)
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        while (head != null) {
        	if (visited.contains(head)) {return head;}
        	visited.add(head);
        	head = head.next;       	
        }
        return null;
    }
}




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
	// M2: use slow and fast pointers, make space O(1)
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (slow != null) {
        	slow = slow.next;
        	if (fast.next == null || fast.next.next == null) {
        		// meaning no circle
        		return null;
        	}
        	fast = fast.next.next;
        	if (fast == slow) {
        		break;
        	}
        }
        // exit the loop when slow and fast meet first time

        // 2nd round: fast starts at beginning of the list, and slow starts at where they first met
        fast = head;
        while (fast != slow) {
        	fast = fast.next;
        	slow = slow.next;
        }
        return fast;
    }
}



// Phase 3 self
public class Solution {
    public ListNode detectCycle(ListNode head) {
        // time O(n), space O(1)
        ListNode slow = head;
        ListNode fast = head;
        while (fast!= null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {break;}
        }
        if (fast == null || fast.next == null) {return null;}
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}



// another way of writing method 2 (more precise)
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            
            if (fast == slow){ // put the second round here
                ListNode slow2 = head; 
                while (slow2 != slow){
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;
            }
        }
        return null;
    }
}



// https://leetcode.com/problems/linked-list-cycle-ii/discuss/44774/Java-O(1)-space-solution-with-detailed-explanation.




















