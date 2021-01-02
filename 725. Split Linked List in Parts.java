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
    public ListNode[] splitListToParts(ListNode root, int k) {
    	ListNode[] resList = new ListNode[k];
    	int size = 0;
    	ListNode p = root;
    	while (p != null) {
    		size += 1;
    		p = p.next;
    	}
    	int base = size / k;
    	int extraCount = size % k;
    	
    	p = root;
    	int i = 0;
    	while (i < k && p != null) {
    		int partLen = base;
    		resList[i] = p;
    		if (extraCount > 0) {
    			partLen = base + 1;
    			extraCount -= 1;
    		}
    		while (partLen > 1) {
    			p = p.next;
    			partLen -= 1;
    		}
    		ListNode nextHead = p.next;
    		p.next = null;
    		p = nextHead;
    		i += 1;
    	}
    	return resList;
    }
}