/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

/*Initial thought
Using recursive way. recur(cur), can make the double linkedlist cur.child between cur and cur.next and then return the last node after flatten.  

time O(n)
space O(k) k is the number of separate double linkedlist.

bug sample:
1-null
|
2-null
|
3-null

some special cases: if cur.next == null. How to return the last node.
*/

class Solution {
    public Node flatten(Node head) {
        flatHelp(head);
        return head;
    }
    
    private Node flatHelp(Node cur) {
        if (cur == null) {return cur;}
        while(cur!= null) {
            if (cur.child != null) {
                Node last = flatHelp(cur.child);
                if (cur.next != null) {
                    last.next = cur.next;
                    cur.next.prev = last;              
                }
                
                cur.next = cur.child;
                cur.child.prev = cur;
                cur.child = null;
                
                cur = last;
            }
            if(cur.next == null) {break;}
            cur = cur.next;
        }
        return cur;
    }
}


/*additional thought
if we view the head as the root of a tree, this is actually preorder dfs. Thus in addition to the recursive way, we can also do iterative way using a stack. basically, every time we pop out the node from the stack, it's gonna be added as the next node in the result flatten list. If this pop out node has both next(like the right child in a tree) and child(like the left child in a tree), then push next and then push child node.

space O(n) time O(k) possible that k == n
*/

class Solution {
    public Node flatten(Node head) {
        if (head == null) {return head;}
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node dummy = new Node(-1, null, null, null);
        Node curTail = dummy;
        while (!stack.isEmpty()) {
            Node popout = stack.pop();
            curTail.next = popout;
            popout.prev = curTail;
            curTail = popout;
            if (popout.next != null) {stack.push(popout.next);}
            if (popout.child != null) {stack.push(popout.child); popout.child = null;}
        }
        dummy.next.prev = null;
        return dummy.next;
    }
    
    
}


/* Another straight forward way to do it
https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/discuss/150321/Easy-Understanding-Java-beat-95.7-with-Explanation

This is actually what I think in the very beginning, but I thought the time complexity will be bad. It was mistaken, since each node is processed at most twice, this is still an O(n) time way and with O(1) space.

//What is the time complexity of this solution? It seems still to be an O(n) solution. Since we need to visit each node at most twice, one for find the tail of the current branch, and the other for flatten the current node. Correct me if I am wrong.
If so, this is a really nice solution with O(n) time and O(1) space!

This is more like a top down flattening, when encounter a node with child node, we directly flatten the current node, then move to the next node.
the recursive method is more like bottom up flattening, means when we encounter a node with child node, we flatten the child node first, then flatten the current node.//

try self implement this way next time.
*/



// Review
// similar recursive way as above M1, but slightly diff implementation
class Solution {
    public Node flatten(Node head) {
        reflat(head);
        return head;
    }
    
    
    private Node reflat(Node head) {
        if (head == null) {return null;}
        Node next = head.next;
        Node childEnd = head;
        if (head.child != null) {
            childEnd = reflat(head.child);
            if (head.next != null) {
                head.next.prev = childEnd;
                childEnd.next = head.next;
            }
            head.next = head.child;
            head.child.prev = head;
            head.child = null;
        } 
        return next == null? childEnd : reflat(next);
    }
}
// preorder way of a tree (child as left and next as right, prev as parent)
class Solution {
    public Node flatten(Node head) {
        if (head == null) {return head;}
        Node dummy = new Node(-1, null, null, null);
        Node tail = dummy;
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            tail.next = cur;
            cur.prev = tail;
            tail = tail.next;
            if (cur.next != null) {stack.push(cur.next);}
            if (cur.child != null) {stack.push(cur.child);}
            cur.child = null;
        }
        dummy.next.prev = null;
        return dummy.next;
    }
}














