class Solution {
	// M1: math way: find all prime factors of n and get their sum
	// time O(n?) (O(sqrt(N) when N is the square of a prime)) space O(1)
    public int minSteps(int n) {
    	int res = 0;
    	int d = 2;
    	while (n > 1) {
    		while (n % d == 0) {
    			res += d;
    			n /= d;
    		}
    		d++;
    	}
    	return res;
    }

}



class Solution {
	// M2: optimized dp way: dp[i] = dp[j] + i / j 
	// and proved if j is the largest factor of i,then do[j] + i / j should be the smallest among all factors
    // time O(n^2) space O(n)
    public int minSteps(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
        	for (int j = i / 2; j >= 1; j--) {
        		if (i % j == 0) {
        			dp[i] = dp[j] + i / j;
        			break;
        		}
        	}
        }
        return dp[n];
    }
}

// excellent explanation - also related to 4 keys keyboard problem(need subscribe)
//https://leetcode.com/problems/2-keys-keyboard/discuss/105932/Java-solutions-from-naive-DP-to-optimized-DP-to-non-DP

// 4 keys keyboard problem
//https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484469&idx=1&sn=e8d321c8ad62483874a997e9dd72da8f&chksm=9bd7fa3daca0732b316aa0afa58e70357e1cb7ab1fe0855d06bc4a852abb1b434c01c7dd19d6&scene=21#wechat_redirect

// math way in solution page
// https://leetcode.com/problems/2-keys-keyboard/solution/











