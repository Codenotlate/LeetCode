class MinStack {
    // using self built element class and built-in stack
    private class Element {
        int value;
        int curMin;

        Element(int x, int m) {
            value = x;
            curMin = m;
        }
    }
    /** initialize your data structure here. */
    private Stack<Element> stack;

    public MinStack() {
        stack = new Stack<>();
    }
    
    public void push(int x) {
        int min = x;
        if (!stack.isEmpty()) {
            min = Math.min(min, stack.peek().curMin);
        }
        stack.push(new Element(x, min));
    }
    
    public void pop() {
        stack.pop();
    }
    
    public int top() {
        return stack.peek().value;
    }             
    
    public int getMin() {
        return stack.peek().curMin;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */



// using two stack way

class MinStack {

    /** initialize your data structure here. */
    private Stack<Integer> values, mins;
    public MinStack() {
        values = new Stack<>();
        mins = new Stack<>();
    }
    
    public void push(int x) {
        if (mins.isEmpty() || x <= mins.peek()) {
            mins.push(x);
        } 
        values.push(x);
    }
    
    public void pop() {
        int x = values.pop();
        if (x == mins.peek()) {mins.pop();}
    }
    
    public int top() {
        return values.peek();
    }
    
    public int getMin() {
        return mins.peek();
    }
}


// not using built-in stack
class MinStack {

    private class Node {
        int value;
        int min;
        Node next;

        Node(int v, int m, Node n) {
            value = v;
            min = m;
            next = n;
        }
    }

    /** initialize your data structure here. */
    private Node head;

    public MinStack() {
       // do nothing
    }
    
    public void push(int x) {
        if (head == null) {
            head = new Node(x, x, null);
        } else {
            head = new Node(x, Math.min(head.min, x), head);
        }
    }
    
    public void pop() {
        head = head.next;
    }
    
    public int top() {
        return head.value;
    }
    
    public int getMin() {
        return head.min;
    }
}






// Phase3 self
// m1 : stack + Pair
class MinStack {
    // M1: use one stack + pair in stake
    Stack<Pair<Integer, Integer>> stack;

    public MinStack() {
        stack = new Stack<>();
    }
    
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new Pair(val, val));
        } else {
            int prevMin = stack.peek().getValue();
            prevMin = Math.min(prevMin, val);
            stack.push(new Pair(val, prevMin));
        }
    }
    
    public void pop() {
        stack.pop();
    }
    
    public int top() {
        return stack.peek().getKey();
    }
    
    
    public int getMin() {
        return stack.peek().getValue();
    }
}

// M2: use two stacks
class MinStack {

    Stack<Integer> stackNum;
    Stack<Integer> stackMin;

    public MinStack() {
        stackNum = new Stack<>();
        stackMin = new Stack<>();
    }
    
    public void push(int val) {
        int mini = val;
        if (!stackMin.isEmpty()) {
            mini = Math.min(mini, stackMin.peek());
        } 
        stackNum.push(val);
        stackMin.push(mini);
    }
    
    public void pop() {
        stackMin.pop();
        stackNum.pop();
    }
    
    public int top() {
        return stackNum.peek();
    }
    
    
    public int getMin() {
        return stackMin.peek();
    }
}

// M3: use self defined linkedList,element push to the head
class MinStack {
    
    private class Node {
        int num;
        int min;
        Node next;
        
        Node(int n, int m, Node nxt) {
            num = n;
            min = m;
            next = nxt;
        }
    }
    
    private Node dummy;
    

    public MinStack() {
        dummy = new Node(0, 0, null);
    }
    
    public void push(int val) {
        int mini = val;
        if (dummy.next != null) {
            mini = Math.min(mini, dummy.next.min);
        } 
        Node added = new Node(val, mini, dummy.next);
        dummy.next = added;
    }
    
    public void pop() {
        dummy.next = dummy.next.next;
    }
    
    public int top() {
        return dummy.next.num;
    }
    
    
    public int getMin() {
        return dummy.next.min;
    }
}





