class Solution {
    /* Initial thought
    similar to 15. we fix one, and deal with the rest as a two sum problem.
    since here we need closest instead of the exact number, hashmap way won't work. We go with the sort and two pointer way.
    for the 2pointer loop, we track the curSum. if curSum > t, right-- till curSum < t. left++ till curSum > t. vice versa. track the diff with target along the way. Update the min diff if abs(current diff) < abs(min diff). In the end, return target + mindiff.
    time O(nlogn+ n^2) = O(n^2) space = O(sort algo(O(1/logn/n)))   
    */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // two pointer way for the array after i
            int left = i+1;
            int right = nums.length - 1;
            while (left < right) {
                int curDiff = nums[left] + nums[right] + nums[i] - target;
                if (Math.abs(curDiff) < Math.abs(minDiff)) {minDiff = curDiff;}
                if (curDiff == 0) {return target;}
                else if (curDiff > 0) {right--;}
                else {left++;}
            }
        }
        return target + minDiff;
    }
}

// solution is good
// https://leetcode.com/problems/3sum-closest/solution/
// binary search way with worse time complexity but can check later

/*Further Thoughts
3Sum is a well-known problem with many variations and its own Wikipedia page.

For an interview, we recommend focusing on the Two Pointers approach above. It's easier to get it right and adapt for other variations of 3Sum. Interviewers love asking follow-up problems like 3Sum Smaller!

If an interviewer asks you whether you can achieve \mathcal{O}(1)O(1) memory complexity, you can use the selection sort instead of a built-in sort in the Two Pointers approach. It will make it a bit slower, though the overall time complexity will be still O(n^2).
*/




// Review - same idea as above
/*Thought
like 3Sum. still sort first and using 2 pointers. if pair sum  == new target, immediately return, if '<', left++; else right--. until left >= right, stop the move. Track the diff with min abs. return target+diff in the end.
time O(n^2) space O(logn for sort)
*/
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int diff = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int curdiff = twoSum(nums, i+1, nums[i], target);
            if (Math.abs(curdiff) < Math.abs(diff)) {
                diff = curdiff;
            }
        }
        return target + diff;
    }
    
    
    private int twoSum(int[] nums, int i, int cur, int target) {
        target -= cur;
        int left = i;
        int right = nums.length - 1;
        int diff = Integer.MAX_VALUE;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {return 0;}
            else if (sum < target) {left++;}
            else {right--;}
            if (Math.abs(diff) > Math.abs(sum-target)) {diff = sum - target;}
        }
        return diff;
    }
}
