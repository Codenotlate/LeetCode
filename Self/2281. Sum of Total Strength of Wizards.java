/*Thoughts
Naive way: time O(n^2). Check each of the subarray which takes O(n^2) time, and for each round of expansion of subarrays with same starting point, the time to get new min and new sum will be O(1). So in total, O(n^2) for time. space O(1)

Fancy O(n) way
Similar as monotonic stack way to find min and max in subarray as in similar questions. Here the diff part is after we find the range of the subarrays an element will be the minimum, how do we get the sum of all subarrays within that range and including that element.
First, when we want sum of subarrays, cumsum will be the way. But here, not only the cumsum if being used, but also the cumsum of cumsum. That's the key to reduce the time from O(n^2) to O(n)
Here is the explanation:
e.g. we have a pos i, elem[i] will be the min between the range [l+1, r-1]. In the monotonic stack case, l will be the index in the stack, and r will be the cur index , and i will be the index being popped out from the stack since it's larger than elem[r] outside the stack.
We use every elements from [l+1] to [i] as the start of the subarray, and the end of the subarray will be ranged from [i] to [r-1]. Thus the sum will be:
cumsum(i) - cumsum(l)
cumsum(i+1) - cumsum(l)
...
cumsum(r-1) - cumsum(l)
------
cumsum(i) - cumsum(l+1)
cumsum(i+1) - cumsum(l+1)
...
cumsum(r-1) - cumsum(l+1)
--------
.......
--------
cumsum(i) - cumsum(i-1)
cumsum(i+1) - cumsum(i-1)
...
cumsum(r-1) - cumsum(i-1)

With above formulas, we can combine the positive parts accross the board, and similar to negative parts.
Positiva part: (cumsum(i) + cumsum(i+1) + ... + cumsum(r-1)) * (i - l) 
                = (cumcumsum(r-1) - cumcumsum(i-1)) * (i - l)
Negative part: (cumsum(l) + cumsum(l+1) + ... + cumsum(i-1)) * (r - i)
                = (cumcumsum(i-1) - cumcumsum(l-1)) * (r - i)
Thus we can get cumsum and cumsum of cumsum in O(n) time and utilize here.

The idea is easy to understand. But how to use mod to avoid overflow if very very trick and frustrating!!
*/
class Solution {
    public int totalStrength(int[] strength) {
        int n = strength.length;
        long[] cumsum = new long[n];
        long[] cumcumsum = new long[n];
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        long res = 0;
        long mod = (long)1e9 + 7;

        for (int r = 0; r <= n; r++) {
            int s;
            if (r < n) {
                s = strength[r];
                cumsum[r] = r == 0? (long)s : (cumsum[r-1] + (long)s) % mod;
                cumcumsum[r] = r == 0 ? cumsum[r] : (cumsum[r] + cumcumsum[r-1]) % mod;
            } else {
                s = 0;
            }

            while (stack.peek() != -1 && strength[stack.peek()] >= s) {
                int i = stack.pop();
                long popNum = (long)strength[i];
                int l = stack.peek();
                long rightcum = cumcumsum[r-1];
                long midcum = i == 0? 0 : cumcumsum[i-1];
                long leftcum = l <= 0? 0 : cumcumsum[l-1];
                long val =  (((rightcum - midcum)* (long)(i - l) - (midcum - leftcum) * (long)(r - i))% mod * popNum % mod) % mod ;
                res = (res + val) % mod;
            }
            stack.push(r);
        }
        return (int)((res + mod) % mod);
    }
}




/** 2024/4/16
Again got tricked by using mod to avoid overflow.
Other than that, the best way to notice the pattern for using prefixSum of prefixSum is to assume a case for index i and least out all sums required for array say from i-2 to i+3.
Time O(n) space O(n)

 */
class Solution {
    public int totalStrength(int[] strength) {
        int n = strength.length;
        long[] pre = new long[n];
        long[] prepre = new long[n];
        int mod = 1000000007;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                pre[i] = strength[i];
                prepre[i] = strength[i];
            } else {
                pre[i] = (pre[i-1]+strength[i]);
                prepre[i] = (prepre[i-1]+pre[i]);
            }
        }

        long res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i <= n; i++) {
            while(stack.peek()!=-1 && (i == n || strength[stack.peek()] >= strength[i])) {
                int pop = stack.pop();
                long rightPart = (prepre[i-1]-(pop>=1?prepre[pop-1]:0)) % mod * (pop-stack.peek()) % mod;
                long leftPart =  ((pop>= 1? prepre[pop-1]:0) - (stack.peek() >= 1?prepre[stack.peek()-1]:0)) %mod * (i-pop) % mod; 
                res = (res%mod + strength[pop] * (rightPart-leftPart)%mod) % mod;
            }
            stack.push(i);
        }
        return (int)((res + mod) % mod);
    }
}