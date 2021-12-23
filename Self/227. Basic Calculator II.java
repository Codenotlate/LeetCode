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