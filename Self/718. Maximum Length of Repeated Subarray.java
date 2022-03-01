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




