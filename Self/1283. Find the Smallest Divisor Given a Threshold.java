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