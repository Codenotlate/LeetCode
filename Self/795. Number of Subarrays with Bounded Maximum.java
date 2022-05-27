/*Thought
Key idea: check for each num in range how many subarrays use it as the max. Also need to handle the equal value properly to avoid dup.
M1: do two passes. first forward pass to get left#m then backwards pass to get right#, then res +=#left * #right. Need to use array to store #s. Note for equal nums, if forwards pass we use <= as criteria, then for backwards we need to use < to avoid dup. Time O(n) space O(n)
M2: one pass using a stack for idx. if peeknum <= curnum, keep popping out. And for popout num, both boundary for it has been find, res += (popidx - peekidx) *(curidx-popidx). At the end, for remaining idx in the stack, res +=(popidx-peekidx) *(len-popidx)
time O(n) space O(n)


Two better other ways from discussion: both with O(n) time and O(1) space
M3: convert problem to count(right)-count(left-1) where count(x) represents the number of subarrays in nums that has max <= x.
Then for count(x), using dp idea, dp[i] represents #of valid subarrays ending in i. Then if nums[i] <= x, then dp[i] = dp[i-1]+1, else dp[i] = 0. Then sum of all dp[i] will be the result for count.

M4: using dp directly. dp[i] represents #of valid subarrays ending in i. Then if nums[i] > right, dp[i] = 0. Else if nums[i] < left, dp[i] = dp[i-1], else if left <= nums[i] <= right, then dp[i] = i - prev idx with dp = 0.(Note: dp[i] != dp[i-1]+1, consider [72,55,36,5,55] l=32 r=69). Then sum of all dp[i] is the result.


*/
// M2 (still not quick enough)
class Solution {
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            while(stack.peek() != -1 && nums[stack.peek()] <= nums[i]) {
                int popIdx = stack.pop();
                if (nums[popIdx] <= right && nums[popIdx] >= left) {
                    res += (popIdx - stack.peek()) * (i-popIdx);
                }
            }
            stack.push(i);
        }
        
        while(stack.peek() != -1) {
            int popIdx = stack.pop();
            if (nums[popIdx] <= right && nums[popIdx] >= left) {
                res += (popIdx - stack.peek()) * (nums.length - popIdx);
            }
        }
        return res;
    }
}

// M3: https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/discuss/1278743/C%2B%2BJavaPython-Easy-to-understand-solution-Clean-and-Concise-O(N)
class Solution {
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        return count(right, nums) - count(left-1, nums);
    }
    
    private int count(int x, int[] nums) {
        int res = 0;
        int dp = 0;
        for (int n: nums) {
            if (n <= x) {dp += 1; res += dp;}
            else {dp = 0;}
        }
        return res;
    }
}



// M4: https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/discuss/117723/Python-standard-DP-solution-with-explanation
class Solution {
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int res = 0;
        int dp = 0;
        int prev = -1;
        for (int i = 0; i <nums.length; i++) {
            if (nums[i] > right) {dp = 0; prev = i;}
            else if (nums[i] >= left) {dp = i-prev;}
            res += dp;
        }
        return res;
    }
    
    
}