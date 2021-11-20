// Phase 3 based on solution
// time O(nlogn) space O(nlogn)
// similar to 315 mergeSort. But in this problem we do two steps for sorted first half and second half. The first step is to update count by moving j++ only when condition satisfied. The second step is to actually sort the array to be used by its parent level.
class Solution {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int start, int end) {
        if (start >= end) {return 0;}
        int mid = start + (end - start) / 2;
        int count = mergeSort(nums, start, mid) + mergeSort(nums, mid + 1, end);
        int i = start; int j = mid + 1;
        while (i <= mid) {
            if (j <= end && nums[j] < nums[i] / 2.0) {j++;}
            else {
                i++;
                count += j - (mid + 1);
            }
        }
        Arrays.sort(nums, start, end + 1);
        return count;
    }
}