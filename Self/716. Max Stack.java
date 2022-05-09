/*Thought
double-sided LL, and [Wrong because we may have node with same value: a max heap of node(based on node val)] Thus we need a treemap to store: value -> LinkedList of node in list.
when push, add a new node to the end of LL, and add a node to the list in treemap under that value. O(logn)
when pop, remove the last node of LL, and remove the node from map.get(val). since the list is added in the order, thus the last node in the map list should be removed.(LL removelast is O(1)) O(logn)
when top, return the last node value O(1)
when peekMax, return the last key in the treemap. O(logn)
when popMax, pop the last key from the treemap, connect its prev and next node directly in LL. O(logn)

remember remove an element from pq is O(n). Thus not use pq here.
*/
class MaxStack {
    public class Node {
        Node prev;
        Node next;
        int val;
        
        public Node(int value) {
            val = value;
        }
    }
    
    
    private Node listhead, listtail;
    private TreeMap<Integer, LinkedList<Node>> map;

    public MaxStack() {
        listhead = new Node(-1);
        listtail = listhead;
        map = new TreeMap<>();
        
    }
    
    public void push(int x) {
        Node cur = new Node(x);
        listtail.next = cur;
        cur.prev = listtail;
        listtail = cur;
        
        map.putIfAbsent(x, new LinkedList<Node>());
        map.get(x).add(cur);
    }
    
    public int pop() {
        Node popped = listtail;
        listtail = popped.prev;
        listtail.next = null;
        popped.prev = null;
        
        map.get(popped.val).removeLast();
        // don't forget this line, otherwise those key with empty list will still be lastkey and impact peekMax
        if (map.get(popped.val).size() == 0) {map.remove(popped.val);}
        return popped.val;
    }
    
    public int top() {
        return listtail.val;
    }
    
    public int peekMax() {
        return map.lastKey();
    }
    
    public int popMax() {
        int max = map.lastKey();
        Node deleted = map.get(max).removeLast();
        // don't forget this line, otherwise those key with empty list will still be lastkey and impact peekMax
        if (map.get(max).size() == 0) {map.remove(max);}
        
        if(deleted.next == null) {listtail = listtail.prev; listtail.next = null;}
        else {
            deleted.prev.next = deleted.next;
            deleted.next.prev = deleted.prev;  
        }
        
        
        // Node cur = listtail;
        // while (cur.val != -1) {
        //     System.out.print(cur.val);
        //     cur = cur.prev;
        // }
        
        return max;
    }
}
// hint from https://leetcode.com/problems/max-stack/discuss/129922/Java-simple-solution-with-strict-O(logN)-push()popMax()pop()