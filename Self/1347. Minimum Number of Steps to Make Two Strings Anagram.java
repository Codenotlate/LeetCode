/*Thought
Use the count array int[26]. For s do count++, for t do count--. For count < 0, meaning this char in t needs to be replaced to some char in s eventually
time O(m+n)  space O(26) = O(1)
*/
class Solution {
    public int minSteps(String s, String t) {
        int[] count = new int[26];
        int res = 0;
        for (char c: s.toCharArray()) {
            count[c-'a']++;
        }
        for (char c: t.toCharArray()) {
            count[c-'a']--;
            if (count[c-'a'] < 0) {res++;}
        }
        return res;
    }
}