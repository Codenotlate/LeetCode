/**
2024/3/13 self:
Sliding window, a maxLen to record the current max length, a map to store the char and frequency within the current window.
left pointer starts at idx 0, right pointer starts at left.
move right pointer, adjust char count in map. Keep moving right pointer until find char already in map (count == 2), update maxLen with (right - left) if proper. 
Move left pointer and reduce the count in the map correspondingly.
Back to move right pointer again.
The loop stops when 1) right pointer out of length or 2) left pointer till the end length smaller or equal to maxLen.

Time O(n) space O(n)

---------------------------------
Learning:
=== better way for idea illustration from naive way to sliding window way.
    In the naive approaches, we repeatedly check a substring to see if it has duplicate character. But it is unnecessary. If a substring sij from index ii to j−1 is already checked to have no duplicate characters. We only need to check if s[j] is already in the substring sij.
    To check if a character is already in the substring, we can scan the substring, which leads to an O(n^2)algorithm. But we can do better.

    By using HashSet as a sliding window, checking if a character in the current can be done in O(1).
=== for M1, time O(2n)= O(n), space O(min(n, size of charset))
=== M2, optimized from M1, time O(n) [self: doubt on this, but should be slightly better than M1]. space the same as M1.
    The intuition is that instead of keep track of count, we can keep track of the index of the char. So when we move left pointer, we no longer need to move it one step a time, we can directly jump to dupIdx + 1.
=== If we have assumption on the charSet
    All previous implementations have no assumption on the charset of the string s.
    If we know that the charset is rather small, we can mimic what a HashSet/HashMap does with a boolean/integer array as direct access table. Though the time complexity of query or insertion is still O(1), the constant factor is smaller in an array than in a HashMap/HashSet. Thus, we can achieve a shorter runtime by the replacement here.

    Commonly used tables are:

    int[26] for Letters 'a' - 'z' or 'A' - 'Z'
    int[128] for ASCII
    int[256] for Extended ASCII
 */
// M1
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int left = 0;
        int right = 0;
        int maxLen = 0;
        Map<Character, Integer> countMap = new HashMap<>();
        while (s.length() - left > maxLen && right < s.length()) {
            char curChar = s.charAt(right);
            countMap.put(curChar, countMap.getOrDefault(curChar,0)+1);
            
            if(countMap.get(curChar) == 2) {
                maxLen = Math.max(maxLen, right - left);
                /*
                Below 'while' part can be optimized using:
                while (chars.get(r) > 1) {
                    char l = s.charAt(left);
                    chars.put(l, chars.get(l) - 1);
                    left++;
                }
                */
                while (left < right) {
                    char delChar = s.charAt(left);
                    countMap.put(delChar, countMap.get(delChar) - 1);
                    left++;
                    if (s.charAt(left-1) == curChar) {break;}
                }
            }
            right++;
        }
        maxLen = Math.max(maxLen, right - left);
        return maxLen;
    }
}
// M3
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int left = 0;
        int right = 0;
        int maxLen = 0;
        Map<Character, Integer> countMap = new HashMap<>();
        while (s.length() - left > maxLen && right < s.length()) {
            char curChar = s.charAt(right);
            if (countMap.containsKey(curChar)) {
                // this max is important, because with the jump move, we are not moving outdated count info for chars already been moved out of the cur window. Thus need this to assure left can only jump to right direction or stays.
                left = Math.max(left,countMap.get(curChar) + 1);
            }
            countMap.put(curChar, right);
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        return maxLen;
    }
}




































