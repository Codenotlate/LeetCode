class Solution {
    /* Initial thought
    similar to 16. sort the array first. Then do 2 pointers for the rest of the array. Here we want to know the count of smaller triplets. Thus if cursum of left and right >= target, right-- till we have cursum < target, count += right -left. Since all pairs (left, left+1...right) are valid. Then left++ and repeat.
    we can start the next repeat rount from left+1 till curright, is because for all right > curright, we already know (left + those right) >= cursum, and know we have left+1 > left, meaning (left+1 + those right) definitely >= cursum, thus we discard those pairs.
    time O(n^2) space O(sort place required)
    */
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int left = i +1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] + nums[i] >= target) {
                    right--;
                } else {
                    count += right - left;
                    left++;
                }
            }
        }
        return count;
    }
}