/*Thought
actually asking longest subarray with max-min <= limit.
sliding window. Tricky part is how to decide the min and max when shrink the window.
One way is to use two PQ for all number in current window, so we can use PQ to know the new min and max when current number is removed from the window. This will be O(nlogn) time.
Another better way is to use two monotonic deque. For the max deque, keep the number inside always in non-ascending order. For the min deque, keep the numbers inside always in non-descending order. When the current number moved from shrinking the window, we check current number with the pos0 number in max deque and min deque. If current number equals to them, remove the equaled one from the deque, meaning this value is moved out of the window and can no longer be the min/max of the new window.
This takes O(n) time.
*/
class Solution {
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> maxDeque = new LinkedList<>();
        Deque<Integer> minDeque = new LinkedList<>();
        int res = 0;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < nums[right]) {maxDeque.pollLast();}
            maxDeque.addLast(nums[right]);
            while (!minDeque.isEmpty() && minDeque.peekLast() > nums[right]) {minDeque.pollLast();}
            minDeque.addLast(nums[right]);
            // notice this part can actually be removed, because we are only interested in the length, and every time the current array is > limit, we will move the left and right at the same time, meaning teh historical longest valid subarray [left, right-1] will never be shrinked but possibly expanded in later operation. 
            // Thus this part can be removed, and in the end we return right - left.
            if (maxDeque.peekFirst() - minDeque.peekFirst() <= limit) {
                res = Math.max(res, right - left + 1);
            } else {
                if (minDeque.peekFirst() == nums[left]) {minDeque.pollFirst();}
                if (maxDeque.peekFirst() == nums[left]) {maxDeque.pollFirst();}
                left++;
            }
            right++;
        }
        return res;
        
    }
}


// from discussion: https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/discuss/609771/JavaC%2B%2BPython-Deques-O(N)
// check the solution and also the explanation code in the comment of that post

// Deque interface in Java: https://www.geeksforgeeks.org/deque-interface-java-example/



