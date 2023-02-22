class Solution {
	// m1: top-donw method with memo
	// space O(n * m), time O(n * m)
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() == 0 || text2.length() == 0) {
        	return 0;
        }
        // dp(s1, i, s2, j) represents the LCS of s1[i:] and s2[j:]
        int[][] memo = new int[text1.length()][text2.length()];           	
    	for (int[] row: memo) {
    		Arrays.fill(row, -1);
    	}
        return dp(text1, 0, text2, 0, memo);
    }

    private int dp(String s1, int i, String s2, int j, int[][] memo) {
    	if (i == s1.length() || j == s2.length()) {return 0;}

    	if (memo[i][j] != -1) {
    		return memo[i][j];
    	}

    	if (s1.charAt(i) == s2.charAt(j)) {
    		memo[i][j] = 1 + dp(s1, i + 1, s2, j + 1, memo);
    	} else {
    		memo[i][j] = Math.max(dp(s1, i + 1, s2, j, memo), dp(s1, i, s2, j + 1, memo));
    	}
    	return memo[i][j];
    }
}



class Solution {
	// M2: bottom up method
	// time O(n * m) space O(n * m)
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() == 0 || text2.length() == 0) {
        	return 0;
        }
        // dp[i][j] represents the LCS of s1[0:i-1] and s2[0:j-1] inclusive
        int m = text1.length();
        int n = text2.length();
        // base case dp[...][0] = 0; dp[0][...] = 0
        int[][] dp = new int[m + 1][n + 1];   
        for (int i = 1; i <= m; i++) {
        	for (int j = 1; j <= n; j++) {
        		if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
        			dp[i][j] = dp[i - 1][j - 1] + 1;
        		} else {
        			dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
        		}
        	}
        } 
        return dp[m][n];
    }
}


// compared with the way in 23/2/22 review, this way can't guarantee the space is min(m,n). It is O(n) which might be the max(m,n).
class Solution {
	// M3: based on M2, optimize to time O(n*m), space O(min(m,n))
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() == 0 || text2.length() == 0) {
        	return 0;
        }
        // dp[i][j] represents the LCS of s1[0:i-1] and s2[0:j-1] inclusive
        int m = text1.length();
        int n = text2.length();
        // base case dp[...][0] = 0; dp[0][...] = 0
        int[] dp = new int[n + 1];   
        for (int i = 1; i <= m; i++) {
        	// represents the value of dp[i-1][j-1]
        	int preRowCol = 0;
        	for (int j = 1; j <= n; j++) {
        		// store dp[i-1][j] before cover it with dp[i][j]
        		int preRow = dp[j];
        		if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
        			dp[j] = preRowCol + 1;
        		} else {
        			dp[j] = Math.max(dp[j - 1], dp[j]);
        		}
        		// dp[i-1][j] in next round of j becomes dp[i-1][j-1]
        		preRowCol = preRow;
        	}
        } 
        return dp[n];
    }
}











// top-down method + bottom-up method
// https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487860&idx=1&sn=f5759ae4f22f966db8ed5a85821edd34&chksm=9bd7ef7caca0666a628fe838dee6d5da44b05eadf01fd7e87fcef813430c8e6dc3eb3c23e15f&scene=21#wechat_redirect
// how to convert from 2d to 1d, using temp to store overlap part
// https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485824&idx=1&sn=09caa56172729cf8cf1b53089e8dee55&chksm=9bd7f788aca07e9e4149f384ec5e279adadec75a2828a76066c56b4789209fb1cd54f9e63f9d&scene=21#wechat_redirect

/*
对于两个子序列 S1 和 S2，找出它们最长的公共子序列。

定义一个二维数组 dp 用来存储最长公共子序列的长度，其中 dp[i][j] 表示 S1 的前 i 个字符与 S2 的前 j 个字符最长公共子序列的长度。考虑 S1i 与 S2j 值是否相等，分为两种情况：

当 S1i==S2j 时，那么就能在 S1 的前 i-1 个字符与 S2 的前 j-1 个字符最长公共子序列的基础上再加上 S1i 这个值，最长公共子序列长度加 1，即 dp[i][j] = dp[i-1][j-1] + 1。
当 S1i != S2j 时，此时最长公共子序列为 S1 的前 i-1 个字符和 S2 的前 j 个字符最长公共子序列，或者 S1 的前 i 个字符和 S2 的前 j-1 个字符最长公共子序列，取它们的最大者，即 dp[i][j] = max{ dp[i-1][j], dp[i][j-1] }。
综上，最长公共子序列的状态转移方程为：
if s1i == s2j:  dp[i][j] = dp[i-1][j-1] + 1
else: dp[i][j] = max(dp[i-1][j], dp[i][j-1])


对于长度为 N 的序列 S1 和长度为 M 的序列 S2，dp[N][M] 就是序列 S1 和序列 S2 的最长公共子序列长度。

与最长递增子序列相比，最长公共子序列有以下不同点：

针对的是两个序列，求它们的最长公共子序列。
在最长递增子序列中，dp[i] 表示以 Si 为结尾的最长递增子序列长度，子序列必须包含 Si ；在最长公共子序列中，dp[i][j] 表示 S1 中前 i 个字符与 S2 中前 j 个字符的最长公共子序列长度，不一定包含 S1i 和 S2j。
在求最终解时，最长公共子序列中 dp[N][M] 就是最终解，而最长递增子序列中 dp[N] 不是最终解，因为以 SN 为结尾的最长递增子序列不一定是整个序列最长递增子序列，需要遍历一遍 dp 数组找到最大者。
*/





