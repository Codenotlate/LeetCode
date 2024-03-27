/** 20240327
2 sliding window way + 1 prefix map way
=== The irregular part comparing to normal sliding window case
In a standard sliding window approach, once the currentSum reaches the target goal, the typical strategy involves simply moving the left pointer of the window forward to potentially find more subarrays. However, this approach has a critical limitation when applied to binary arrays.

Including a zero element in the subarray won't change the sum. As a result, even if the currentSum reaches the goal initially, we might miss further subarrays that also meet the goal by simply shrinking the window as long as the sum remains equal to the goal. This is because the presence of zeros creates the possibility of combining them with elements encountered later to reach the target sum.

-- so for M1:
Thus subarrays exceeding the target sum are irrelevant to our objective. We only care about subarrays whose sum is either equal to the goal or less than the goal.

-- for M2:
we deal with this by tracking the leading zeros of a window.

*/


// M1: two sliding window (<= goal & <= goal-1)
class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        return numNoLargerThanSum(nums, goal) - numNoLargerThanSum(nums, goal-1);
    }

    private int numNoLargerThanSum(int[] nums, int goal) {
        int count = 0;
        int left = 0;
        int right = 0;
        int curSum = 0;
        while(right < nums.length) {
            curSum += nums[right];
            while(curSum > goal && left < right) {
                curSum -= nums[left];
                left++;                
            }
            if (curSum <= goal && left <= right) {count+=right-left+1;}
            right++;
        }
        return count;
    }
}


// M2: sliding window one pass with tracking trailing zeros
class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        int count = 0;
        int left = 0;
        int right = 0;
        int curSum = 0;
        int preZeros = 0;
        while(right < nums.length) {
            curSum += nums[right];
            while((curSum > goal || nums[left] == 0) && left < right) {
                preZeros = nums[left] == 0? preZeros + 1:0;
                curSum -= nums[left];
                left++;                
            }
            if (curSum == goal && left <= right) {count += preZeros + 1;}
            right++;
        }
        return count;
    }
}



// M3: Store prefixSum into a map. Each time check the count of goal and currentSum-goal. This is basically the same as get the count of all subarray with sum==goal ending with each num in nums. TIme O(n) space O(n)
class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        Map<Integer, Integer> map = new HashMap<>();
        int prefixSum = 0;
        int count = 0;
        for (int num: nums) {
            prefixSum += num;
            count += map.getOrDefault(prefixSum-goal, 0);
            if (prefixSum == goal) {count++;}
            map.put(prefixSum, map.getOrDefault(prefixSum,0)+1);
        }
        return count;
    }
}