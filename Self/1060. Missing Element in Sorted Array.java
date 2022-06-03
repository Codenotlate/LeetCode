/*Thought
Binary search.
base case is when nums.len <= 2. note don't forget special case when nums.len = 1.
each time compare diff = nums[mid] - nums[left] - (mid-left) with k. because if no num missing between mid and left, then diff should be 0, so diff represents the number of num missed between left and mid. Thus we can compare it with k to decide which half to go next. Note if we go to the second half, we need to adjust k as k - diff.
For len = 2, base case[a,b], if a+k < b, then return a+k, else there's actually one num b not missing, thus return a + k + 1.

time O(logn) 
space O(1)
*/
class Solution {
    public int missingElement(int[] nums, int k) {
        // special case when len = 1
        if (nums.length == 1) {return nums[0] + k;}
        int left = 0;
        int right = nums.length-1;
        while(left < right-1) {
            int mid = left + (right-left) / 2;
            int diff = nums[mid] - nums[left] - (mid-left);
            if (diff >= k) {right = mid;}
            else {left = mid; k -= diff;}
        }
        return nums[left] + k >= nums[right] ? nums[left] + k + 1 : nums[left] + k;
    }
}


/*
Was asked this question in my Facebook onsite interview, 2nd question after I completed the first one in under 20 min, took a long time to arrive at the binary search solution with the help of the interviewer, but didn't have time in the end to code it all. If you haven't seen this question before, it is very difficult to see that binary search pattern especially in an interview setting.                                                                                                                                                                                               
*/