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






// Review
/*Thoughts
The result can be converted to another perspective. Instead of sum(maxi - mini), we can do sum(maxi) - sum(mini), which also means for each num in nums, we can figure out in how many subarrays it is used as the max, similar to as the min.
For figuring out how many time each num becomes max or min or its subarray, we can use a monotonic stack to make this O(n) time. The input of the stack is the index of that num in nums. This way, we can use index diff to get # of times. Details go as below:
For the stack finding max, we first input -1 into the stack, for each cur num, we keep comparing it with the stack peek idx num, pop out all the peek num that are smaller or equal to it. [Wrong part!! - Add peek idx num * (curIdx - stack peek idx - 1)] Add {peek idx num * (peekIdx - stack peek idx) * (curIdx - peekIdx) } to maxSum part. Eventually the max stack will be a stack with number in descending order, thus called monotonic stack.
Similar thing to min stack, except keeping popping out and add to minSum part when peek idx num is larger than or equal to cur idx num.

Add note to have minSum and maxSum and also the multiply part set as long to avoid data overflow issue.
Time O(n) space O(n)
 */
class Solution {
    public long subArrayRanges(int[] nums) {
        Stack<Integer> minStack = new Stack<>();
        Stack<Integer> maxStack = new Stack<>();
        minStack.push(-1);
        maxStack.push(-1);
        long minSum = 0;
        long maxSum = 0;

        // optimization in code to also include handling with remaining idxs in stack after the loop of all num in nums array
        for (int i = 0; i <= nums.length; i++) {
            int curNum = i < nums.length? nums[i] : Integer.MAX_VALUE;
            // deal with maxStack
            while(maxStack.peek() != -1 && nums[maxStack.peek()] <= curNum) {
                int popIdx = maxStack.pop();
                maxSum += (long) nums[popIdx] * (popIdx - maxStack.peek()) * (i - popIdx);
            }
            maxStack.push(i);
            // deal with minStack
            curNum = i < nums.length? nums[i] : Integer.MIN_VALUE;
            while(minStack.peek() != -1 && nums[minStack.peek()] >= curNum) {
                int popIdx = minStack.pop();
                minSum += (long) nums[popIdx] * (popIdx - minStack.peek()) * (i - popIdx);
            }
            minStack.push(i);
        }
        return maxSum - minSum;
    }
}




