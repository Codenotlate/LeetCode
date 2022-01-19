// Phase 3 solution
// M1: DP way
// each position transfers two messages: max and min cumProd ends on the position
// because due to the negative numbers, the max could potentially become the max in the future.

class Solution {
    public int maxProduct(int[] nums) {
        int maxPrev = 1;
        int minPrev = 1;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
        	int temp = maxPrev;
        	maxPrev = Math.max(Math.max(maxPrev * nums[i], minPrev * nums[i]), nums[i]);
        	minPrev = Math.min(Math.min(temp * nums[i], minPrev * nums[i]), nums[i]);
        	res = Math.max(res, maxPrev);
        }

        return res;
    }
}


/* Related dicussion for M1
https://leetcode.com/problems/maximum-product-subarray/discuss/48230/Possibly-simplest-solution-with-O(n)-time-complexity

The interesting thing about this solution is that it depends not only on a state (the largest product that can be obtained by a sequence ending in the previous number), but two states (the largest and smallest products). A simpler dp problem might just create a dp[] and put the maximum value in it(in this case, the largest product). But this solution show us a new way: Our dp array can store more data than just a single value. The reason why the above solution does not use the dp array is that dp[i] only depends on dp[i - 1] so there is no need to save all the previous max and min data, just save the previous max and min value(dp[i - 1]). The following code uses the custom inner class Tuple so that both imax and imin can be stored, and all imax and imin are stored in the dp array. Although it is a bit more complicated, it helps to deepen understanding.
这道题妙就妙在它不仅仅依赖了一个状态（前一个数所能获得的最大乘积），而是两个状态（最大和最小乘积）。比较简单的dp问题可能就只是会建立一个dp[]，然后把最大值放到其中。但是这道题给我们打开了新的思路：我们的dp数组里面可以存更多的信息。而上面的解法之所以没有用dp数组的原因是dp[i]只依赖于dp[i - 1]因此没有必要把前面所有的信息都存起来，只需要存前一个dp[i-1]的最大和最小的乘积就可以了。下面的代码使用了自定义的内部类Tuple,从而可以同时存imax和imin,并将所有的imax和imin存到了dp数组中。虽然稍显复杂，但是有助于加深理解。

*/



// Phase3 solution M2: another way to think about the question

class Solution {
    public int maxProduct(int[] nums) {
    	int n = nums.length;
    	int prefixProd = 1;
    	int suffixProd = 1;
    	int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
        	prefixProd *= nums[i];
        	suffixProd *= nums[n - i - 1];
        	res = Math.max(res, Math.max(prefixProd, suffixProd));
        	if (prefixProd == 0) {prefixProd = 1;}
        	if (suffixProd == 0) {suffixProd = 1;}
        }
        return res;
    }
}




/* related discussion about M2
https://leetcode.com/problems/maximum-product-subarray/discuss/183483/JavaC%2B%2BPython-it-can-be-more-simple

First, if there's no zero in the array, then the subarray with maximum product must start with the first element or end with the last element. And therefore, the maximum product must be some prefix product or suffix product. So in this solution, we compute the prefix product A and suffix product B, and simply return the maximum of A and B.

Why? Here's the proof:

Say, we have a subarray A[i : j](i != 0, j != n) and the product of elements inside is P. Take P > 0 for example: if A[i] > 0 or A[j] > 0, then obviously, we should extend this subarray to include A[i] or A[j]; if both A[i] and A[j] are negative, then extending this subarray to include both A[i] and A[j] to get a larger product. Repeating this procedure and eventually we will reach the beginning or the end of A.

What if there are zeroes in the array? Well, we can split the array into several smaller ones. That's to say, when the prefix product is 0, we start over and compute prefix profuct from the current element instead. And this is exactly what A[i] *= (A[i - 1]) or 1 does.

*/


// Reveiw self
// similar as above M1
// time O(n) space O(n)
class Solution { 
    public int maxProduct(int[] nums) {
        int[][] dp = new int[nums.length][2];
        int max = nums[0];
        dp[0] = new int[]{nums[0], nums[0]};
        for (int i = 1; i < nums.length; i++) {
            // for every position, keep track of the minproduct including current position as well as maxproduct. It is determined by (nums[i], dp[i-1][min] * nums[i], dp[i-1][max] * nums[i])
            int prevlow = dp[i-1][0] * nums[i];
            int prevhigh = dp[i-1][1] * nums[i];
            dp[i][0] = Math.min(nums[i], Math.min(prevlow, prevhigh));
            dp[i][1] = Math.max(nums[i], Math.max(prevlow, prevhigh));
            max = Math.max(max, dp[i][1]);
        }
        return max;
    }
}

// optimize above method space to O(1)
class Solution { 
    public int maxProduct(int[] nums) {
        int max = nums[0];
        int prevlow = nums[0];
        int prevhigh = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // for every position, keep track of the minproduct including current position as well as maxproduct. It is determined by (nums[i], dp[i-1][min] * nums[i], dp[i-1][max] * nums[i])
            prevlow *= nums[i];
            prevhigh *= nums[i];
            int curlow = Math.min(nums[i], Math.min(prevlow, prevhigh));
            prevhigh = Math.max(nums[i], Math.max(prevlow, prevhigh));
            max = Math.max(max, prevhigh);
            prevlow = curlow;
        }
        return max;
    }
}














