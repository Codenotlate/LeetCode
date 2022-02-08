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



// Reveiw self
// note use StringBuilder instead of string, and remember to use StringBuilder.reverse() method.
// Integer.valueOf(StringBuilder.toString())
// String.valueOf(char)
class Solution {
    /* Initial thought
    Using stacks for the nested condition. 
    method1: Can use one stack for char, when encounter ']', we need to pop out the string until '['. And then pop out all digit chars to form the number.
    method2: can use two stacks, one for number, the other for letters and []. Convert digit chars into number when they are added to the stack
    */
    // try method2
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<String> charStack = new Stack<>();
        int i = 0;
        while (i < s.length()) {
            StringBuilder numStr = new StringBuilder();
            while(i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                numStr.append(s.charAt(i));
                i++;            
            }
            if(numStr.length() != 0){numStack.push(Integer.valueOf(numStr.toString()));}
            if (s.charAt(i) == ']') {
                StringBuilder str = new StringBuilder();
                while(!charStack.peek().equals("[")) {
                    str.append(charStack.pop());
                }
                charStack.pop(); // pop out [
                int k = numStack.pop();
                while(k-- > 0) {
                    charStack.push(str.toString());
                }                
            } else {
                charStack.push(String.valueOf(s.charAt(i)));
            }
            i++;
        }
        
        StringBuilder res = new StringBuilder();
        while(!charStack.isEmpty()) {
            res.append(charStack.pop());
        }
        res.reverse();
        return res.toString();
    }
}