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





// Review 23/2/22 - a diff way compared to above way, try above way next time
/* Thoughts
DP problem. and 0 is a special case.
dp[i] represents the max len of subarray ending in pos i for both positive product or negative product.
if nums[i] > 0, dp[i][pos] = dp[i-1][pos] + 1; dp[i][neg] = dp[i-1][neg] == 0? -1:dp[i-1][neg] + 1
else if nums[i] < 0, dp[i][pos] = dp[i-1][neg] == 0? -1: dp[i-1][neg] + 1; dp[i][neg] = dp[i-1][pos] + 1
else nums[i] == 0, dp[i][pos/neg] = 0

return max(dp[i][pos])

time O(n) space O(n) can be optimized to O(1)


 [1,-2,-3,4]
- 0, 2, 1,2 
+ 1, 0, 3,4

 [-1,-2,-3,0,1]
- 1, 1, 3, 0, 0
+ 0, 2, 2, 0, 1

*/
// M1: O(n) space way
class Solution {
    public int getMaxLen(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n+1][2];
        int maxRes = 0;
        for (int i = 1; i <= n; i++) {
            int num = nums[i-1];
            if (num > 0) {
                dp[i][0] = dp[i-1][0] + 1;
                dp[i][1] = dp[i-1][1] == 0? 0 : dp[i-1][1]+1;
            } else if (num < 0) {
                dp[i][0] = dp[i-1][1] == 0? 0: dp[i-1][1] + 1;
                dp[i][1] = dp[i-1][0] + 1;
            } else {
                dp[i][0] = 0;
                dp[i][1] = 0;
            }
            maxRes = Math.max(maxRes, dp[i][0]);
        }
        return maxRes;
    }
}
// M2: O(1) space way
class Solution {
    public int getMaxLen(int[] nums) {
        int n = nums.length;
        int dp_neg = 0;
        int dp_pos = 0;
        int maxRes = 0;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (num > 0) {
                dp_pos += 1;
                dp_neg = dp_neg == 0 ? 0 : dp_neg+1;
            } else if (num < 0) {
                int last_dp_pos = dp_pos;
                dp_pos = dp_neg == 0 ? 0 : dp_neg+1;
                dp_neg = last_dp_pos + 1;
            } else {
                dp_pos = 0;
                dp_neg = 0;
            }
            maxRes = Math.max(maxRes, dp_pos);
        }
        return maxRes;
    }
}

