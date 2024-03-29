/** 24/3/29
This question is kind of similar to 930. But different in two aspects:
1) first here the moving condition of a window is more straight forward, since it requires the productSum to be strictly smaller than k.
2) The hashmap way in 930 is not gonna work easily here, cause here we require strictly smaller instead of equal.
Time O(n) space O(1)


---------------
learning notes:
Another interesting idea is to first convert each element in the nums to be the log of it. Then the product problem becomes sum problem.
Then we can use either the sliding window way, or the binary search way since the prefixSum will be a non-descending array.
The binary search way will take O(nlogn) time 


*/

class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int count = 0;
        long curProduct = 1;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            curProduct *= nums[right];
            while (curProduct >= k && left <= right) {
                curProduct /= nums[left];
                left++;
            }
            count += right - left + 1;
            right++;
        }
        return count;
    }
}