/* from solution
notice that if you have duplicate values in nums array, if you earn one of them, you end up earning all of them. This is because you have deleted its neighbors and therefore make its remaining duplicates "undeletable".

Thus the problem can be converted to a set of (num:num*count), and we get the max sum out of it based on the no num-1 and num+1 rule. Using DP.
if forwards: dp[i] = max(arr[i]+dp[i+2], dp[i+1])
if backwards: dp[i] = max(arr[i] + dp[i-2], dp[i-1]).

One way is to use an array with size(max-min+1). count * num stores in arr[num-min].
time O(n+max-min) space O(max-min)
Another space saving way is to replace array with a map.
time same, space O(min(max-min, n))

https://leetcode.com/problems/delete-and-earn/discuss/109895/JavaC%2B%2B-Clean-Code-with-Explanation

https://leetcode.com/problems/delete-and-earn/discuss/109891/Sharing-my-Simple-Straight-Forward-Java-O(n)-Solution-Explanation-Included

*/
//M1
class Solution {
    public int deleteAndEarn(int[] nums) {
        int min = 10001;
        int max = 0;
        for (int n: nums) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        int[] arr = new int[max-min+1];
        for (int n: nums) {
            arr[n-min] +=n;
        }
        
        int dp = arr[max-min];
        int dp_next = 0;
        for (int i = max-min-1; i>= 0; i--) {
            int temp = dp;
            dp = Math.max(dp, arr[i] + dp_next);
            dp_next = temp;
        }
        return dp;
    }
}

//M2
class Solution {
    public int deleteAndEarn(int[] nums) {
        int min = 10001;
        int max = 0;
        for (int n: nums) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
            map.put(n, map.getOrDefault(n,0)+n);
        }
        
        int dp = map.get(max);
        int dp_next = 0;
        for (int i = max-1; i>= min; i--) {
            int temp = dp;
            dp = Math.max(dp, map.getOrDefault(i,0) + dp_next);
            dp_next = temp;
        }
        return dp;
    }
}