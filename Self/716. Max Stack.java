/*Thought
double-sided LL, and [Wrong because we may have node with same value: a max heap of node(based on node val)] Thus we need a treemap to store: value -> LinkedList of node in list.
when push, add a new node to the end of LL, and add a node to the list in treemap under that value. O(logn)
when pop, remove the last node of LL, and remove the node from map.get(val). since the list is added in the order, thus the last node in the map list should be removed.(LL removelast is O(1)) O(logn)
when top, return the last node value O(1)
when peekMax, return the last key in the treemap. O(logn)
when popMax, pop the last key from the treemap, connect its prev and next node directly in LL. O(logn)

!!!---remember remove an element from pq is O(n). Thus not use pq here.---!!!
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


// Review 23/1/24
/* Thoughts
to make popMax time not O(n), we need a double LL. And in order to know the next largest after current max being popped out, we need a sorted structure.e.g. maxheap or treeMap. Note maxheap won't work here, as remove a non-root element from heap takes O(n) time.
Thus we use treemap with <node value, List<node in LL>>.
Note List get size is O(1) for both arrayList and LL. And LL.removeLast() is O(1), arrayList.remove(size-1) is also O(1).
https://stackoverflow.com/questions/43145395/time-complexity-while-deleting-last-element-from-arraylist-and-linkedlist
https://stackoverflow.com/questions/863469/what-is-the-time-complexity-of-a-size-call-on-a-linkedlist-in-java
 */
class MaxStack {
    private class Node {
        int val;
        Node prev;
        Node next;

        public Node(int value) {
            val = value;
        }
    }


    TreeMap<Integer, ArrayList<Node>> map;
    Node dumHead;
    Node dumTail;

    public MaxStack() {
        map = new TreeMap<>();
        dumHead = new Node(-1);
        dumTail = dumHead;
    }
    
    public void push(int x) {
        Node addNode = new Node(x);
        // add in double LL
        dumTail.next = addNode;
        addNode.prev = dumTail;
        dumTail = addNode;

        // add in map
        map.putIfAbsent(x, new ArrayList<>());
        map.get(x).add(addNode);
    }
    
    public int pop() {
        // remove in double LL
        int val = dumTail.val;
        dumTail = dumTail.prev;
        dumTail.next = null;

        // remove in map
        map.get(val).remove(map.get(val).size() - 1);
        if (map.get(val).size() == 0) {map.remove(val);}

        return val;
    }
    
    public int top() {
        // return last node in LL
        return dumTail.val;
        
    }
    
    public int peekMax() {
        //return last key in map
        return map.lastKey();
    }
    
    public int popMax() {
        // remove from map.lastKey() list
        int maxVal = map.lastKey();
        Node maxNode = map.get(maxVal).remove(map.get(maxVal).size()-1);
        if (map.get(maxVal).size() == 0) {map.remove(maxVal);}

        // remove maxNode from LL (special case when maxNode is dumTail)
        maxNode.prev.next = maxNode.next;
        if (maxNode.next != null) {maxNode.next.prev = maxNode.prev;}
        else {dumTail = maxNode.prev;}

        return maxVal;
    }
}



/* Review of heap data structure
Implementation note: (https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html)
this implementation provides O(log(n)) time for the enqueing and dequeing methods (offer, poll, remove() and add); 
linear time for the remove(Object) and contains(Object) methods; 
and constant time for the retrieval methods (peek, element, and size).

(https://stackoverflow.com/questions/12719066/priority-queue-remove-complexity-time):
remove() -> This is to remove the head/root, it takes O(logN) time.
remove(Object o) -> This is to remove an arbitrary object. Finding this object takes O(N) time, and removing it takes O(logN) time.

Detail implementation of heap: https://en.wikipedia.org/wiki/Binary_heap




*/