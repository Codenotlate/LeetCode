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


// Phase3 self
// similar as above M3
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
        int size1 = getSize(l1);
        int size2 = getSize(l2);
        // make sure l1 is the one with longer size
        if (size1 < size2) {
            ListNode temp = l1;
            l1 = l2;
            l2 = temp;
        }
        
        ListNode p1 = l1;
        ListNode p2 = l2;
        int diff = Math.abs(size1 - size2);
        ListNode midres = null;
        while (p1 != null) {
            if (diff-->0) {
                midres = new ListNode(p1.val, midres);
            } else {
                midres = new ListNode(p1.val + p2.val, midres);
                p2 = p2.next;
            }
            p1 = p1.next;          
        }
        
        // now get the midres in reverse direction, next adjust the remaining and reverse the order at the same time
        ListNode prev = null;
        ListNode cur = midres;
        int rem = 0;
        while(cur != null) {
            //adjust num and rem
            int value = cur.val + rem;
            cur.val = value % 10;
            rem = value / 10;
            // reverse the direction
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        
        return rem == 0? prev : new ListNode(rem, prev);
        
        
    }
    
    private int getSize(ListNode l) {
        int size = 0;
        while (l != null) {
            size++;
            l = l.next;
        }
        return size;
    }
}



// Review
/*Initial thought
The straightforward way is to reverse both arrays, get the result and reverse the result array to return.
The other way is to loop the two input arrays first in forward way, and build the result array in reverse order, with each node containing original sum value even it's larger than 10. e.g. for 9->9->9 and 9, we will get 9<-9<-18
Then we adjust the result array for the carry value to its next node and at the same time reverse the array iteratively. e.g. we will get 0->0->8. And if carry != 0, we add a head node with value as carry: 1->0->0->8.
time O(max(l1, l2)) space O(1)
*/
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // always make l1 the larger one
        int len1 = getLen(l1);
        int len2 = getLen(l2);
        if(len1 < len2) {
            ListNode temp = l2;
            l2 = l1;
            l1 = temp;
            int tempLen = len1;
            len1 = len2;
            len2 = tempLen;
        }
        
        ListNode head = null;        
        while (len1 > 0) {
            int v1 = l1.val;
            int v2 =  len1 > len2 ? 0 : l2.val;
            head = new ListNode(v1 + v2, head);
            l1 = l1.next;
            if (len1 <= len2) {l2 = l2.next;}
            len1--;            
        }
        
        // we get the raw result, we need to adjust the carry and reverse it
        ListNode prev = null;
        ListNode cur = head;
        int carry = 0;
        while(cur != null) {
            // adjust the val
            int num = cur.val + carry;
            cur.val = num % 10;
            carry = num / 10;
            // reverse the order
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        
        if(carry != 0) {ListNode res = new ListNode(carry, prev); return res;}
        return prev;
    }
    
    private int getLen(ListNode l) {
        int len = 0;
        while( l!=null) {
            l = l.next;
            len++;
        }
        return len;
    }
}





















