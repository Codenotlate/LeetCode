//Phase3 self - top-down
//DP: time O(n^2), space O(n)

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        if (n == 1) {return triangle.get(0).get(0);}
        int[] dp = new int[n];
        dp[0] = triangle.get(0).get(0);
        int minSum = Integer.MAX_VALUE;
        
        for (int i = 1; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                int add;
                if (j == i) {add = dp[j - 1];}
                else if (j == 0) {add = dp[j];}
                else {add = Math.min(dp[j], dp[j - 1]);}
                
                dp[j] = triangle.get(i).get(j) + add;
                if (i == n - 1) {
                    minSum = Math.min(minSum, dp[j]);
                }
                
            }
        }
        return minSum;
    }
}


// can also do bottom up
// https://leetcode.com/problems/triangle/discuss/38730/DP-Solution-for-Triangle

// phase3 self
class Solution {
    // dp and starting from the bottom
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];
        for (int i = 0; i < triangle.get(n-1).size(); i++) {
            dp[i] = triangle.get(n-1).get(i);
        }
        
        for (int i = n-2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j+1]);
            }
        }
        return dp[0];
    }
}
