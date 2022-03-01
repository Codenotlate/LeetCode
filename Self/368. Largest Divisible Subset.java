/*Initial thought
notice for i < j < k, if k % j == 0 and j % i ==0 then k % i == 0
sort the array first. use recursion + memo or dp
dp[i] represents the largest subset ending in i, meaning i is the largest in the subset. Then dp[i] = max(dp[x]+i) where x is the number <i and i % x == 0.
If we record all subsets from each num in nums, then the space needed would be O(n^2). Thus we can first use similar dp to get the dp[i] with largest subset size. Then build the solution in that size by starting at position i and move backwards. In this way, space will be O(n)

*/
class Solution { 
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int maxi = 0;
        int maxLen = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i],dp[j] + 1);
                    if (dp[i] > maxLen) {
                        maxLen = dp[i];
                        maxi= i;
                    }
                }
            }
        }
        
        // now we know the subset ends with maxi has the maxLen, move backwards to get the result
        List<Integer> res = new LinkedList<>();
        res.add(nums[maxi]);
        int smallest = nums[maxi];
        for (int j = maxi - 1; j >= 0; j--) {
            //note we need to check not only the division, but also the len ([4,8,10,240])
            if (smallest % nums[j] == 0 && dp[j] == maxLen-1) {
                res.add(nums[j]); 
                maxLen--;
                smallest = nums[j];
            }
        }
        Collections.reverse(res);
        return res;
        
    }
}




// check 3 solution ways
// https://leetcode.com/problems/largest-divisible-subset/solution/
// https://leetcode.com/problems/largest-divisible-subset/discuss/84006/Classic-DP-solution-similar-to-LIS-O(n2)













