/* Initial thought
The problem can be converted to separate the weights array into at most days groups. And return the min(max(group sum) of all separation types)
first of all, we will always get a better or equal best solution if we separate it to exactly days groups.
Can use recursion + memo. For the first group, it can have n choices, [0:n]. result = max(first group sum(arr[0:x]), recur(weights[x+1, n-1], days-1)). memo done the result for dp[x][days].
dp[k][i] = max(dp[k-1][j], sum(arr[i:j])) where j in range[i, n-1].
time O(n*n*k) n is len of weights, k = days
space O(n*k)

from hint: binary search on the answer, need a function which returns true if and only if we can do the task with the target capacity in 'days' days.
time O(n * log(sum(w) - max(w)))
space O(1)
*/
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int max = 0;
        int sum = 0;
        for (int w: weights) {
            max = Math.max(max, w);
            sum += w;
        }
        int start = max;
        int end = sum;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if(ispossible(mid, weights, days)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
    
    private boolean ispossible(int target, int[] weights, int k) {
        int curSum = 0;
        for (int w: weights) {
            curSum += w;
            if (curSum > target) {
                k--;
                curSum = w;
            }
            if (k <= 0) {return false;}
        }
        return true;
    }
}









// Review
/*Thought
binary search on range (max, sum). When we have a threshold, how to determine how many days needed?
Since the weights should be packed in order, we jsut go throught the weights, whenever acumsum > threshold, days++, accumsum = cur weight.
Thus take O(n) time for each threshold check.time O(n * log(sum-min)) space O(1)


*/
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int start = Integer.MIN_VALUE;
        int end = 0;
        for (int w: weights) {
            start = Math.max(w, start);
            end += w;
        }
        
        while (start < end) {
            int mid = start + (end- start) / 2;
            int count = getCount(weights, mid);
            if (count > days) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }
    
    private int getCount(int[] weights, int mid) {
        int cumsum = 0;
        int count = 0;
        for (int w: weights) {
            cumsum += w;
            if (cumsum > mid) {
                count++;
                cumsum = w;
            }
        }
        return count+1;
    }
}















