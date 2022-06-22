/*Thought
sliding window + countmap
track the countmap and unique char nums in the current window, expand till uniq > k, then shrink the left side. The basic idea is that if s[i:cur] has uniq >k then s[i:] will all be invalid. Thus we can move the left pointer to skip i.
Pay attention to all the moving edge cases. Hard to be bugfree.
time O(n) space O(n) // since not only lowercase letters

*/
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int curUniq = 0;
        int left = 0;
        int right = 0;
        int res = 0;
        while (right < s.length()) {
            while (right < s.length()) {
                char c = s.charAt(right);
                map.put(c, map.getOrDefault(c,0)+1);
                if (map.get(c) == 1) {curUniq++;}
                if (curUniq > k) {break;}
                right++;
            }
            res = Math.max(res, right - left);
            while (left <= right) {
                char c = s.charAt(left);
                map.put(c, map.get(c)-1);
                if (map.get(c) == 0) {
                    map.remove(c);
                    curUniq--;
                }                
                left++;
                if(curUniq <= k) {break;}
            }
            right++;
        }
        return res;
    }
}