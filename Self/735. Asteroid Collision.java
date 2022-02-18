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