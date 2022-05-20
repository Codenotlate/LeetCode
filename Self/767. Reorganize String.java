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