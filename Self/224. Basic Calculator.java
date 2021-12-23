// Phase3 from solution
// M1 source: https://leetcode.com/problems/basic-calculator/discuss/62362/JAVA-Easy-Version-To-Understand!!!!!

class Solution {
    public int calculate(String s) {
        int res = 0;
        int sign = 1;
        int num = 0;
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
                while(i + 1 < s.length() && Character.isDigit(s.charAt(i+1))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                res += sign * num;
                num = 0;
            } else if (c == '+' || c == '-') {
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;
            } else if (c == ')') {
                res = res * stack.pop() + stack.pop();
            }
            
        }
        return res;
    }
}




// Phase3 from solution
// M2 source(first comment): https://leetcode.com/problems/basic-calculator/discuss/62361/Iterative-Java-solution-with-stack
class Solution {
    public int calculate(String s) {
        int res = 0;
        int num = 0;
        int sign = 1;
        Stack<Integer> signStack = new Stack<>();
        signStack.push(sign);
        
        for (char c: s.toCharArray()) {
            if (c <= '9' && c >= '0') {
                num = num * 10 + c - '0';
            } else if (c =='+' || c == '-') {
                res += sign * num;
                num = 0;
                sign = signStack.peek() * (c == '+' ? 1 : -1);
            } else if (c == '(') {
                // push the sign outside this () into stack
                signStack.push(sign);
            } else if (c == ')') {
                signStack.pop();
            }
        }
        return res + sign * num;
    }
}













// another unsuccessful answer using two stacks (failed case: "1-(-2)")
// the problem is you can't tell which one number is in current parenthese
// class Solution {
//     public int calculate(String s) {
//         Stack<Integer> numStack = new Stack<>();
//         Stack<Character> charStack = new Stack<>();
        
//         String numStr = "";
//         int i = 0;
//         while(i < s.length()) {
//             // don't forget to skip space char
//             if (s.charAt(i) == ' ') {i++; continue;}
//             while (i < s.length() && s.charAt(i)>= '0' && s.charAt(i) <= '9') {
//                 numStr += s.charAt(i);
//                 i++;
//             }
//             if (!numStr.equals("")) {
//                 numStack.push(Integer.valueOf(numStr)); 
//                 numStr = "";
//             } else {
//                 if (s.charAt(i) != ')') {
//                     charStack.push(s.charAt(i));
//                     i++;
//                     continue;
//                 }
//                 // when char is ')'
//                 int sum = 0;
//                 while (!charStack.isEmpty() && !numStack.isEmpty()) {
//                     char ope = charStack.pop();
//                     int num = numStack.pop();
//                     if (ope == '+' || ope == '(') {sum += num;}
//                     else {sum -= num;}
//                     if (ope == '(') {break;}
//                 }
//                 numStack.push(sum); 
//                 i++;
//             }         
//         }
//         int res = 0;
//         while (!charStack.isEmpty()) {
//             char ope = charStack.pop();
//             int num = numStack.pop();
//             if (ope == '+') {res += num;}
//             else {res -= num;}
//         }
//         return numStack.isEmpty() ? res : res + numStack.pop();
        
        
        
        
        
        
        
//     }
// }
































// // naive unsuccessful one
// class Solution {
//     // phase3 M1:using one stack of char
//     public int calculate(String s) {
//         // loop s, and put char into stack
//         Stack<Character> stack = new Stack<>();
//         for (char c: s.toCharArray()) {
//             if (c == ' ') {continue;}
//             if (c != ')') {stack.push(c);}
//             else {
//                 // pop out until '(' and put into a linkedList (add to the head)
//                 List<Character> charList = new LinkedList<>();
//                 while (!stack.isEmpty()) {
//                     char pop = stack.pop();
//                     if (pop == '(') {break;}
//                     charList.add(0, pop);
//                 }
//                 // loop the charList, if number more than one digit, keep reading
//                 int i = 0;
//                 int sum = 0;
//                 int flag = 1;
                
//                 while ( i < charList.size()) {
//                     if (charList.get(i) == '+') {
//                         sum += 
//                     }
//                     else if (charList.get(i) == '-') {flag *= -1; i++;}
//                     else {
//                         StringBuilder numStr = new StringBuilder();
//                        while (i < charList.size() && charList.get(i) != '+' && charList.get(i) != '-') {
//                            numStr.append(charList.get(i));
//                            i++;
//                        }
//                         num = Integer.valueOf(numStr.toString());
                        
//                     }
//                 }
//                 // add sum as reverse char back to stack
//                 for(char x: ("" + sum).toCharArray()) {
//                     stack.push(x);
//                 }
//             }
//         }
        
//         List<Character> charList = new LinkedList<>();
//         while (!stack.isEmpty()) {
//             charList.add(0, stack.pop());
//         }
//         // loop the charList, if number more than one digit, keep reading
//         int i = 0;
//         int sum = 0;
//         int flag = 1;
       
//         while ( i < charList.size()) {
//             if (charList.get(i) == '+') {i++;}
//             else if (charList.get(i) == '-') {flag *= -1; i++;}
//             else {
//                 StringBuilder numStr = new StringBuilder();
//                while (i < charList.size() && charList.get(i) != '+' && charList.get(i) != '-') {
//                    numStr.append(charList.get(i));
//                    i++;
//                }
//                 sum += flag * Integer.valueOf(numStr.toString());
//             }
//         }
//         return sum;
//     }
// }