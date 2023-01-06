class Solution {
	// dp way: knapsack with multi-dim weight constraints
	// time O(len of strs * m * n) space O(m * n)
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String s: strs) {
        	int zeros = 0, ones = 0;
        	for (char c : s.toCharArray()) {
        		if (c == '0') {
        			zeros++;
        		} else {
        			ones++;
        		}
        	}

        	for (int curM = m; curM >= zeros; curM--) {
        		for (int curN = n; curN >= ones; curN--) {
        			dp[curM][curN] = Math.max(dp[curM][curN], dp[curM - zeros][curN - ones] + 1);
        		}
        	}
        }
        return dp[m][n];
    }
}

// Review 23/1/6 Same idea as above
/* Thought
Knapsack problem. resources are finite. dp[mi][ni][posi] represents the result for m and n from the strs[posi:]. dp[mi][ni][posi] = max(1 + dp[mi-pm][ni-pn][posi+1], dp[mi][ni][posi+1])
Or if dp[mi][ni][posi] represents the result for m and n from the strs[:posi]. Then dp[mi][ni][posi] = max(dp[mi][ni][posi-1], dp[mi-pm][ni-pn][posi-1] + 1])

The first way posi go backwards, the second way posi go forwards. Either way the space can be optimized from 3d to 2d. And the loop for m and n need to be backwards.

time O(m*n*len) space O(m*n).


*/
// second dp way
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m+1][n+1];
        for(String s: strs) {
            int[] mnCount = getCount(s);
            // don't forget the equal to 0 case here.
            for (int mi = m; mi >= 0; mi--) {
                for (int ni = n; ni >= 0; ni--) {
                    if (mi >= mnCount[0] && ni >= mnCount[1]) {
                        dp[mi][ni] = Math.max(dp[mi][ni], 1 + dp[mi-mnCount[0]][ni-mnCount[1]]);
                    }                    
                }
            }
        }
        return dp[m][n];
    }

    private int[] getCount(String s) {
        int count0 = 0;
        int count1 = 0;
        for (char c: s.toCharArray()) {
            if (c == '0') {count0++;}
            else {count1++;}
        }
        return new int[]{count0, count1};
    }
}





