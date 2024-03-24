/** 2024/3/24
self:
====Brute force way: get all substring and check whether valid. time O(wordLen^2 * forbiddenLen * 10). There are a lot of repetitive work inside, e.g. if we know sub[i:j] is invalid, then we don't need to check sub[i:j+1/j+2/j+...]
====Sliding window way
First convert forbidden to a set, so that check can be O(1)
Whenever we expand the window, wen need to check not only substr[l,r+1], but all substr ends with r. Since we know forbidden.length <=10, at most we only need to check 10 substr ending with r.
if we found one above substr[:r] is contained in the set, we know the current window is invalid, so we shrink the window. Three operations on shrinking: 1) update maxLen to r-l; 2) move left directly to the substr_left+1, cause we already know substr[substr_left:right] is invalid. 3) right = max(left, right).
time O(forbiddenLen*10 + wordLen * 10^2 (the second 10 due to hash to check in the set)) = O(forbiddenLen + wordLen) space O(n*10 for substr + forbiddenLen) = O(wordLen+forbiddenLen)

--------------------------------
no editorial yet - but similar idea as other solutions.
==== one note
The slice of string is time and space consuming. Thus can use Trie to save the time and space of slicing.


 */
class Solution {
    public int longestValidSubstring(String word, List<String> forbidden) {
        int maxLen = 0;
        Set<String> set = new HashSet<>();
        for (String f: forbidden) {
            set.add(f);
        }
        int left = 0;
        int right = 0;
        while (right < word.length()) {
            boolean isInvalid = false;
            for (int subleft = right; subleft >= Math.max(left, right-9); subleft--) {
                if (set.contains(word.substring(subleft, right+1))) {
                    maxLen = Math.max(maxLen, right-left);
                    left = subleft+1;
                    right = Math.max(right, left);
                    isInvalid = true;
                    break;
                } 
            }
            if (!isInvalid) {right++;}
        }
        // don't forget this final update
        maxLen = Math.max(maxLen, right-left);
        return maxLen;
    }
}