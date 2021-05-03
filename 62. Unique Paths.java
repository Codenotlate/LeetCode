class Solution {
	// similar as 64
	// try starts from (0, 0) this time
	// dp[i, j] represents the # of paths starting from (0,0) to bottom right
	// optimize to O(n) space (1d dp)
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        // can also use Arrays.fill(dp, 1) to preset i==0 and j==0 edges
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (j == 0) {
        			dp[j] = dp[j];
        		} else if (i == 0) {
        			dp[j] = dp[j - 1];
        		} else {
        			dp[j] = dp[j] + dp[j - 1];
        		}
        	}
        }
        return dp[n - 1];
    }
}




/*
也可以直接用数学公式求解，这是一个组合问题。机器人总共移动的次数 S=m+n-2，向下移动的次数 D=m-1，那么问题可以看成从 S 中取出 D 个位置的组合数量，这个问题的解为 C(S, D)。

public int uniquePaths(int m, int n) {
    int S = m + n - 2;  // 总共的移动次数
    int D = m - 1;      // 向下的移动次数
    long ret = 1;
    for (int i = 1; i <= D; i++) {
        ret = ret * (S - D + i) / i;
    }
    return (int) ret;
}
*/