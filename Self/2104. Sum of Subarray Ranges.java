/*Thought
Naive way: O(n^2) way. for each [i:] loop, update the min and max along the way
O(n) way: similar to 907
First convert the problem from sum(max-min) to sum(max)-sum(min). Thus for each nums[i], we want to know how many subarrays use it as max or min. The way to find how many is basically find the first pos with num >=/<= it for max / min on its left and right. The # = (i-left) * (right-i).
Second trick is to use monotone stack to find both left and right idx for each i at the same time. We can calculate when i is popped out, this way we only need 1-pass, becaue when it's popped out we guarantee the peeknum on its left and curnum on its right are those target numbers we want. We also need to add (-1) to stack as the mostleft index.
*/
class Solution {
    public long subArrayRanges(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        long res = 0;
        // get minRes part first
        for (int i = 0; i <= nums.length; i++) {
            while(stack.peek() != -1 && (i == nums.length || nums[i] <= nums[stack.peek()])) {
                int popIdx = stack.pop();
                // note need to convert to long here to avoid overflow
                res -=(long) nums[popIdx] * (popIdx - stack.peek()) * (i - popIdx);
            }
            if ( i < nums.length) {stack.push(i);}
        }
        // get maxRes part
        for (int i = 0; i <= nums.length; i++) {
            while(stack.peek() != -1 && (i == nums.length || nums[i] >= nums[stack.peek()])) {
                int popIdx = stack.pop();
                res += (long) nums[popIdx] * (popIdx - stack.peek()) * (i - popIdx);
            }
            if ( i < nums.length) {stack.push(i);}
        }
        return res;
    }
}