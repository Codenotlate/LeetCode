/*Initial thought
Binary search the range. The answer will fall into the range [1, max(nums)]. notice if threshold == nums.length, simply return max(nums). Otherwise, check the target between [1, max(nums)].
have a function isValid(mid, nums) returns true if using target, the sum <= threshold. If mid is valid, end = mid, else start = mid +1. we return the final start since an answer is guaranteed.
Since isValid function is going to take O(n) time. The overall time will be O(len(nums)*log(max(nums) - 1))
space O(1)
*/
class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        int end = 0;
        for (int n: nums) {end = Math.max(end, n);}
        if(threshold == nums.length) {return end;}
        int start = 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if (isValid(mid, nums, threshold)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
    
    private boolean isValid(int target, int[] nums, int threshold) {
        int sum = 0;
        for (int n: nums) {
            int add = n%target == 0? 0 : 1;
            sum += n / target + add;
        }
        return sum <= threshold;
    }
}



// Review
// time O(n * log(maxn)) space O(1)
class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        int maxNum = 0;
        long sum = 0;
        for (int n: nums) {
            sum += n;
            maxNum = Math.max(maxNum, n);
        }
        // note sum should be wrong, otherwise may have overflow
        if (sum <= threshold) {return 1;}
        int start = 1;
        int end = maxNum;
        while (start < end) {
            int mid = start + (end - start) /2 ;
            if (isValid(mid, nums, threshold)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
    
    private boolean isValid(int div, int[] nums, int threshold) {
        int sum = 0;
        for (int n : nums) {
            int res = n % div == 0 ? n / div : (n / div + 1);
            sum += res;
            if (sum > threshold) {return false;}
        }
        return true;
    }
}