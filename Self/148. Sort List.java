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
	//self: O(1) space bottom-up merge sort
    public ListNode sortList(ListNode head) {
        if (head == null) {return null;}
        int interval = 1;
        int n = size(head);

        ListNode newHead = new ListNode(-1, head);
        ListNode l1 = head;
        while (interval <= n) {
        	ListNode newTail = newHead;
        	while (l1 != null) {
        		ListNode l2 = split(l1, interval);
	        	ListNode l3 = split(l2, interval);
	        	// since head might change after each merge, we choose to append to tail and keep a dummy head
	        	newTail = mergeAndConnect(l1, l2, newTail);
	        	l1 = l3;
        	}
        	interval *= 2;
        	l1 = newHead.next; // reset l1 to the head of the sorted list after iterate all for one interval
        }
        return newHead.next;
    }

    // return the size of the list
    private int size(ListNode head) {
    	int count = 0;
    	while (head != null) {
    		count++;
    		head = head.next;
    	}
    	return count;
    }

    // split the list to head with interval # of nodes, 
    // set the end of the split part to null and return the next node after the split part
    private ListNode split(ListNode head, int interval) {
    	ListNode p = head;
    	while (interval > 1 && p != null) {
    		p = p.next;
    		interval--;
    	}
    	if (p == null) {
    		return null;
    	} else {
    		ListNode next = p.next;
    		p.next = null;
    		return next;
    	}
    }


    // merge sorted l1 and l2 and append to newTail of previous sorted part
    // return the newTail of the sorted list
    private ListNode mergeAndConnect(ListNode l1, ListNode l2, ListNode newTail) {
    	while (l1 != null && l2 != null) {
    		if (l1.val < l2.val) {
    			newTail.next = l1;
    			l1 = l1.next;
    		} else {
    			newTail.next = l2;
    			l2 = l2.next;
    		}
    		newTail = newTail.next;
    	}
    	newTail.next = l1 == null ? l2 : l1;
    	while (newTail.next != null) {
    		newTail = newTail.next;
    	}
    	return newTail;
    }
}




// almost same idea, slightly concise way
// https://leetcode.com/problems/sort-list/discuss/46712/Bottom-to-up(not-recurring)-with-o(1)-space-complextity-and-o(nlgn)-time-complextity


// can also try top-down with space O(logn) next time

// Phase 3 self
// M1: bottom up
/**
 * Definition for singly-l nked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode sortList(ListNode head) {
        // Phase3 self
        // bottom-up time O(nlogn) space O(1)
        if (head == null || head.next == null) {return head;}
        int step = 2;
        ListNode dummy = new ListNode(-1, head);
        // get the list len
        ListNode p1 = head;
        int len = 0;
        while(p1 != null) {
            len++;
            p1 = p1.next;
        }
        
        // start the main loop
        ListNode prevTail;
        while (step / 2 <= len) { // while there still exists the first p2 to merge with p1
            prevTail = dummy;
            p1 = dummy.next;
            while (p1 != null) {
                
                // find next p1
                ListNode p1next = p1;
                ListNode tempprev = p1next; // use to split p1 list and p2 list, set their end to null
                for (int i = 0; i < step; i++) {
                    tempprev = p1next;
                    p1next = p1next.next;
                    if (p1next == null) {break;}
                }
                tempprev.next = null;
                // find corresponding p2 for current p1
                int n = step / 2; // the length to get p2 from p1
                ListNode p2 = p1;
                while (n-- > 0) {
                    tempprev = p2;
                    p2 = p2.next;
                    if (p2 == null) {break;}
                }
                tempprev.next = null;
                
                // merge p1 list and p2 list, return current prevtail
                prevTail = mergesort(p1, p2, prevTail);
                
                p1 = p1next;
            }
            step *= 2;        
        }
        return dummy.next;
        
    }
    
    private ListNode mergesort(ListNode p1, ListNode p2, ListNode prevTail) {
        while(p1!=null || p2!= null) {
            int val1 = p1 == null? Integer.MAX_VALUE : p1.val;
            int val2 = p2 == null? Integer.MAX_VALUE : p2.val;
            if (val1 < val2) {
                prevTail.next = p1;
                p1 = p1.next;
            } else {
                prevTail.next = p2;
                p2 = p2.next;
            }
            prevTail = prevTail.next;
        }
        return prevTail;
    }
}





class Solution {
    // M2: top donw time O(nlogn) space O(logn)
    public ListNode sortList(ListNode head) {
        // step1: find the middle point using slow and fast points
        // step2: sort first half and sort the second half
        // step3: merge 2 sorted list
        if (head == null || head.next == null) {return head;}
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode secondhead = slow.next;
        slow.next = null;
        ListNode firsthead = sortList(head);
        secondhead = sortList(secondhead);
        
        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;
        while (firsthead != null || secondhead != null) {
            int val1 = firsthead == null? Integer.MAX_VALUE:firsthead.val;
            int val2 = secondhead == null? Integer.MAX_VALUE:secondhead.val;
            if (val1 < val2) {
                prev.next = firsthead;
                firsthead = firsthead.next;
            } else {
                prev.next = secondhead;
                secondhead = secondhead.next;
            }
            prev = prev.next;
        }
        return dummy.next;
    }
}












