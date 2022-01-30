class Solution {
    // Phase3 self
    // time O(n) space O(n)
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        Set<String> opes = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
        for (String s: tokens) {
            if (!opes.contains(s)) {
                stack.push(Integer.valueOf(s));
            } else {
                int num1 = stack.pop();
                int num2 = stack.pop();
                if (s.equals("+")) {
                    stack.push(num1 + num2);
                } else if (s.equals("-")) {
                    stack.push(num2 - num1);
                } else if (s.equals("*")) {
                    stack.push(num1 * num2);
                } else {
                    stack.push(num2 / num1);
                }
            }
        }
        return stack.pop();
    }
}  



// Review self
class Solution {
    /*initial thought
    use stack, when meet operator, pop two element out and add the result back to stack again
    time O(n) spaceO(n)
    
    */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s: tokens) {
            if (s.equals("+")) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.add(num1 + num2);
            } else if (s.equals("-")) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.add(num1 - num2);
            } else if (s.equals("*")) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.add(num1 * num2);
            } else if (s.equals("/")) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.add(num1 / num2);
            } else {
                stack.add(Integer.valueOf(s));
            }
        }
        return stack.pop();
    }
}