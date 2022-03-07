/* Initial thought
note if k == num.len, return 0.
consider dp way. Each position has two choices, to be removed or not. starting from the tail, dp[i][k] represents the max number coming from num[0:i] with k digits to remove. dp[i][k] = min(dp[i-1][k-1], dp[i-1][k] * 10 + s[i])
init: k == 0, return number(s[0:i]), if i == 0, return 0.
time O(nk) space O(k)
note above dp way requires num to be given in some number range.

from solution:
first notice the left digits has more weight in deciding the min, thus moving starting from the left.
Also notice, whether we remove the number in pos i is determined by whether num[i] > nums[i+1]. because if nums[i] < num[i+1] and you remove pos i, it will only generate a larger number then removing num[i+1] instead. Also if num[i] == num[i+1] we won't remove i, instead we determine whether to remove [i+1] based on comparison between [i+1] and [i+2].
The third note is that if we remove pos i, we still need to compare i-1 with i+1, and remove i-1 if num[i-1] > num[i+1]. Thus we think about using stack-like structure to achieve it.

time O(n)
space O(n)

*/
class Solution {
    public String removeKdigits(String num, int k) {
        if (k == num.length()) {return "0";}
        StringBuilder stack = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            char curChar = num.charAt(i);
            while(stack.length() != 0 && stack.charAt(stack.length() - 1) > curChar && k > 0) {
                stack.deleteCharAt(stack.length()-1);
                k--;
            }
            stack.append(curChar);        
        }
        while (k-- > 0) {
            stack.deleteCharAt(stack.length()-1);
        }
        // note need to deal with leading zeros and don't forget the comparison for start and stack.length.
        int start = 0;
        while(start < stack.length() && stack.charAt(start) == '0') {start++;}
        
        return start < stack.length()? stack.substring(start): "0";
    }
}


// similar as idea in solution

































