/*Thought
if diff between # of 1s and # of 0s are > 1, then impossible, return -1.
if # of 1 is larger, we know all odd pos of the string should be 0 to be alternating. Thus we can count all 1's in the wrong odd pos. Similar thing for # of 0 is larger. If equal, return the smaller result from start with 1 or 0.
time O(n) space O(1)
*/
class Solution {
    public int minSwaps(String s) {
        int count0 = 0;
        int count1 = 0;
        for (char c: s.toCharArray()) {
            if (c== '0') {count0++;}
            else {count1++;}
        }
        int diff = count1- count0;
        if (diff > 1 || diff < -1) {return -1;}
        if (diff > 0) {return swapNum(s, 1);}
        else if (diff < 0) {return swapNum(s,0);}
        return Math.min(swapNum(s,1), swapNum(s,0));
    }
    
    // return the num of values in even pos that is not equal to target
    private int swapNum(String s, int target) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if(i % 2 == 0 && s.charAt(i) - '0' != target) {count++;}
        }
        return count;
    }
}