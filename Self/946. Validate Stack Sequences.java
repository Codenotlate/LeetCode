/*Thought
Use a stack to mimic the process, add the numebr in pushed to the stack, and a pointer starts at 0 for popped. while the stack.peek() == cur popnum, then we need to pop it out of the stack, and move popIdx++. When we processed all num in pushed, check whether the stack is empty at the end.
time O(n) space O(n)

*/
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int popIdx = 0;
        for (int num: pushed) {
            stack.push(num);
            while(!stack.isEmpty() && stack.peek() == popped[popIdx]) {
                stack.pop();
                popIdx++;
            }
            
        }
        return stack.isEmpty();
    }
}