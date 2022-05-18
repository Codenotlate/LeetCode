/*Thought
Since 0 will not be in the result, when we encounter 0 we need to reset. Also product negative or positive is actually the same as the number of negatives odd or even. Thus we can count the negatives along the way. for non zero values, if current count is even, update the maxres as i - lastzero pos. Else we need to know the position of the first negative appeared in this subarray, update the maxres as i - firstneg pos.

time O(n) space O(1)
*/
class Solution {
    public int getMaxLen(int[] nums) {
        int res = 0;
        int lastzero = -1;
        int lastneg = -1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count = 0;
                lastzero = i;
                lastneg = i;
            } else {
                if (nums[i] < 0) {
                    count++;
                    if(lastneg == lastzero) {lastneg = i;}
                }
                if (count % 2 == 0) {res = Math.max(res, i - lastzero);}
                else {res = Math.max(res, i - lastneg);}
            }
        }
        return res;
    }
}


