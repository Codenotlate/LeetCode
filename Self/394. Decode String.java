class Solution {
    // Phase3 self
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        for (char c: s.toCharArray()) {
            StringBuilder sub = new StringBuilder();
            StringBuilder countStr = new StringBuilder();
            if (c != ']') {
                stack.push(String.valueOf(c));
            } else {
                while (!stack.peek().equals("[")) {
                    sub.append(stack.pop());
                }
                stack.pop(); // pop out "["
                // Note the number could be more than one digit
                while (!stack.isEmpty()) {
                    String peek = stack.peek();
                    if(peek.length() != 1 || peek.charAt(0) < '0' || peek.charAt(0) > '9') {break;}
                    countStr.append(stack.pop());
                }
                countStr.reverse();
                int count = Integer.valueOf(countStr.toString());
                while (count-- > 0) {
                    stack.push(sub.toString());
                }
            }
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        res.reverse();
        return res.toString();
    }
}

// can check other solutions in https://leetcode.com/problems/decode-string/solution/