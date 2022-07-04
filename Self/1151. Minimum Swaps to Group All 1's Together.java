/*Thought
Do first pass to get the number of 1's in the data. Eventually we want to have a window with that size and have all values as 1. And we need to do swaps to achieve that. So it's actually find a window in data with that size and has min number of 0's.
time O(n)
space O(1)

*/
class Solution {
    public int minSwaps(int[] data) {
        int res = data.length;
        int oneSize = 0;
        for (int n: data) {if (n == 1) {oneSize++;}}
        if (oneSize <= 1) {return 0;}
        int left = 0;
        int zeroNum = 0;
        for (int right = 0; right < data.length; right++) {
            if (data[right] == 0) {zeroNum++;}
            if (right - left + 1 < oneSize) {continue;}
            else {
                res = Math.min(res, zeroNum);
                if (data[left] == 0) {zeroNum--;}
                left++;
            }
            
        }
        return res;
    }
}