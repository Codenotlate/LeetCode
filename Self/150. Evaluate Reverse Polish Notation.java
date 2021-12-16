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