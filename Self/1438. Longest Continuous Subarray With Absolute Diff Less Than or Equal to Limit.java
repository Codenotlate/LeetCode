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




// 2024.9.24 (self come up)
// two monotonic deque way, time O(n) space O(n)
// ALWAYS remember what you put in deque is index, when comparing use nums[index]!!
class Solution {
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> dq1 = new LinkedList<>(); //repre minStack (min at left)
        Deque<Integer> dq2 = new LinkedList<>(); // repre maxStack (max at left)
        int left = 0;
        int right = 0;
        int size = 0;
        while (right < nums.length) {
            // add right to dq1 and dq2 accordingly
            while (!dq1.isEmpty() && nums[dq1.peekLast()] >= nums[right]) {
                dq1.pollLast();
            }
            dq1.addLast(right);
            while (!dq2.isEmpty() && nums[dq2.peekLast()] <= nums[right]) {
                dq2.pollLast();
            }
            dq2.addLast(right);

            // shrink the window if invalid (dq won't be empty cause 1 size array always valid)
            while (left < nums.length && nums[dq2.peekFirst()] - nums[dq1.peekFirst()] > limit) {
                left++;
                // poll out data outside the current window
                while (dq1.peekFirst() < left) {dq1.pollFirst();}
                while (dq2.peekFirst() < left) {dq2.pollFirst();}
            }
            // update size for valid window
            size = Math.max(size, right-left+1);
            right++;
        }
        return size;

    }
}


