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





//Review - similar sliding window idea, but different implementation
/* special case, if s1.len > s2.len, return false.
finding a window in s2, that has the exactly same chars count as s1. since only lowercase letters, can use int[26] for count for s1 first.
Then maintain a window in s2,  keep move right pointer right if count[char] > 0. Decrease the count and adjust nonZeros along the way. If current window no longer satisfies, move left pointer one step right, need to adjust count[left] and nonZeros accordingly.

time O(l2) space O(26) = O(1)


*/

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {return false;}
        int[] count = new int[26];
        int nonZeros = 0;
        for (char c : s1.toCharArray()) {
            count[c-'a']++;
            if (count[c-'a'] == 1) {nonZeros++;}
        }
        
        int left = 0;
        int right = left;
        while (left < s2.length()) {
            while (right < s2.length() && count[s2.charAt(right) - 'a'] > 0) {
                int countIdx = s2.charAt(right) - 'a';
                count[countIdx]--;
                if (count[countIdx] == 0) {
                    nonZeros--;
                    if (nonZeros == 0) {return true;}
                }
                right++;
            }
            
            int charIdx = s2.charAt(left)-'a';
            count[charIdx]++;
            if (count[charIdx]== 1) {nonZeros++;}
            left++;
            
        }
        return false;
    }
}







// 24/3/29
// Time O(l1+l2-l1)=O(l2) space O(26) = O(1)
// When shrinking the window, we need to take one step as a time instead of jumping, due to examples like: s1=abcd, s2 = adbac
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {return false;}
        int[] count = new int[26];
        int uniqChar = 0;
        for (char c: s1.toCharArray()){
            count[c-'a']++;
            if (count[c-'a']==1) {uniqChar++;}
        }

        int left = 0;
        int right = 0;
        int unneeded = 0;
        while (right < s2.length()) {
            char curChar = s2.charAt(right);
            count[curChar - 'a']--;
            if (count[curChar-'a'] == 0) {
                uniqChar--;
                if (uniqChar == 0) {return true;}
            } else if (count[curChar-'a']<0) {
                unneeded++;
            }
            // while(unneeded> 0 && left <= right) { can be optimized to below
            while(unneeded> 0 && left <= right-s1.length()+1) {
                char leftChar = s2.charAt(left);
                count[leftChar-'a']++;
                if (count[leftChar-'a']==1) {
                    uniqChar++;
                } else if (count[leftChar-'a']==0){
                    unneeded--;
                }
                left++;
            }
            right++;
            
        }
        return false;

    }
}





















