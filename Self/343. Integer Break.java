class Solution {
	// M1: self way dp, time O(n^2), space O(n)
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for(int i = 3; i <= n; i++) {
        	for (int j = 1; j <= i / 2 + 1; j++) {
        		dp[i] = Math.max(dp[i], Math.max(j, dp[j]) * Math.max(i - j, dp[i - j]));
        	}
        }
        return dp[n];

    }
}



// can reduce it to a knapsack problem
// https://leetcode.com/problems/integer-break/discuss/80707/Easy-Java-DP-solution-with-explanation-typical-knapsack-problem
// the volumn of knapsack is the sum j and the items are numbers from 1 to j - 1,
// and each item can be used many times.
// 1d dp is optimized from 2d dp
// time O(n^2), space O(n)
class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
        	for (int j = i; j <= n; j++) {
        		dp[j] = Math.max(dp[j], dp[j - i] * i);
        	}
        }
        return dp[n];
    }
}


// M3: math way
// https://leetcode.com/problems/integer-break/discuss/80785/O(log(n))-Time-solution-with-explanation
// proved for numbers >= 4, it can always be split to n * (n-2) or n * (n - 3) that has the same sum but with larger product
// thus the final split of n should be comprised only by 2 or 3.
// and if n % 3 == 0 then all 3s, if n%3 == 1 then 3*a + 2*2， if n % 3 == 2, then 3*a + 2
// there can't be more than 2 twos, since 3 * 3 > 2 * 2 * 2
class Solution {
    public int integerBreak(int n) {
        if (n == 2) {return 1;}
        if (n == 3) {return 2;}
        if (n % 3 == 0) {
        	return (int) Math.pow(3, n / 3);
        } else if (n % 3 == 1) {
        	return (int) Math.pow(3, (n - 4) / 3) * 4;
        } else {
        	return (int) Math.pow(3, n / 3) * 2;
        }
    }
}




// Review 23/1/8
/* Thoughts
Can view it as a knapsack problem, each item is unlimited, from 1 to n - 1. The total amount is n. Let dp[i][amount] represents the max product for amount from [i : ]. then dp[i][amount] = max(i * dp[i][amount - i], dp[i+1][amount]). The space can be optimized to O(n). The order will be i -> n-1 to 1,  amount small to large.
Time O(n^2)
 */

class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        for (int i = n-1; i >= 1; i--) {
            for (int amount = i; amount <= n; amount++) {
                dp[amount] = Math.max(dp[amount], i * dp[amount - i]);
            }
        }
        return dp[n];
    }
}







