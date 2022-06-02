/*Thought
Can do it in one pass. first pointer go k steps first, record the kth node. Then another second pointer starts at beginning, move with the first pointer until first pointer reach the end, the second pointer will be at position n-k. swap the value for the two pointers.
time O(n) space O(1)
*/
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        ListNode cur = head;
        ListNode first = null;
        int count = 1;
        while (count < k) {
            cur = cur.next;
            count++;
        }
        first = cur;
        ListNode second = head;
        while (cur.next != null) {
            cur = cur.next;
            second = second.next;
        }
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
        return head;
    }
}