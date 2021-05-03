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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // method1: reverse the list first using two stacks
        // base case
        if (l1 == null) {return l2;}
        if (l2 == null) {return l1;}

        Stack<Integer> s1 = buildStack(l1);
        Stack<Integer> s2 = buildStack(l2);

        int carry = 0;
        ListNode resHead = null;

        while(!s1.isEmpty() || !s2.isEmpty() || carry != 0) {
        	int d1 = s1.isEmpty()? 0 : s1.pop();
        	int d2 = s2.isEmpty()? 0 : s2.pop();
        	int digit = (d1 + d2 + carry) % 10;
        	carry = (d1 + d2 + carry) / 10;
        	resHead = new ListNode(digit, resHead);
        }
        return resHead;

    }

    private Stack<Integer> buildStack(ListNode l) {
    	Stack<Integer> res = new Stack<>();
    	while (l != null) {
    		res.push(l.val);
    		l = l.next;
    	}
    	return res;
    }


}


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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int s1, s2;
        // make sure l1 is the longer one
        if (size(l2) > size(l1)) {
        	ListNode temp = l1;
        	l1 = l2;
        	l2 = temp;
        }
    	s1 = size(l1);
    	s2 = size(l2);

    	ListNode tempHead = null;

    	while(s1 > s2) {
    		int d1 = l1.val;
    		int d2 = 0;
    		s1 -= 1;
    		l1 = l1.next;
    		tempHead = new ListNode(d1 + d2, tempHead);
    	}// now s1 = s2

    	while (l1 != null) {
    		tempHead = new ListNode(l1.val + l2.val, tempHead);
    		l1 = l1.next;
    		l2 = l2.next;
    	}
    	// now get tempHead list, do normalization
    	int carry = 0;
    	ListNode resHead = null;
    	while (tempHead != null) {
    		int digit = (tempHead.val + carry) % 10;
    		carry = (tempHead.val + carry) / 10;
    		resHead = new ListNode(digit, resHead);
    		tempHead = tempHead.next;
    	}
    	// if carry != 0, need to add a new node
    	if (carry != 0) {
    		resHead = new ListNode(carry, resHead);
    	}
    	return resHead;  
    }


    private int size(ListNode l) {
    	int size = 0;
    	while (l != null) {
    		size += 1;
    		l = l.next;
    	}
    	return size;
    }

}




/**
M3: based on M2. but reverse the tempHead list inplace with carry
optimize the space from O(2n) to O(n)
 */
class Solution {
    // no reverse of input
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int size1 = size(l1);
        int size2 = size(l2);
        int sizeDiff = size1 - size2;
        if (sizeDiff < 0) { // swap to make sure l1 size >= l2 size.
            ListNode temp = l1;
            l1 = l2;
            l2 = temp;
            sizeDiff *= -1;
        }
        
        ListNode reverseHead = null;
        // deal with the part l1 longer than l2
        while (sizeDiff > 0) {
            reverseHead = new ListNode(l1.val, reverseHead);
            sizeDiff--;
            l1 = l1.next;
        }
        // deal with the part l1 and l2 have the same size
        while(l1 != null) {
            reverseHead = new ListNode(l1.val + l2.val, reverseHead);
            l1 = l1.next;
            l2 = l2.next;
        }
        return reverseWithCarry(reverseHead, 0); // try to reverse inplace        
    }
    
    private int size(ListNode node) {
        int res = 0;
        while (node != null) {
            res++;
            node = node.next;
        }
        return res;
    }
    
    private ListNode reverseWithCarry(ListNode head, int carry) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode recordNext = cur.next;
            int sum = cur.val + carry;
            cur.val = sum % 10;
            carry = sum / 10;
            // after update the val of cur node, we reverse
            cur.next = pre;
            pre = cur;
            cur = recordNext;
        }
        if (carry != 0) {
            pre = new ListNode(carry, pre);
        }
        return pre;
    }
}

























