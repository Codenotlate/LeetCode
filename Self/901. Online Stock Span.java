//23/2/2 - self done (similar as the optimal solution, they used int[] or pair for stack)
/* Thoughts
Need to make the next amortized O(1), use a monotonic stack with index number inserted and in descending order.
*/
class StockSpanner {
    Stack<Integer> stack;
    List<Integer> list;
    int size;

    public StockSpanner() {
        size = -1;
        stack = new Stack<>();
        stack.push(-1);
        list = new ArrayList<>();
    }
    
    public int next(int price) {
        list.add(price);
        size++;
        while(stack.peek() != -1 && list.get(stack.peek()) <= price) {
            stack.pop();
        }
        int res = size - stack.peek();
        stack.push(size);
        return res;
    }
}

