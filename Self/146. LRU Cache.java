/* Memo
There's discussion under solution about how to solve it in an interview. Better show you know the linkedHashMap solution and also how to implement the doubleLinkedLIst + hashmap way. Analyze the time complexity and then ask interviewer which one they want you to implement.
For solution 2, the key idea is to use a doubleLinkedList to make the remove of the node in constant time.
*/

class LRUCache {
    class Node {
        int key;
        int value;
        Node prev;
        Node next;
    }
    
    private Map<Integer, Node> cacheMap = new HashMap<>();
    private int capacity;
    private Node dumHead, dumTail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        dumHead = new Node();
        dumTail = new Node();
        dumHead.next = dumTail;
        dumTail.prev = dumHead;
    }
    
    private void remove(Node cur) {
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
    }
    
    private void addFirst(Node cur) {
        cur.next = dumHead.next;
        cur.prev = dumHead;
        dumHead.next = cur;
        cur.next.prev = cur;
    }
    
    public int get(int key) {
        if (cacheMap.keySet().contains(key)) {
            Node cur = cacheMap.get(key);
            remove(cur);
            addFirst(cur);
            return cur.value;
        } 
        return -1;
    }
    
    public void put(int key, int value) {
        if (cacheMap.keySet().contains(key)) {
            Node cur = cacheMap.get(key);
            cur.value = value;
            remove(cur);
            addFirst(cur);
        } else {
            Node newCur = new Node();
            newCur.key = key;
            newCur.value = value;
            cacheMap.put(key, newCur);
            addFirst(newCur);
            if (cacheMap.size() > capacity) {
                Node deleted = dumTail.prev;
                cacheMap.remove(deleted.key);
                remove(deleted);
            }
        }
    }
}

/*
Discussion on why we need double LinkedList instead of single LL:
You can use a singly linked list!
Think about this: The only operations we ever do on the linked list are add node and remove node.

Important: We never need to traverse (search) the linked list because the dictionary allows us to instantly look up the location of each node! We can add nodes as long as we keep a pointer to the tail since we only ever add to the end of the list.

However, there will be one tricky thing you must handle if you use a singly linked list. Let's say we want to delete a node in the list. (I'll call it currNode for example) We can look up the address of currNode from the dictionary. Since the list is singly linked, we don't know what the previous node is. So the only method to delete currNode is to copy the contents (val , next) of the next node into the current node, and delete the next node. The tricky part is, now currNode will have the contents of the next node, but the dictionary will have the old key, value pair. So you have to delete that key, value pair and make a new one for the updated currNode.
 */

// another way directly use LinekedHashMap structure in Java
// https://leetcode.com/problems/lru-cache/discuss/45939/Laziest-implementation%3A-Java's-LinkedHashMap-takes-care-of-everything
class LRUCache {
    private LinkedHashMap<Integer, Integer> map;
    private int SIZE;
    public LRUCache(int capacity) {
        map = new LinkedHashMap<>();
        SIZE = capacity;
    }

    public int get(int key) {
        if(map.containsKey(key)) {
            int value = map.remove(key);
            map.put(key, value);
            return value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            map.remove(key);
        }else if(map.size() + 1 > SIZE) {
            map.remove(map.keySet().iterator().next());
        }
        map.put(key, value);
    }
}





// Review - similar idea as above(double LL + map <key, node>), but above way more elegant
class LRUCache {
    class Node {
        int val;
        int key;
        Node prev;
        Node next;
    }
    
    Node dummy;
    Node tail;
    int capacity;
    Map<Integer, Node> map;

    public LRUCache(int capacity) {
        dummy = new Node();
        tail = dummy;
        this.capacity = capacity;
        map = new HashMap<>();
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {return -1;}
        Node cur = map.get(key);
        if (cur == tail) {return cur.val;}
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        tail.next = cur;
        cur.prev = tail;
        tail = tail.next;
        return cur.val;        
    }
    
    public void put(int key, int value) {
        if (!map.containsKey(key)) {
            Node newnode = new Node();
            newnode.key = key;
            newnode.val = value;
            tail.next = newnode;
            newnode.prev = tail;
            tail = tail.next;
            map.put(key, tail);
            
            if (map.keySet().size() > capacity) {
                Node deleted = dummy.next;
                dummy.next = deleted.next;
                dummy.next.prev = dummy;
                map.remove(deleted.key);
            }
        } else {
            Node cur = map.get(key);
            cur.val = value;
            if (cur == tail) {return;}
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
            tail.next = cur;
            cur.prev = tail;
            tail = tail.next;
        }
    }
}



// Review - error: don't forget to remove the key in the map as well
/* Thought
double LL + hashmap (key -> node in LL)
 */
class LRUCache {
    private class Node {
        int value;
        int key;
        Node prev;
        Node next;
    }
    Map<Integer, Node> keyMap;
    Node dummyHead;
    Node dummyTail;
    int currentSize;
    int capSize;

    public LRUCache(int capacity) {
        capSize = capacity;
        currentSize = 0;
        dummyHead = new Node();
        dummyTail = new Node();
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
        keyMap = new HashMap<>();
    }
    
    public int get(int key) {
        if (!keyMap.containsKey(key)) {return -1;}
        int res = keyMap.get(key).value;
        // update the order of the node
        Node curNode = keyMap.get(key);
        removeNode(curNode);
        insertEnd(curNode);
        return res;
         
    }
    
    public void put(int key, int value) {
        Node curNode;
        if (keyMap.containsKey(key)) {
            curNode = keyMap.get(key);
            curNode.value = value;
            removeNode(curNode);
            insertEnd(curNode);
        } else {
            curNode = new Node();
            curNode.key = key;
            curNode.value = value;
            keyMap.put(key, curNode);
            insertEnd(curNode);
            currentSize++;
            if (currentSize > capSize) {
                //!!! don't forget this line!
                keyMap.remove(dummyHead.next.key);
                removeNode(dummyHead.next);               
                currentSize--;
            }
        }
        
        
    }

    private void removeNode(Node cur) {
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        // cur.prev = null;
        // cur.next = null;
    }

    private void insertEnd(Node cur) {
        dummyTail.prev.next = cur;
        cur.prev =dummyTail.prev;
        dummyTail.prev = cur;
        cur.next = dummyTail;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */






