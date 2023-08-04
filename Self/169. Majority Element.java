// 23/8/4 - O(n) time and O(1) space way is not easy, needs below Algo.
/** Learned from solution - Boyer-Moore Majority Vote Algorithm
Since it's guaranteed there exists a majority element with count > n/2. Thus we can imagine if we use each non-majority element occurence to offset one occurence of majority element, we should have one majority element left in the end. Also the offset between non-majority elements won't harm the result.
Thus we do the count and track the element with non-zero count. The last element left with non-zero count is the majority element.

 */

class Solution {
    public int majorityElement(int[] nums) {
        int count = 1;
        int majorElement = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == majorElement) {
                count++;
            } else {
                count--;
                if (count < 0) {
                    majorElement = nums[i];
                    count = 1;
                }
            }
        }
        return majorElement;
    }
}