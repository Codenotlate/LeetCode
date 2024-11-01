// 2024.10.30
// binary way? search range [max(num), sum(nums)]. For each number, check if it's valid and find the smallest valid one?
// check can be greedy way again, expand the window until window sum > target number, countWin++, start a new window. As long as countWin <= k, it's valid.
// time O(nlog(sumnums))
class Solution {
    public int splitArray(int[] nums, int k) {
        long right = 0;
        long left = nums[0];
        for (int n: nums) {
            right += n;
            left = Math.max(left, n);
        }

        while (left < right) {
            long mid = left + (right-left)/2;
            if (isValid(mid, nums, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return (int)left;

    }

    private boolean isValid(long target, int[] nums, int k) {
        int countWin = 0;
        long winSum = 0;
        for (int i = 0; i < nums.length; i++) {
            winSum += nums[i];
            if (winSum > target) {
                countWin++;
                winSum = nums[i];
            }
        }
        return countWin+1 <= k;
    }
}

// There's also DP and recursion way. Try it next time.