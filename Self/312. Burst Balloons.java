// Phase3: learned from solution
// good solution page: ttps://leetcode.com/problems/burst-balloons/solution/
// both method: time O(N^3) space O(n^2)
// idea: DP + divide & conquar + thinking backwards


class Solution {
    // M1 top-down DP
    public int maxCoins(int[] nums) {
        int newLen = nums.length + 2;
        int[] newNums = new int[newLen];
        System.arraycopy(nums, 0, newNums, 1, nums.length);
        newNums[0] = 1;
        newNums[newLen - 1] = 1;
        int[][] memo = new int[newLen][newLen];
        
        return dfs(newNums, 1, newLen - 2, memo);     
    }
    
    
    private int dfs(int[] newNums, int left, int right, int[][] memo) {
        if (left > right) {return 0;}
        if (memo[left][right] != 0) {return memo[left][right];}
        
        // choices: each nums[i] could become the last to be burst -- thinking backwards!
        for (int i = left; i <= right; i++) {
            int gain = newNums[left - 1] * newNums[i] * newNums[right + 1];
             memo[left][right] = Math.max(memo[left][right], gain + dfs(newNums, left, i - 1, memo) + dfs(newNums, i + 1, right, memo));
        }
        
        return memo[left][right];
    }
}  



class Solution {
    // M2 bottom-up DP
    public int maxCoins(int[] nums) {
        int newLen = nums.length + 2;
        int[] newNums = new int[newLen];
        System.arraycopy(nums, 0, newNums, 1, nums.length);
        newNums[0] = 1;
        newNums[newLen - 1] = 1;
        int[][] memo = new int[newLen][newLen];
        
        // since dp[l][r] depends on dp[l][i-1] and dp[i+1][r], we need to loop from bottom to top and from left to right.
        for (int l = newLen - 2; l >= 1; l--) {
            for (int r = l; r <= newLen - 2; r++) {
                for (int i = l; i <= r; i++) {
                    int gain = newNums[l - 1] * newNums[i] * newNums[r + 1];
                    memo[l][r] = Math.max(memo[l][r], gain + memo[l][i - 1] + memo[i + 1][r]);
                }
            }
        }
                                          
        return memo[1][newLen-2];
    }
    
    
    
}  