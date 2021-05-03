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
	// M1（self）: similar idea as merge sort, divide and conquar with merge2Lists
    // time O(n * logk) where n means in total n nodes in the final result, and k is the size of lists
    // space O(logk), since we use recursive here, recursive stack is used logk times
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {return null;}
        return divideHelper(lists, 0, lists.length - 1);
    }

    private ListNode divideHelper(ListNode[] lists, int start, int end) {
    	// base case
    	if (start > end) {return null;}
    	if (start == end) {return lists[start];}
    	int mid = start + (end - start) / 2;
    	ListNode l1 = divideHelper(lists, start, mid);
    	ListNode l2 = divideHelper(lists, mid + 1, end);
    	return merge2Lists(l1, l2);
    }

    private ListNode merge2Lists(ListNode l1, ListNode l2) {
    	if (l1 == null) {return l2;}
    	if (l2 == null) {return l1;}
    	ListNode newHead = new ListNode(-1);
    	ListNode newTail = newHead;
    	while (l1 != null && l2 != null) {
    		if (l1.val < l2.val) {
    			newTail.next = l1;
    			newTail = l1;
    			l1 = l1.next;
    		} else {
    			newTail.next = l2;
    			newTail = l2;
    			l2 = l2.next;
    		}
    	}
    	newTail.next = l1 != null ? l1 : l2;
    	return newHead.next;
    }
}


// M2: based on M1: optimize the space to O(1)
// by changing from top-down to bottom-up

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {return null;}
        int interval = 1;
        int K = lists.length;

        while (K > interval) {
        	for (int i = 0; i < K - interval; i = i + 2 * interval) {
        		lists[i] = merge2Lists(lists[i], lists[i + interval]);
        	}
        	interval *= 2;
        }
        return lists[0];
    }

    private ListNode merge2Lists(ListNode l1, ListNode l2) {
    	if (l1 == null) {return l2;}
    	if (l2 == null) {return l1;}
    	ListNode newHead = new ListNode(-1);
    	ListNode newTail = newHead;
    	while (l1 != null && l2 != null) {
    		if (l1.val < l2.val) {
    			newTail.next = l1;
    			newTail = l1;
    			l1 = l1.next;
    		} else {
    			newTail.next = l2;
    			newTail = l2;
    			l2 = l2.next;
    		}
    	}
    	newTail.next = l1 != null ? l1 : l2;
    	return newHead.next;
    }
}






// M3: using priority queue
// can also use priority queue, put k numbers of nodes into queue, and pop one, add its next
// thus the max size of priority queue is k. add operation takes O(logk) time
// if in total there are n nodes, then its time is O(nlogk), space O(k)
// https://leetcode.com/problems/merge-k-sorted-lists/discuss/10528/A-java-solution-based-on-Priority-Queue