// review self
class Solution {
    /* initial thought
    The longest possible answer is min(t1.len, t2.len). And there are alot of repetitive subproblems. Thus can consider dp or recursion+memo
    i stands for pos in s1, and j for s2. Then:
    dp[i][j] = s1[i] == s2[j] ? 1 + dp[i+1][j+1] : max(dp[i+1][j], dp[i][j+1])
    where dp[i][j] represents the longestlen result for s1[i:] and s2[j:]
    */
    // M1: recursion + memo timeO(l1*l2) space O(l1 * l2)
    public int longestCommonSubsequence(String text1, String text2) {
        // if(text1.length() < text2.length()) {
        //     String temp = text1;
        //     text1 = text2;
        //     text2 = temp;
        // } // unnecessary
        int[][] memo = new int[text1.length()][text2.length()];
        for (int[] row: memo) {Arrays.fill(row, -1);}
        return getlongestLen(text1, text2, 0, 0, memo);
    }
    
    private int getlongestLen(String s1, String s2, int i, int j, int[][] memo) {
        if(i >= s1.length() || j >= s2.length()) {return 0;}
        if (memo[i][j] != -1) {return memo[i][j];}
        memo[i][j] = s1.charAt(i) == s2.charAt(j) ? 1+getlongestLen(s1, s2, i+1, j+1, memo) : Math.max(getlongestLen(s1, s2, i+1, j, memo),getlongestLen(s1, s2, i, j+1, memo));
        return memo[i][j];
    }
}

class Solution {
    // M2: dp timeO(l1*l2) space O(l1 * l2). Similar as above M3 but in backwards order
    // from above formula we can see dp[i][j] depends on dp[i+1][j+1], dp[i+1][j], dp[i][j+1]. Thus we can optimize space from O(l1*l2) to O(min(l1, l2)). And dp direction should be i and j both backwards.
    public int longestCommonSubsequence(String text1, String text2) {
        int[] memo = new int[text2.length()+1];
        for (int i = text1.length() - 1; i >= 0; i--) {
            int previ1j1 = 0;
            for (int j = text2.length() - 1; j>=0; j--) {
                int tempprev = memo[j];
                memo[j] = text1.charAt(i) == text2.charAt(j) ? 1 + previ1j1 : Math.max(memo[j+1], memo[j]);
                previ1j1 = tempprev;
            }
        }
        
        return memo[0];
    }
}





// Review
/*Initial thought
assume text1 always longer than text2. Then two pointers starting 0 for two string. if they equal, p1++, p2++;  if not equal, either p1++ or p2++. This can be converted to a dp problem. dp[i][j] = 1+dp[i+1][j+1] if text1[i] == text2[j]; dp[i][j] = max(dp[i+1][j], dp[i][j+1])
Thus time O(m*n) space can be optimized to O(min(m,n)).

*/
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() < text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        int m = text1.length();
        int n = text2.length();
        int[] dp = new int[n + 1];
        int dp_prev = 0;
        for (int i = m-1; i >= 0; i--) {
            // don't forget to reset
            dp_prev = 0;
            for(int j = n-1; j >= 0; j--) {
                int temp = dp[j];
                if(text1.charAt(i) == text2.charAt(j)) {
                    dp[j] = 1 + dp_prev;
                } else {
                    dp[j] = Math.max(dp[j], dp[j+1]);
                }
                dp_prev = temp;
            }
        }
        return dp[0];
    }
}






// Review 23/2/22 - still need hint from solution
/* Thoughts - need hint from the solution
Assume we have two pointers. If s1[i] == s2[j] then, i++, j++. Otherwise, either i++ or j++. Thus if we use dp[i][j] to represent the result for s1[:i] and s2[:j]. Then:
if s1[i] == s2[j], dp[i][j] = d[i-1][j-1] +1
Otherwise, dp[i][j] = max(dp[i-1][j], dp[i][j-1])
(similarly, if we assume dp[i][j] is result for s1[i:] and s2[j:], we also can have similar dp formula except with reverse direction)
M1: we can use dfs + memo as top-down way. time & space both O(mn)
M2: bottom-up DP way. time O(mn) space can be optimized to O(min(m,n))

*/
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() < text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        int[] dp = new int[text2.length()+1];
        for (int i = 0; i < text1.length(); i++) {
            int dp_i1j1 = 0;
            for (int j = 1; j <= text2.length(); j++) {
                int prev = dp[j]; // for dp[i-1][j]
                if (text1.charAt(i) == text2.charAt(j-1)) {
                    dp[j] = dp_i1j1 + 1;
                } else {
                    dp[j] = Math.max(dp[j-1], dp[j]);
                }
                dp_i1j1 = prev; // become dp[i-1][j-1] for next round of j
            }
        }
        return dp[dp.length - 1];
    }
}
