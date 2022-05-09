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








// Review
/*Thought
Naive way: using only onew stack of char. adding both number and char to it.
Improved way: using two separate stacks for number and for stringBuilder. For each pair of number and string, we keep the string in curStr(not in stack), and we pop the number from the number stack each time. We choose this pattern because using '[' to indentify when to add current number and curStr is a clearer way. If we using char or number to identify, we may need to use while instead of curent 'if' logic.
Below solution is the same as solution M2:
Time Complexity: O(maxK⋅n), where maxK is the maximum value of k and n is the length of a given string s. We traverse a string of size nn and iterate kk times to decode each pattern of form k[string]. This gives us worst case time complexity as O(maxK⋅n).
Space Complexity: O(m+n), where mm is the number of letters(a-z) and nn is the number of digits(0-9) in string ss. In worst case, the maximum size of stringStack and countStack could be mm and nn respectively.

Can try solution M3: recursive way later

*/
class Solution {
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<StringBuilder> strDeque = new Stack<>();
        int curNum = 0;
        StringBuilder curStr = new StringBuilder();
        for (char c: s.toCharArray()) {
            if(Character.isDigit(c)) {curNum = curNum * 10 + c - '0';}
            else if (c >= 'a' && c <= 'z') {curStr.append(c);}
            else if (c == '[') {
                numStack.add(curNum);
                curNum = 0;
                strDeque.add(curStr); // an empty str is actually a sign for the start of one [] block
                curStr = new StringBuilder();
            } else {
                int k = numStack.pop();
                StringBuilder nextcurStr = strDeque.pop();
                while(k-- > 0) {nextcurStr.append(curStr);}
                curStr = nextcurStr;
            }
        }
        return curStr.toString();
    }
}







// Review - same as above
/*Thought
1[a]3[ab2[c]]13[i]ef

numstack: 
SB: [

curstr = a
curnum = 0

we need a numstack. we also need a StringBuilder stack.

if number: curnum = curnum * 10 + c-'0';
if [: push curnum to numstack. curnum = 0, push curstr, curstr = "". push [ to curstr
if char: append to curstr.
if ]: pop out k from numstack, then curstr = strstack.pop() append curstr k times.

*/
class Solution {
    public String decodeString(String s) {
        int curnum = 0;
        StringBuilder curstr = new StringBuilder();
        Stack<Integer> numstack = new Stack<>();
        Stack<StringBuilder> strstack = new Stack<>();
        
        for (char c: s.toCharArray()) {
            if (Character.isDigit(c)) {
                curnum = curnum * 10 + c - '0';
            } else if (c == '[') {
                numstack.push(curnum); 
                curnum = 0;
                strstack.push(curstr); 
                curstr = new StringBuilder();
            } else if (c == ']') {
                int k = numstack.pop();
                StringBuilder temp = strstack.size() > 0? strstack.pop() : new StringBuilder();
                while (k-- > 0) {temp.append(curstr);}
                curstr = temp;
            } else {
                curstr.append(c);
            }
        }
        
        return curstr.toString();
    }
}