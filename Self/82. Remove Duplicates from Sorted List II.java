// Phase3 self
class Solution {
    // one pass: time O(n) space O(1)
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            if (next == null || next.val != cur.val) {
                prev = cur;
                cur = next;
            } else {
                while (next != null && next.val == cur.val) {
                    next = next.next;
                }
                cur = next;
                prev.next = cur;
            }
        }
        return dummy.next;
    }
}