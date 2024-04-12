/** 2024/4/10
Naive way: O(n^3) to O(n^2)
Since it's see from the right, we can use monotonic stack from right to left. Whenever we have curVal > peekVal, we pop and keep count, cause the count is just the visible people in between curVal and its next larger.
Time O(n) space O(n)
---------------------
seems like it can also be solved from left to right, but right to left is more intuitive to me.
 */
class Solution {
    public int[] canSeePersonsCount(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[heights.length];
        for (int i = heights.length-1; i >= 0; i--) {
            int count = 0;
            while (!stack.isEmpty() && heights[i] > heights[stack.peek()]) {
                stack.pop();
                count++;
            }
            res[i] = stack.isEmpty()? count : count+1;
            stack.push(i);
        }
        return res;
    }
}