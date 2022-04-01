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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // one pass way
        if (head == null || head.next == null) {return head;}
        ListNode dummy = new ListNode(-1, head);
        ListNode former = dummy;
        int i = -1;
        while ( i < left - 2) {
            former = former.next;
            i++;
        }
        // now former points to the node in left position
        i += 2;
        ListNode prev = former.next;
        ListNode cur = prev.next;
        while(i <= right - 1) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
            i++;
        }
        former.next.next = cur;
        former.next = prev;
        return dummy.next;
        
        
    }
}


// a more concise way in solution M2
// https://leetcode.com/problems/reverse-linked-list-ii/solution/
// below M2 is similar to solution M2, easier for me to understand

// Phase3 self
/* initial thought
find the position of firstTail before the reversed range and the start of the reverse node, do reverse inplace until prev reaches the end of the reverse range. change firstTail.next.next = cur, firstTail.next = prev.
need to setup a dummy in front, return dummy.next in the end.

*/
//M2
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1, head);
        ListNode firstTail = dummy;
        int step = left;
        while(step-- > 1) {firstTail=firstTail.next;}
        ListNode cur = firstTail.next;
        ListNode prev = null;
        while(right-- > left - 1) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        firstTail.next.next = cur;
        firstTail.next = prev;
        return dummy.next;
    }
}





// review
/*Initial thought
If no need for one pass, we can have three pointers pointing to prevnode of left, right node and next node of right using the first pass. And then do the second pass to reverse the LL between p1.next and right. The reverse can be either recursively or iteratively.
For one pass way, we can combine above two steps, do the label and reverse simultaneously.
Note need to pay attention to edge case, when left is head or right is end node.
time O(n) space O(1)
e.g. [1,2,3,4,5] left = 1 right = 3
*/
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(left == right) {return head;}
        int curpos = 0;
        ListNode dummy = new ListNode(-1, head);
        ListNode p1 = dummy;
        ListNode cur = head;
        ListNode prev = null;
        while(curpos < right) {
            curpos++;
            if (curpos < left - 1) {cur = cur.next; continue;}
            else if (curpos == left - 1) {p1 = cur; cur = cur.next;}
            else { // do reverse here
                ListNode next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }           
        }
        p1.next.next = cur;
        p1.next = prev;
        return dummy.next;
    }
}