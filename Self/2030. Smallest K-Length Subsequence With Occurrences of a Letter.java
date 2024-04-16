/** 2024/4/15
Similar question as 316, where we can use stringBuilder directly as a stack. But this question has a lot more edge cases:
* the condition for pop is different based on whether curChar is letter.
* we should keep count of nonletter chars and depending on it to see whether we can push curChar into the stack.
Time O(n) space O(n)


 */
class Solution {
    public String smallestSubsequence(String s, int k, char letter, int repetition) {
        int count = 0;
        for (char c: s.toCharArray()) {
            if (c == letter) {count++;}
        }
        StringBuilder stack = new StringBuilder();
        int nonletter = 0;
        for (int i = 0; i < s.length(); i++) {
            while (stack.length() != 0 && stack.charAt(stack.length()-1) > s.charAt(i) && stack.length() + s.length() - i > k && (stack.charAt(stack.length()-1) != letter || count > repetition)) {
                char pop = stack.charAt(stack.length()-1);
                stack.deleteCharAt(stack.length() - 1);
                if (pop == letter) {
                    count--;
                } else {
                    nonletter--;
                }               
            }
            if (stack.length() < k && (s.charAt(i) == letter || nonletter < k - repetition)) {
                stack.append(s.charAt(i));
                if (s.charAt(i) != letter) {nonletter++;}
            }else if (s.charAt(i) == letter) {
                count--;
            }
        }
        return stack.toString();
    }
}