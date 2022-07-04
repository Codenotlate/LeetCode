/*Thought
notice the length is even, we can first use fast and slow pointers to find the first node of the second half. 
For O(n) space way, it's simple. Just do the second pass with fast pointer starting at pos0, and move with previous slow pointer on same pace, having an array with size (n/2) to store the twin sum, then add the number to the corresponding array pos. Go through the twin sum array again to find the max sum. (or a simpler version implemented in M1)
For O(1) space way, need to reverse the second half of the LL. Then pass the two halves to have maxsum in O(1) space. But if we require LL being unchanged, we need to revert back the reverse.
*/
// M1: O(n) space O(n) time
class Solution {
    public int pairSum(ListNode head) {
        int size = 0;
        ListNode cur = head;
        while (cur != null) {
            size++;
            cur = cur.next;
        }
        
        int[] sums = new int[size / 2];
        int res = 0;
        int i = 0;
        cur = head;
        while (cur != null) {
            if (i < sums.length) {sums[i] += cur.val; res = Math.max(res, sums[i]);}
            else {sums[size - 1 - i] += cur.val; res = Math.max(res, sums[size-1-i]);}
            cur = cur.next;
            i++;            
        }
        return res;
    }
}

// M2: O(1) space O(n) time
class Solution {
    public int pairSum(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        ListNode secondStart = reverse(slow);
        int res = 0;
        fast = head;
        slow = secondStart;
        while (slow != null) {
            res = Math.max(res, fast.val + slow.val);
            fast = fast.next;
            slow = slow.next;
        }
        
        reverse(secondStart);
        return res;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}