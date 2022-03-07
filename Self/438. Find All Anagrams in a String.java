/* Initial thought (should be similar as 567)
build a count array for p. Use a fixed size window to pass s, also keep track of the non-zero numbers in p's count array, if non-zero == 0, meaning the current window is an anagram of p, add the left position of the window into result.

time O(lenp + lens - lenp) = O(lens)
space O(26) = O(1)
*/
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        int[] counts = new int[26];
        int nonZero = 0;
        List<Integer> res = new LinkedList<>();
        if(p.length() > s.length()) {return res;}
        for (char c: p.toCharArray()) {
            counts[c-'a']++;
            if(counts[c-'a'] == 1) {nonZero++;}
        }
        
        int left = 0;
        int right = 0;
        while (right < left + p.length()) {
            char c = s.charAt(right);
            if (counts[c-'a'] == 0) {nonZero++;}
            else if(counts[c-'a'] == 1) {nonZero--;}
            counts[c-'a']--;
            right++;
        }
        right--;
        if (nonZero == 0) {res.add(left);}
        while (left < s.length() - p.length()) {
            char l = s.charAt(left);
            if (counts[l-'a'] == 0) {nonZero++;}
            else if (counts[l-'a'] == -1) {nonZero--;}
            counts[l-'a']++;
            left++;
            right++;
            char r = s.charAt(right);
            if (counts[r-'a'] == 1) {nonZero--;}
            else if (counts[r-'a'] == 0) {nonZero++;}
            counts[r-'a']--;
            if (nonZero == 0) {res.add(left);}
        }
        return res;
    }
}

// similar idea in this post
// https://leetcode.com/problems/find-all-anagrams-in-a-string/discuss/92059/O(n)-Sliding-Window-JAVA-Solution-Extremely-Detailed-Explanation


