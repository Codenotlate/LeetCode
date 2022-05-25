/*Thought from discussion
sliding window
key idea is: the number of changes(k) = substring size - maxCount of char in that window
Also since we want longest substring, thus when we reached some valid str, there's no need to check window with size smaller than current maxlen.
Another note: we use current window size - maxCount > k as the trigger to move left pointer, and we actually don't need to update maxCount for this move, because the move will only make a newmaxCount, that is <= maxCount, since the following window size >= current window size,  thus we can still use this as the trigger

*/
class Solution {
    public int characterReplacement(String s, int k) {
        int maxCount = 0; // represents the maxCount of char from s[0] till now
        int[] count = new int[26];
        int start = 0;
        for (int end = 0; end < s.length(); end++) {
            count[s.charAt(end) - 'A']++;
            maxCount = Math.max(maxCount, count[s.charAt(end) -'A']);
            if (end-start+1-maxCount > k) {
                count[s.charAt(start++)-'A']--;
            }
        }
        return s.length()-start;
    }
}


/* from discussion:
https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91271/Java-12-lines-O(n)-sliding-window-solution-with-explanation
Check the second comment in Java

another good post: https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91285/Sliding-window-similar-to-finding-longest-substring-with-k-distinct-characters

And a followup in the interview
https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91285/Sliding-window-similar-to-finding-longest-substring-with-k-distinct-characters/274788
*/