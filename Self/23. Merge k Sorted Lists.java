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




// Phase3 self

class Solution {
    // Top down way is obvious, recursive on first and second half, then merge sort these two halves. Time O(nlogk) space O(logk)
    // Bottom up way can optimize the space to be O(1) by storing the sorted list in the original array
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        if (k == 0) {return null;}
        int step = 1;
        while (step < k) {
            for (int i = 0; i < k - step; i = i + 2 * step) {
                lists[i] = mergeSort(lists[i], lists[i + step]);
            }
            step *= 2;
        }
        return lists[0];
    }
    
    
    private ListNode mergeSort(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode prevTail = dummy;
        while (l1 != null || l2 != null) {
            int val1 = l1 == null? Integer.MAX_VALUE:l1.val;
            int val2 = l2 == null? Integer.MAX_VALUE:l2.val;
            if (val1 < val2) {
                prevTail.next = l1;
                l1 = l1.next;
            } else {
                prevTail.next = l2;
                l2 = l2.next;
            }
            prevTail = prevTail.next;
        }
        return dummy.next;
    }
}



/** 2024.7.29
Use a pq with size k to add one node from each linkedlist. Each time pop one node, add its next to othe pq.

Time O(nlogk)

----------------------
Above D&C way is fancy. Try it next time!

 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b)->(a.val-b.val));
        for (ListNode first: lists) {
            if(first != null) {pq.add(first);}
        }
        ListNode dummyHead = new ListNode(-1);
        ListNode curEnd = dummyHead;
        while (!pq.isEmpty()) {
            ListNode cur = pq.poll();
            curEnd.next = cur;
            curEnd = curEnd.next;
            if (cur.next != null) {pq.add(cur.next);}
        }
        return dummyHead.next;
    }
}

