class MyQueue {
    // amortized O(1) per operation
    /** Initialize your data structure here. */
    private Stack<Integer> in, out;

    public MyQueue() {
        in = new Stack<>();
        out = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        in.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.pop();
        
    }
    
    /** Get the front element. */
    public int peek() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

// Phase3 self
class MyQueue {
    Stack<Integer> start, end;

    public MyQueue() {
        start = new Stack<>();
        end = new Stack<>();
    }
    
    public void push(int x) {
        end.push(x);
    }
    
    public int pop() {
        if (start.isEmpty()) {
            while (!end.isEmpty()) {
                start.push(end.pop());
            }
        }
        return start.pop();
    }
    
    public int peek() {
        if (start.isEmpty()) {
            while (!end.isEmpty()) {
                start.push(end.pop());
            }
        }
        return start.peek();
    }
    
    public boolean empty() {
        return start.isEmpty() && end.isEmpty();
    }
}
