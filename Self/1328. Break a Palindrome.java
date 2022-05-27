/*Thought
if len == 1, return empty str.
two pointers from start and end, find the first pair where left != right that has char not equal to 'a', replace the left char to 'a' and return. If all 'a', change the last char to 'b'.
time O(n) space O(n)
*/
class Solution {
    public String breakPalindrome(String palindrome) {
        if (palindrome.length() == 1 ) {return "";}
        char[] chars = palindrome.toCharArray();
        int left = 0;
        int right = palindrome.length()-1;
        while (left < right) {
            if (chars[left] != 'a') {
                chars[left] = 'a';
                return new String(chars);
            }
            left++;
            right--;
        }   
        chars[chars.length-1] = 'b';
        return new String(chars);
    }
}