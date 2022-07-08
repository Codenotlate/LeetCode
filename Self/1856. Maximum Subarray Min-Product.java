/*Thought
monotone stack + cumsum array
time O(n) space O(n)
*/
class Solution {
    public int maxSumMinProduct(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        long[] cumsum = new long[nums.length+1];
        cumsum[1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cumsum[i+1] = cumsum[i] + nums[i];
        }
        
        long res = 0;
        int curIdx = 0;
        while (curIdx < nums.length) {
            while (stack.peek() != -1 && nums[stack.peek()] > nums[curIdx]) {
                int popIdx = stack.pop();
                res = Math.max(res, nums[popIdx] * (cumsum[curIdx] - cumsum[stack.peek()+1]));
                
            }
            stack.push(curIdx);
            curIdx++;
        }
        
        while(stack.peek() != -1) {
            int popIdx = stack.pop();
            res = Math.max(res, nums[popIdx] * (cumsum[curIdx] - cumsum[stack.peek() + 1]));
        }
        
        return (int) (res % 1000000007);
    }
}

// a more concise way of code
// https://leetcode.com/problems/maximum-subarray-min-product/discuss/1198896/O(n)-Monostack-with-picture