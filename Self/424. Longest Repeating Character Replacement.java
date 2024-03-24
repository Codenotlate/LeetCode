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
-- self comment: here it checks all letters to update the maxCharCount which in unnecessary step. Although it's O(26) ~ O(1) here.

And a followup in the interview
https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91285/Sliding-window-similar-to-finding-longest-substring-with-k-distinct-characters/274788
-- self comment: if we need to return the result substring, we can use the way in above post, where we check O(26) to update maxCount intime.
*/



/* 2024/3/23
Similar idea as above. Two things not figured out in the first place:
1) we only need to record maxCount, no need for nonMaxCount, as nonMaxCount = current window size - maxCount
2) we don't need to update maxCount in time. i.e. when we move left out of window, we don't need to update maxCount to the most accurate one (maybe from another letter). Which also means we don't need to track maxCountLetter.
The reason being (from discussion):
we focus on "longest". The length of valid substring is determined by what?  Max Occurrence + k
We only need to update max occurrence when it becomes larger, because only that can we generate a longer valid substring.
If we can't surpass the historic max occurrence, then we can not generate a longer valid substring from current "start", I mean the new windows's left bound. To some extend, this becomes a game to find max occurrence.
Or , a better understanding is:
"A game to eliminate inferior 'left bounds'.

*/

class Solution {
    public int characterReplacement(String s, int k) {
        int maxLen = 0;
        int left = 0;
        int right = 0;
        char maxChar = '_';
        int maxCharCount = 0;
        int[] map = new int[26];
        while(right < s.length()) {
            char cur = s.charAt(right);
            map[cur-'A']++;
            if (map[cur-'A']>maxCharCount) {
                maxCharCount = map[cur-'A'];
                maxChar = cur;
            }
            if (right - left + 1 - maxCharCount <= k) {
                maxLen = Math.max(maxLen, right-left+1);
            } else { // shrink left side
                char leftChar = s.charAt(left);
                map[leftChar-'A']--;
                if (leftChar == maxChar) {
                    maxCharCount -= 1;
                }
                left++;
            }
            right++;
        }
        return maxLen;
    }
}











