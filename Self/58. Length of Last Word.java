/*
Way 1: with built-in functions for string. split by space, the get the last element from the array to get length. O(n) way.
Way 2: two pointers without built-in. While it's space, keep moving until not space. The second pointer starts at the first pointer's position, and again move until space. Record the currentLength as p2-p1. Then have p1 = p2 and repeat the above process until p1 reaches the end of the string. return the last updated currentLength. O(n) way.

one optimization - we can just go from right to left since we only want the most right word.
 */
// M2
class Solution {
    public int lengthOfLastWord(String s) {
        int left = 0;
        int len = 0;
        while (left < s.length()) {
            while(left < s.length() && s.charAt(left) == ' ') {
                left++;
            }
            int right = left;
            while (right < s.length() && s.charAt(right) != ' ') {
                right++;
            }
            len = right - left > 0 ? right - left : len;
            left = right;
        }
        return len;
    }
}

//M2.1
class Solution {
    public int lengthOfLastWord(String s) {
        int left = s.length() - 1;
        int len = 0;
        while (left >= 0) {
            while(left >= 0 && s.charAt(left) == ' ') {
                left--;
            }
            int right = left;
            while (right >= 0 && s.charAt(right) != ' ') {
                right--;
            }
            len = left - right;
            if (len > 0) {return len;}
            left = right;
        }
        return len;
    }
}










