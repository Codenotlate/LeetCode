/** 2024.4.10
M1: monotonic stack way.  This is basically the same as question 84 for finding the largest rectangle area. It's just added the i<=k<=j filter when deciding the result.
Time O(n) space O(n)
M2: Notice this question is essenticially asking max results for all subarrays including position k. Thus a two pointer way can be used. Both pointers start at k and expand to left and right. The key here is to decide which pointer to move. This is similar to question 42/11. We choose to move the pointer that won't miss possible better results. 
Here we choose to move the pointer that's going to have larger values. Because when we move for example the right pointer to have [i,..,j,right] here, we are possibly missing the subarray [left, i,...,j]. However as long as we have right value >= left value, we can safely ignore that subarray since it won't contribute a better max area.
Time O(n) Space O(1)

 */
// M1
class Solution {
    public int maximumScore(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int res = 0;
        for (int cur = 0; cur <= nums.length; cur++) {
            while (stack.peek() != -1 && (cur == nums.length || nums[stack.peek()] >= nums[cur])) {
                int popIdx = stack.pop();
                if (k > stack.peek() && k < cur) {
                    res = Math.max(res, nums[popIdx] * (cur - stack.peek()-1));
                }   
            }
            stack.push(cur);
        }
        return res;
    }
}
// M2
class Solution {
    public int maximumScore(int[] nums, int k) {
        int left = k;
        int right = k;
        int res = nums[k];
        int curmin = nums[k];
        // note the while condition here is important
        while (left >= 1 || right < nums.length-1) {
            if (left <= 0 || (right < nums.length-1 && nums[left-1] <= nums[right+1])) {
                right++;
                curmin = Math.min(curmin, nums[right]);               
            } else {
                left--;
                curmin = Math.min(curmin, nums[left]);
            }
            res = Math.max(res, curmin * (right - left + 1));
        }
        return res;
    }
}