/*Initial thought
recursion + memo or dp way. use dp[i][j] to represent the longest len with subarrays end in nums1[i] and nums2[j].
add = nums1[i] == nums2[j]? 1 : 0 
dp[i][j] = nums1[i] == nums2[j]? 1 + dp[i-1][j-1] : 0;
return max dp[i][j]

naive implemented way: time O(mn) space O(mn)
improved implemented way: time O(mn) space O(n) since only need to keep track of dp[i-1] row

*/
// naive dp O(mn) space way
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[m][n];
        int maxLen = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = nums1[i] == nums2[j] ? 1 : 0;
                } else {
                    dp[i][j] = nums1[i] == nums2[j] ? 1 + dp[i-1][j-1] : 0;
                }
                maxLen = Math.max(maxLen, dp[i][j]);
            }
        }
        return maxLen;
    }
}

// improved dp O(n) space way
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] dp = new int[n];
        int maxLen = 0;
        int dp_prev = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j];
                if (i == 0 || j == 0) {
                    dp[j] = nums1[i] == nums2[j] ? 1 : 0;
                } else {
                    dp[j] = nums1[i] == nums2[j] ? 1 + dp_prev : 0;
                }
                maxLen = Math.max(maxLen, dp[j]);
                dp_prev = temp;
            }
        }
        return maxLen;
    }
}



// a java version sliding window way here with O(1) space, check later
// https://leetcode.com/problems/maximum-length-of-repeated-subarray/discuss/109059/O(mn)-time-O(1)-space-solution





// Review: above is forwards, this one is backwards
/*Thought
DO or recur + memo way
M1: recur + memo way: if l1[i] = l2[j], then 1 + recur(i+1, j+1). else 0. This way time O(l1 * l2) space O(l1 * l2)
M2: DP way. dp[i][j] represents result for l1[i:] and l2[j:] and i&j must be included in the subarray. Then similarly. if l1[i] == l2[j], then dp[i][j] = 1 + dp[i+1][j+1]. Otherwise, dp[i][j] = 0. Since each dp[i][j] could be the final result, we need to record the maxLen = max(dp[i][j]) along the way. 
Space can be optimized to O(len2). time still O(l1 * l2)

*/
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int l1 = nums1.length;
        int l2 = nums2.length;
        int[] dp = new int[l2 + 1];
        int maxLen = 0;
        for (int i = l1 - 1; i >=0; i--) {
            int prev_ij = 0;
            for (int j = l2-1; j>=0; j--) {
                int temp = dp[j];
                if (nums1[i] == nums2[j]) {
                    dp[j] = 1+ prev_ij;
                    maxLen = Math.max(maxLen, dp[j]);
                }
                else {dp[j] = 0;}
                prev_ij = temp;
            }
        }
        return maxLen;
    }
}

