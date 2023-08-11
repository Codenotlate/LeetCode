// 23/8/11
/** From solution (had the similar idea but unable to figure out)
Do it twice, and default all value as 1. First time from left to right, second time from right to left and based on the first time result array. res[i] = max(res[i], f(res[i-1])).
Time O(n) space O(n)

 */
// M1:
class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] res = new int[n];
        Arrays.fill(res, 1);
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i-1]) {res[i] = res[i-1] + 1;}
        }
        int sum = res[n-1];
        for (int i = n-2; i >= 0; i--) {
            if (ratings[i] > ratings[i+1]) {res[i] = Math.max(res[i], res[i+1]+1);}
            sum += res[i];
        }
        return sum;
    }
}
// M2: O(1) space way from solution
// https://leetcode.com/problems/candy/solutions/2234434/c-o-n-time-o-1-space-full-explanation/?envType=study-plan-v2&envId=top-interview-150
/*
view it as peak and valley (or slopes).  Notice the lowest point should always be 1. And for the peak value, the candy is gonna be max(left, right). 
Thus go from left to right: if on an increasing/decreasing slope, candy[i] = candy[i-1]+1, add candy[i] to sum. (for decreasing slope, it's reverse amount, but total sum is the same). And after the current increase and decrease calculation down, given peak is calculated twice with the count from left and count from right, thus we need to remove the min(leftCount, rightCount) out of sum.
if we have candy[i] = candy[i-1], we reset candy[i] = 1.
We can init sum as n, in this case we give at least 1 candy to every position. Thus when we have candy[i] = candy[i-1] case, we do nothing, as that 1 candy is already counted in when init.
*/

class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int sum = n;
        int i = 1;
        while ( i < n) {
            if (ratings[i] == ratings[i-1]) {
                i++;
                continue;
            }
            int upPrevCandy = 0;
            while(i < n && ratings[i] > ratings[i-1]) {
                upPrevCandy++;
                sum += upPrevCandy;
                i++;
            }
            int downPrevCandy = 0;
            while(i < n && ratings[i] < ratings[i-1]) {
                downPrevCandy++;
                sum += downPrevCandy;
                i++;
            }
            sum -= Math.min(upPrevCandy, downPrevCandy);
        }
        return sum;
    }
}

