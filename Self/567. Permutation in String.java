/* Initial thought
permutation requires to have same chars with same count. Thus we can have a fixed size window moving through s2, check if each window has the same chars with same count as s1.
Use a nonZeroCount to track the non-zero chars number in counts. When this number is decreased to 0, meanning the current window char frequency matches s1 exactly. For chars in window, we decrease the count in counts, and increase it back if it's moved out of the window.

time O(l1 + l2 - l1)
space O(26) = O(1)
*/
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int[] counts = new int[26];
        int nonZeros = 0;
        
        for (char c: s1.toCharArray()) {
            if(counts[c-'a'] == 0) {nonZeros++;}
            counts[c-'a']++;
        }
        int l1 = s1.length();
        int l2 = s2.length();
        // don't forget about this special case
        if (l1 > l2) {return false;}
        int left = 0;
        int right = 0;
        while (right < left + l1) {
            char c = s2.charAt(right++);
            counts[c-'a']--;
            if(counts[c-'a'] == 0) {nonZeros--;}
            if(counts[c-'a'] == -1) {nonZeros++;}
        }
        if(nonZeros == 0) {return true;}
        right--;
        
        while (left < l2 - l1) {
            counts[s2.charAt(left)-'a']++;
            if(counts[s2.charAt(left)-'a'] == 0) {nonZeros--;}
            if(counts[s2.charAt(left)-'a'] == 1) {nonZeros++;}
            left++;
            right++;
            counts[s2.charAt(right)-'a']--;
            if(counts[s2.charAt(right)-'a'] == 0) {nonZeros--;}
            if(counts[s2.charAt(right)-'a'] == -1) {nonZeros++;}
            if(nonZeros == 0) {return true;}
        }
        return false;
    }
}
































