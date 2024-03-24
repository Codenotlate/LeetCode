// Phase3 solution M1: using Deque
// time O(n) space O(n)
/*
Key idea is to make sure elements in deque only includes those within current window and also have the potential to become max for future windows
for the simplicity of window check, we put the index in the deque
each time, we remove the index out of window from deque start side
and remove index with values <= cur (no potential to become max) from deque end side.

*/
// M1 is essentially a Monotonic queue Algo
// see explanation
//https://1e9.medium.com/monotonic-queue-notes-980a019d5793

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {        
        int n = nums.length;
        if (n == 1 || k == 1) {return nums;}
        if (k >= n) {k = n;}
        int[] res = new int[n - k + 1];
        int resPos = 0;

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
        	// remove index out of window from front
        	if (deque.size() > 0 && deque.peek() < i - k + 1) {
        		deque.poll();
        	}
        	// remove index with no potential value from the end
        	while (deque.size() > 0 && nums[deque.peekLast()] <= nums[i]) {
        		deque.pollLast();
        	}
        	deque.add(i);

        	if (i >= k - 1) {
        		res[resPos++] = nums[deque.peek()];
        	}
        }
        return res;
    }
}




// M2: dp way
/*
Separate into diff groups, check the max from block left to current position
check the max from block right to current position
for window [i, j], max is max(right[i], left[j])

time O(n)
space O(n)
*/


class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (k > n) {k = n;}
        if (k == 1 || n == 1) {return nums;}

        int[] left = new int[n];
        int[] right = new int[n];
        // scan to build left
        for (int i = 0; i < n; i++) {
        	if (i % k == 0) {
        		left[i] = nums[i];
        	} else {
        		left[i] = Math.max(left[i - 1], nums[i]);
        	}
        }
        // scan to build right
        for (int i = n - 1; i >= 0; i--) {
        	if (i == n - 1 || i % k == k - 1) {
        		right[i] = nums[i];
        	} else {
        		right[i] = Math.max(right[i + 1], nums[i]);
        	}
        }

        int[] res = new int[n - k + 1];

        for (int i = 0; i < n - k + 1; i++) {
        	res[i] = Math.max(right[i], left[i + k - 1]);
        }
        return res;


    }
}













/*
Some comments about M3 dp way:
I don't think the approach 3 meet the description of this question. We are told that You can only see the k numbers in the window which means we should treat it as a data stream instead of a static data list. Therefore we shouldn't do any preprocess on it.
*/







// summary of all method
// https://leetcode.com/problems/sliding-window-maximum/discuss/458121/Java-All-Solutions-(B-F-PQ-Deque-DP)-with-Explanation-and-Complexity-Analysis




/* 2024/3/22 self
Naive way is to check each of the k-size window for the new max. Time O(k *(n-k)) space O(1)
An optimized way is to reduce the checking of the max of the new k window. The key problem to solve is to know the second largest of each window when the max is being removed from the left of the window.
Similar to concept of monotonic stack, we can use a monotonic deque here. Where we store the position of each element, and the corresponding element in non-ascending order. The benefit of storing index here is we can easily determine whether a number has been removed from the current window by comparing (cur index - number index + 1) with k.
Four steps: 1) compare i with first_index, pop if first_index is no longer in the window. No need to use while here bcs at most we will remove only one element. 2) pop all element with corresponding number <= current number. 3) based on deque first number and the current number, add the max of current window ending with current i. 4) Add i to the deque
The reason we use deque instead of a stack here is bcs we need to compare with the first_index, while stack doesn't keep track of the first element inside. Also we need to remove elements in both directions and stack doesn't support that as well.
Time O(n) space O(k)


 */

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] res = new int[nums.length-k+1];
        for (int i = 0; i < nums.length; i++) {
            if(deque.size() > 0 && i-deque.peek()+1 > k) {
                deque.poll();
            }
            while(deque.size() > 0 && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            if (i >= k-1) {
                res[i-k+1] = deque.size() == 0 ? nums[i] : Math.max(nums[i], nums[deque.peek()]);
            }
            deque.add(i);
        }
        return res;

    }
}