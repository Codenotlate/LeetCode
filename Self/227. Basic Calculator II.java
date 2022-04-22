// Phase3 self
// similar to 224

class Solution {
    public int calculate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> chars = new Stack<>();
        chars.push('+');
        
        int i = 0;
        int num = 0;
        while ( i < s.length()) {
            char c = s.charAt(i);
            if (c == ' ') {i++; continue;}
            if (Character.isDigit(c)) {
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                if (!chars.isEmpty() && (chars.peek() == '/' || chars.peek() == '*')) {
                    int num1 = nums.pop();
                    char ope = chars.pop();
                    if (ope == '/') {nums.push(num1 / num);}
                    else {nums.push(num1 * num);}
                } else {
                    nums.push(num);
                }
                num = 0;
            } else {
                chars.push(c);
                i++;
            }
        }
        
        int res = 0;
        while (!nums.isEmpty()) {
            res += nums.pop() * (chars.pop() == '+' ? 1 : -1);
        }
        
        return res;
        
        
    }
}


// source page: https://leetcode.com/problems/basic-calculator-ii/solution/
// M2 from solution : optimize to one stack
// time O(n) space O(n)
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int curNum = 0;
        char lastSign = '+';
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + c - '0';
            } else if (c == ' ' && i < s.length() - 1) { // if ' ' is the last char in s, we still go to below block
                continue;
            } 
            if (i == s.length() - 1 || !Character.isDigit(c)) {
                if(lastSign == '+') {
                    stack.push(curNum);
                } else if (lastSign == '-') {
                    stack.push(-curNum);
                } else if (lastSign == '*') {
                    stack.push(stack.pop() * curNum);
                } else if (lastSign == '/') {
                    stack.push(stack.pop() / curNum);
                }
                curNum = 0;
                lastSign = c;
            }
        }
        
        while (!stack.isEmpty()) {curNum += stack.pop();}
        return curNum;
    }
}




// M3: optimized to use space O(1), use lastNum to track the stack peek. And add those element in stack below peek to res directly.
class Solution {
    public int calculate(String s) {
        int curNum = 0;
        char lastSign = '+';
        int lastNum = 0;
        int res = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + c - '0';
            } else if (c == ' ' && i < s.length() - 1) {
                continue;
            } 
            if (i == s.length() - 1 || !Character.isDigit(c)) {
                if(lastSign == '+') {
                    res += lastNum;
                    lastNum = curNum;
                } else if (lastSign == '-') {
                    res += lastNum;
                    lastNum = -curNum;
                } else if (lastSign == '*') {
                    lastNum = lastNum * curNum;
                } else if (lastSign == '/') {
                    lastNum = lastNum / curNum;
                }
                curNum = 0;
                lastSign = c;
            }
        }
        
        res += lastNum;
        return res;
    }
}













// Review
// M1: naive way: using two stacks and need to trim s to remove tail spaces. time O(n) space O(n)
class Solution {
    public int calculate(String s) {
        s = s.trim();
        Stack<Integer> numStack = new Stack<>();
        Stack<Character> opeStack = new Stack<>();
        int curnum = 0;
        opeStack.add('+');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {continue;}
            if (Character.isDigit(c)) {curnum = curnum * 10 + c - '0';}
            if(i == s.length() - 1 || !Character.isDigit(c)) {
                numStack.push(curnum);
                curnum = 0;
                char prevOpe = opeStack.peek();
                if (prevOpe == '*') {
                    int num2 = numStack.pop();
                    int num1 = numStack.pop();
                    numStack.push(num1 * num2);
                    opeStack.pop();
                } else if (prevOpe == '/') {
                    int num2 = numStack.pop();
                    int num1 = numStack.pop();
                    numStack.push(num1 / num2);
                    opeStack.pop();
                }
                opeStack.push(c);
            }
        }
        
        int res = 0;
        numStack.push(curnum);
        while (!numStack.isEmpty()) {
            char c = opeStack.pop();
            curnum = numStack.pop();
            if (c == '+') {res+=curnum;}
            else {res -= curnum;}
            
        }
        return res;
    }
}


// M2: improved way: using one stack and no need for trim
/*improve1: notice in the end, all the ope left in opeStack is either + or -. Thus if we adjust - to + (-num). Then all remaining operands will be '+'. And thus we don't need the opeStack.
improve2: remove trim(), change the logic to accomdate end with spaces case.
*/
class Solution {
    public int calculate(String s) {
        Stack<Integer> numStack = new Stack<>();
        char prevOpe = '+';
        int curnum = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {curnum = curnum * 10 + c - '0';}
            if(i == s.length() - 1 || (!Character.isDigit(c) && c != ' ')) {
                if (prevOpe == '*') {
                    numStack.push(numStack.pop() * curnum);
                } else if (prevOpe == '/') {
                    numStack.push(numStack.pop() / curnum);
                } else if (prevOpe == '-') {
                    numStack.push(-curnum);
                } else {
                    numStack.push(curnum);
                }
                prevOpe = c;
                curnum = 0;
            }
        }
        
    
        while (!numStack.isEmpty()) {
            curnum += numStack.pop();          
        }
        return curnum;
    }
}

// M3: further improved way: O(n) time O(1) space
/*improve1: Since all remaining operands will be '+'. And thus we even don't need the numStack. we simply check the prevnum and directly add the result to final result.
*/
class Solution {
    public int calculate(String s) {
        int prevnum = 0;
        char prevOpe = '+';
        int curnum = 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {curnum = curnum * 10 + c - '0';}
            if(i == s.length() - 1 || (!Character.isDigit(c) && c != ' ')) {
                if (prevOpe == '*') {
                    prevnum *= curnum;
                } else if (prevOpe == '/') {
                    prevnum /= curnum;
                } else if (prevOpe == '-') {
                    res += prevnum;
                    prevnum = -curnum;
                } else {
                    res += prevnum;
                    prevnum = curnum;
                }
                prevOpe = c;
                curnum = 0;
            }
        }
        
    
        res += prevnum;
        return res;
    }
}