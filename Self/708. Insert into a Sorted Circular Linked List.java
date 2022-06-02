/*Thought
three cases as below.
Be extra careful with equal conditions

time O(n) space O(1)
*/


class Solution {
    public Node insert(Node head, int insertVal) {
        Node insertnode = new Node(insertVal);
        if (head == null) {insertnode.next = insertnode; return insertnode;}
        Node cur = head;
        do {
            if ((cur.val <= insertVal && cur.next.val >= insertVal) || (cur.val <= insertVal && cur.next.val < cur.val) || (cur.next.val > insertVal && cur.next.val < cur.val)) {break;}
            cur = cur.next;
        } while (cur != head); // do this to deal with single node case
        insertnode.next = cur.next;
        cur.next = insertnode;
        return head;
    }
}

/* from discussion
to me this problem is an ideal question for a TDD approach, figure out all test cases before coding. Algorithmic wise its only about traversing the list and adding values.

hi @algocodehk , it is a good point to mention about the TDD (Test-Driven Development, I suppose). In certain sense, I think TDD does apply to all problems in LeetCode. After all, the online-judge on LeetCode verifies the solutions by running testing cases again each solution.
This problem is not particularly complex or difficult, "algorithmically speaking". If one is asked this problem during the interview, it is a good occasion to show the capability of coming up several diverse test cases. And then It is also critical to work out an "elegant" and "concise" solution that works for all test cases, without resorting to too many conditional branches or loops.
*/