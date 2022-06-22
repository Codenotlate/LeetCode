/*Thought
M1: two passes. go through the string forward for first pass, do count++ for ( else count--. when count = 0, update res, when count < 0, rest count = 0, and update left pointer to cur pos. Apply same process again from backwards.
time O(n) space O(1)

M2: stack way. Thought with O(n) space, but also O(n) time. Need to put -1 into stack first.
*/
//M1
class Solution {
    public int longestValidParentheses(String s) {
        int res = 0;
        int left = 0;
        int count = 0;
        for (int right = 0;right < s.length(); right++) {
            if (s.charAt(right) == '(') {count++;}
            else {
                count--;
                if (count == 0) {res = Math.max(res, right-left +1);}
                else if (count < 0) {
                    count = 0;
                    left = right + 1;
                }
            }
        }
        left = s.length() - 1;
        count = 0;
        for (int right = s.length()-1; right >= 0; right--) {
            if (s.charAt(right) == ')') {count++;}
            else {
                count--;
                if (count == 0) {res = Math.max(res, left - right + 1);}
                else if (count < 0) {
                    count = 0;
                    left = right-1;
                }
            }
        }
        return res;
    }
}

// M2
class Solution {
    public int longestValidParentheses(String s) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {stack.push(i); continue;}
            if (stack.peek() != -1 && s.charAt(stack.peek()) == '(') {
                stack.pop();
                res = Math.max(res, i-stack.peek());
            } else {
                stack.push(i);
            }
        }
        return res;
    }
}