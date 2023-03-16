/*Thought
similar to the gap problem with k = 1. Again we need to find the chars with max count. And add the rest chars in between.
from solution, we can build the result string by fill the result string at pos 0, 2, 4, 6... for the char with max count first. When pos > res.len, reset pos to 1, and continue to fill pos += 2. Since we start with max count char and we do all even number first and then all odd number, thus if it's possible to have result then this way of building will guarantee no adjacent are the same.
Note at first we also need to compare maxCount with (s.len+1) / 2
time O(n) space O(n)
*/
class Solution {
    public String reorganizeString(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int maxCount = 0;
        char maxChar = ' ';
        for (char c: s.toCharArray()) {
            map.put(c, map.getOrDefault(c,0) + 1);
            if (maxCount < map.get(c)) {
                maxCount = map.get(c);
                maxChar = c;
            }
        }
        
        if (maxCount > (s.length() + 1)/2) {return "";}
        char[] res = new char[s.length()];
        int idx = 0;
        while (map.get(maxChar) > 0) {
            res[idx] = maxChar;
            map.put(maxChar, map.get(maxChar)-1);
            idx += 2;
        }
        
        for (char c: map.keySet()) {
            while (map.get(c) > 0) {
                if (idx > res.length - 1) {
                    idx = 1;
                }
                res[idx] = c;
                map.put(c, map.get(c)-1);
                idx += 2;
            }
        }
        
        return new String(res);
    }
}

// from this post: https://leetcode.com/problems/reorganize-string/discuss/232469/Java-No-Sort-O(N)-0ms-beat-100





// another way using PQ: https://leetcode.com/problems/reorganize-string/discuss/128907/Java-solution-99-similar-to-358





// Review 23/3/16 - the string generation part needs hint from the answer above
/*Thought
Count the chars first. Then make the char with largest count as the border for each gap. if sum(other count) < maxCount - 1, return "". Otherwise, build the result string.
Hint from solution: have a char[s.len], then have idx label the current insertion position, idx += 2, making sure it's not adjacent. If idx >=len, reset to 1. Note need to make sure starts with the char having maxCount first. otherwise, may have "cabaa".
time O(n) space (n)
 */
class Solution {
    public String reorganizeString(String s) {
        int[] counts = new int[26];
        int maxCount = 0;
        int maxCharIdx = -1;
        for (char c: s.toCharArray()) {
            counts[c-'a']++;
            if (counts[c-'a'] > maxCount) {
                maxCount = counts[c-'a'];
                maxCharIdx = c-'a';
            }
           
        }
        if (s.length() - maxCount < maxCount - 1) {return "";}
        int idx = 0;
        char[] chars = new char[s.length()];
        // start with char having maxCount first
        idx = insert(chars, counts, idx, maxCharIdx);
        for (int i = 0; i < 26; i++) {
            if (counts[i] > 0) {
                idx = insert(chars, counts, idx, i);
            }
        }
        return new String(chars);
    }


    private int insert(char[] chars, int[] counts, int idx, int countIdx) {
        char c = (char) (countIdx + 'a');
        while (counts[countIdx] > 0) {
                chars[idx] = c;
                idx += 2;
                if (idx >= chars.length) {idx = 1;}
                counts[countIdx]--;
        }
        return idx;
    }
} 