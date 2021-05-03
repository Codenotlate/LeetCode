class MyLinkedList {
    private class Node {
        public int val;
        public Node next;
        public Node(int val) {this.val = val; this.next = null;}
        public Node(int val, Node next) {this.val = val; this.next = next;}

    }

    private Node head;
    private Node tail;
    private int size;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private Node getNode(int index) {
        if (index >= this.size) {return null;}
        Node p = this.head;
        while (index > 0) {
            p = p.next;
            index--;
        }
        return p;
    }

    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        Node p = getNode(index);
        if (p == null) {return -1;}
        return p.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        if (this.head == null) {
            this.head = this.tail = new Node(val);
        } else {
            this.head = new Node(val, this.head);
        }
        this.size++;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if (this.tail == null) {
            this.head = this.tail = new Node(val);
        } else {
            this.tail.next = new Node(val);
            this.tail = this.tail.next;
        }
        this.size++;
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index > this.size || index < 0) {return;}
        if (index == 0) {
            addAtHead(val);
        } else if (index == this.size) {
            addAtTail(val);
        } else {
            Node pre = getNode(index - 1);
            pre.next = new Node(val, pre.next);
            this.size++;
        }
        
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index >= this.size || index < 0) {return;}
        if (index == 0) {
            this.head = this.head.next;
            if (index == this.size - 1) {this.tail = null;}
        } else {
            Node pre = getNode(index - 1);
            pre.next = pre.next.next;
            if (index == this.size - 1) {this.tail = pre;}
        }
        this.size--; 
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */