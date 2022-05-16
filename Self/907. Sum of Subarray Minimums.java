class Solution {
    /* Initial thought
    get all dp[i][j], which reporesents the min value in array[i:j].
    dp[i][j] = min(dp[i, j-1], arr[j])
    for i = 0 to n-1, j = i to n-1, get dp[i][j]
    result equal to sum of all dp[i][j] where j >= i
    time O(n^2) space O(n^2)    
    */
    
    /* Optimized way
    Based on above naive solution, we notice the sum is actually composed of the sum produced by each element. for each A[i], we care about how many subarrays it is in and it's the min of that subarray. Thus our goal converts to find the previous less number on the left of A[i] and next less number on the right of A[i]. Then the number of subarrays with A[i] as min will be (rightPos - i) * (i-leftPos). We sum this up for each A[i].
    And we use monotonous stack to get PLN and NLN for each A[i] in the first place. note consider duplicates, for PLN we want to get <, and for NLN we need to get <=.
    time O(n) space O(n)
    */
    public int sumSubarrayMins(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[] left = new int[arr.length];
        int[] right = new int[arr.length];
        
        // find PLN (<)
        for (int i = 0; i < arr.length; i++) {
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty()? -1: stack.peek();
            stack.push(i);
        }
        
        // find NLN (<=)
        stack = new Stack<>();
        for (int i = arr.length-1; i >= 0; i--) {
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty()? arr.length: stack.peek();
            stack.push(i);
        }
        
        long res = 0;
        long mod = 1000000007;
        for (int i = 0; i < arr.length; i++) {
            res = (res + (long)arr[i] * (i-left[i]) * (right[i] - i)) % mod;
        }
        return (int)res;
        
        
    }
}


// good discussion
// https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
// for left dist * right dist prove:
// https://lh3.googleusercontent.com/-GyygvrTJ3GY/XRlvmDTxEHI/AAAAAAAAO4E/yDc-Xvo3isgM8QFOSiVp6yUK_j9E8cwGACK8BGAs/s0/2019-06-30.jpg
// https://leetcode.com/problems/sum-of-subarray-minimums/discuss/170750/JavaC%2B%2BPython-Stack-Solution


// Another even better way, using dp and monotone stack. Here view each A[i] as the end of each subarray, and see how many array ends in A[i] and has A[i] as min.
// https://leetcode.com/problems/sum-of-subarray-minimums/discuss/170769/Java-O(n)-monotone-stack-with-DP 
class Solution {
    public int sumSubarrayMins(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        long[] dp = new long[arr.length + 1];
        stack.push(-1);
        
        long mod = 1000000007;
        long res = 0;
        for (int i = 0; i < arr.length; i++) {
            while (stack.peek() != -1 && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            dp[i + 1] = (dp[stack.peek() + 1] + arr[i] * (i - stack.peek())) % mod;
            res = (res + dp[i+1]) % mod;
            stack.push(i);
        }
        
        return (int)res;
    }
}



// Review
class Solution {
    public int sumSubarrayMins(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        // use long to deal with overflow
        long res = 0;
        
        for (int i = 0; i <= arr.length; i++) {
            while (stack.peek() != -1 && (i == arr.length || arr[i] <= arr[stack.peek()])) {
                int popIdx = stack.pop();
                res = (res + (long) arr[popIdx] * (popIdx - stack.peek()) * (i - popIdx)) % 1000000007;
            } 
            if(i < arr.length) {stack.push(i);}
        }
        
        return (int) res;
    }
}