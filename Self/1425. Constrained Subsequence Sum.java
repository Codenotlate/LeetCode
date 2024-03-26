/**
This is on top level a DP problem. Where we define dp[i] as the maxSum of a sequence ending at position i. Since k defines the max step we can take. we then have dp[i+1] = nums[i+1] + max(0, dp[i],dp[i-1],...,dp[i-k]).
==== Naive way: would be directly go back k steps to check for each i. This will have time as O(nk), exceeding time limit, space as O(n).
==== DP + Monotonic deque way: very similar to question 239. In this way we can optimize to O(n) time and O(n+k)=O(n) space.
==== DP + monotonic deque + optimized space way: use nums directly for storing dp instead of creating new ones. Then space will be only for deque which is O(k).

-----------------------
from editorial:
==== non-optimal way1 [heap]: dealing with get max from k dps using a max heap. Then the time becomes O(nlogn) - logn because for heap we can only remove the peek value, and can't really maintain the heap size as k.
==== non-optimial way2 [treeMap]: optimized from above way. By using a treeMap, we now able to remove elements outside the k size window. Time O(nlogk)
"""In Java, we will use TreeMap. Each key will be a value in dp which we will map to its frequency. To remove dp[i - k] from the window, we will decrement its frequency, and if its frequency becomes 0, we will delete the key."""
class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        TreeMap<Integer, Integer> window = new TreeMap();
        window.put(0, 0);
        
        int dp[] = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
            dp[i] = nums[i] + window.lastKey();
            window.put(dp[i], window.getOrDefault(dp[i], 0) + 1);
            
            if (i >= k) {
                window.put(dp[i - k], window.get(dp[i - k]) - 1);
                if (window.get(dp[i - k]) == 0) {
                    window.remove(dp[i - k]);
                }
            }
        }
        
        int ans = Integer.MIN_VALUE;
        for (int num : dp) {
            ans = Math.max(ans, num);
        }
        
        return ans;
    }
}



 */

class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        int[] dp = new int[nums.length];
        Deque<Integer> deque = new ArrayDeque<>();
        dp[0] = nums[0];
        deque.add(0);
        int maxdp = dp[0];
        for (int i = 1; i < nums.length; i++) {
            while(deque.size() > 0 && i-deque.peek()>k) {deque.poll();}
            int maxk = dp[deque.peek()];
            dp[i] = nums[i]+Math.max(maxk,0);
            while (deque.size() > 0 && dp[deque.peekLast()] <= dp[i]) {deque.pollLast();}
            deque.addLast(i);
            maxdp = Math.max(maxdp, dp[i]);
        }
        return maxdp;

    }
}