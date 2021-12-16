class MyStack {

    // normal way with O(n) push

    private Queue queue;
    /** Initialize your data structure here. */
    public MyStack() {
        queue = new LinkedList();
    }
    
    /** Push element x onto stack. */
    
    public void push(int x) {
        queue.add(x);
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.add(queue.remove());
        }
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return (int) queue.remove();
    }
    
    /** Get the top element. */
    public int top() {
        return (int) queue.peek();
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}

// another way for follow up: amortized O(1)
// see: https://leetcode.com/problems/implement-stack-using-queues/discuss/62522/O(1)-purely-with-queues



/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */


// Phase3 self
class MyStack {
    LinkedList<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }
    
    public void push(int x) {
        queue.add(x);
        int size = queue.size();
        while (size-- > 1) {
            queue.add(queue.poll());
        }
        
    }
    
    public int pop() {
        return queue.poll();
        
    }
    
    public int top() {
        return queue.peek();
    }
    
    public boolean empty() {
        return queue.isEmpty();
    }
}

