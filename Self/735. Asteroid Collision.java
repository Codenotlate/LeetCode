class Solution {
    /* Initial idea
    Use stack. loop the array.
    Put positive value into the stack. if we meet negative value, compare with the positive value in the stack till abs(peek) > abs(cur neg) or stack is empty. if stack is empty, put neg value into the result.
    time O(n) space O(n)
    */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int n: asteroids) {
            if (stack.isEmpty() || stack.peek() < 0 || n > 0) {stack.push(n);}
            else {
                while(!stack.isEmpty() && stack.peek() > 0 && n < 0) {
                    if (stack.peek() < Math.abs(n)) {stack.pop();}
                    else if (stack.peek() == Math.abs(n)) {stack.pop(); n = 0;}
                    else {n = 0;}
                }
                if (n < 0) {stack.push(n);}
            }
        }
        
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {res[i] = stack.pop();}
        return res;
    }
}

// similar idea but more concise code
// https://leetcode.com/problems/asteroid-collision/discuss/109694/JavaC%2B%2B-Clean-Code





// Review
/*Thought
Using stack. while cur num is negative and stack is not empty with peek value positive, thencompare abs(peek) with abs(cur)。 If abs(peek) > abs(cur), label cur = 1001, representing cur is exploded., break the while loop. if (==), pop out from stack and cur = 1001, nreak the hwile loop. if (<), pop out from stack, continue the while loop.
Outside the while loop, if cur != 1001, meaning cur didn't explode, then add cur to stack. 
time O(n) space O(n) for stack
*/
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int cur: asteroids) {
            while(!stack.isEmpty() && cur < 0 && stack.peek() > 0) {
                int diff = Math.abs(stack.peek())- Math.abs(cur);
                if (diff > 0) {cur = 1001; break;}
                else if (diff == 0) {stack.pop(); cur = 1001; break;}
                else {stack.pop();}
            }
            if (cur != 1001) {stack.push(cur);}
        }
        
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {res[i] = stack.pop();}
        return res;
    }
}