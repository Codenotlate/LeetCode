/*Thought from solution
M1: brutal force O(n^3)
M2: improved brutal force, for each j, we want to find the min in [0:j-1] range and find whether there is a k satisfying 132 pattern in [j+1:end] range. time O(n^2)
M3: based on M2, using stack to find k. First we use an array to store the min in [0:j-1] for each j (this min is actually potential nums[i]). Then we move backwards on nums to find k.
For numj, if minj < numj(i.e.numi < numj), then check the numk in the stack, if numk <= numi, this numk is invalid for all the following numi, thus pop it out until the peek numk > numi or stack is empty. Then we check if stack is not empty and peek numk < numj, then return true. Otherwise, we push this current numj into the k stack and continue. Note if minj >= numj, then there's no need to push this numj into the k stack. cause it won't be a valid numk for all following numj.
Time O(n) space O(n)
*/
class Solution {
    public boolean find132pattern(int[] nums) {
        int[] mins = new int[nums.length];
        for (int i = 0; i < mins.length; i++) {
            if (i == 0) {mins[0] = nums[0]; continue;}
            mins[i] = Math.min(mins[i-1], nums[i]);
        }
        
        Stack<Integer> stack = new Stack<>();
        for (int j = nums.length-1; j >= 0; j--) {
            if (nums[j] > mins[j]) {
                while(!stack.isEmpty() && stack.peek() <= mins[j]) {stack.pop();}
                if (!stack.isEmpty() && stack.peek() < nums[j]) {return true;}
                stack.push(nums[j]);
            }
        }
        return false;
    }
}

// Note there's other ways in the solution, like using the array instead of the stack. but the space is still O(n) since we still need the mins array.

/* A easier way - one pass using stack
discussion post: https://leetcode.com/problems/132-pattern/discuss/906789/Short-Java-O(N)-Solution-with-Detailed-Explanation-and-Sample-Test-Case-or-Stack-or-100
*/