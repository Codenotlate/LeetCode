/*Thought
The key idea is to find the first(left) and last(right) element that has wrong positions, then result = right - left + 1. 
Several ways to get the supposed position in sorted array for each num. 
M1: One way is to sort into a new array and find its position in the sorted array. time O(nlogn)
M2: Another way is to use monotone stack. Cause actually we don't care the correct pos for each nunm, instead, what we actually care is the first and last numbers with incorrect pos. meaning you can find a number smaller behind it or a number larger on its left side. Thus we use a monotone stack, and pass the array twice (forwards and then backwards) to find the left and right. time O(n) space O(n)
M3: optimize space from O(n) to O(1). Similar to M2, we care the first and last numbers with incorrect pos. For a number to be in correct position, it needs to be >= all numbers on its left (i.e. num >= max(leftside)) and also be <= all numbers on its right(i.e. num <= min(rightside)). Thus we can pass the array twice, once forward, update the max for leftside along the way, and to get the last incorrect pos. Similarly we do backwards to get the first incorrect pos.
*/

// M2: stack way time O(n) space O(n)
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int left = nums.length;
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                left = Math.min(left, stack.pop());
            }
            stack.push(i);
        }
        stack.clear();
        int right = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                right = Math.max(right, stack.pop());
            }
            stack.push(i);
        }
        return Math.max(0,right - left + 1);
    }
}
// M3: space O(1) way
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int right = -1;
        int leftmax = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            leftmax = Math.max(leftmax, nums[i]);
            if (leftmax > nums[i]) {right = i;}
        }
        int left = nums.length;
        int rightmin = Integer.MAX_VALUE;
        for (int i = nums.length - 1; i >= 0; i--) {
            rightmin = Math.min(rightmin, nums[i]);
            if(rightmin < nums[i]) {left = i;}
        }
        return Math.max(0,right - left + 1);
    }
}