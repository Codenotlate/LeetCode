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




// Review - 23/3/9 M1 similar as above.
/*Thoughts
M1: use a stack to mimic the process. while stack peek equal to current pop number, keep popping it out, move popIdx to the next. Else (not equal), add push[pushIdx] into the stack and pushIdx++. If after one pass of pushed, the stack is empty, then return true else false.
time O(n) space O(n)


M2: Try to find an O(1) space way by not using an explicit stack. Directly use pushed array, but this way pushed array will be modified.
from solution: https://leetcode.com/problems/validate-stack-sequences/solutions/197685/c-java-python-simulation-o-1-space/
"""
should not count as O(1) space.
be more precise during interview: O(1) extra space, O(N) modified space
"""

 */
 // M1
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int popIdx = 0;
        int pushIdx = 0;
        while (popIdx < popped.length) {
            while (!stack.isEmpty() && stack.peek() == popped[popIdx]){
                stack.pop();
                popIdx++;
            }
            if (pushIdx >= pushed.length) {break;}
            stack.push(pushed[pushIdx]);
            pushIdx++;                       
        }
        return stack.isEmpty();
    }
}
// M2
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int stackTop = -1;
        int popIdx = 0;
        int pushIdx = 0;
        while (popIdx < popped.length) {
            while (stackTop != -1 && pushed[stackTop] == popped[popIdx]){
                stackTop--;
                popIdx++;
            }
            if (pushIdx >= pushed.length) {break;}
            pushed[stackTop+1] = pushed[pushIdx];
            stackTop++;
            pushIdx++;                       
        }
        return stackTop == -1;
    }
}




