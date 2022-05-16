/*Thought
M1: time O(n) space O(n) using stack
stack of int, when encounter (, insert 0 into the stack. When encounter ), pop out until 0, max(2 * popsum, 1) and push back to stack. In the end, return the sum of remaining numbers in the stack.

M2: time O(n) space O(1) using count of ( and view the calculation in a different way: in M1, we do 2 * (A+B). In M2, we do 2*A + 2 * B.
so when we encounter ( we do count++, when encounter ) we do count--. ALso check if it is the most central one, meaning s[i-1] == (, then res += max(1, 2 * count).
*/
// M1: stack way, time & space O(n)
class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {stack.push(0);}
            else {
                int popsum = 0;
                while (stack.peek() != 0) {
                    popsum += stack.pop();
                }
                stack.pop();
                stack.push(Math.max(1, 2*popsum));
            }
        }
        while (!stack.isEmpty()) {res += stack.pop();}
        return res;
    }
}



// M2: O(1) space way
class Solution {
    public int scoreOfParentheses(String s) {
        int count = 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {count++;}
            else {
                count--;
                if ( i > 0 && s.charAt(i-1) == '(') {
                    res += Math.pow(2, count);
                }
            }
            
        }
        return res;
    }
}