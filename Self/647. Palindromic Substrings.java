// DP way
// define df(i, j) as whether substring starts at i and ends atj is palindromatic.
// then the base case is dp(i, i) = true and dp (i, i+1) = true if s[i] == s[i+1] otherwise false
// rule: dp[i][j] = dp[i+1][j-1] && s[i] == s[j]
// return number of true value in dp[][] 

// time O(n^2) space O(n^2) -> but space can be improved to O(n)
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int count = 0;
        // base case 1 & 2
        for (int i = 0; i < n; i++) {
        	dp[i][i] = true;
        	count++;
        	if (i < n-1) {
        		dp[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        		count += (dp[i][i + 1] ? 1 : 0) ;
        	}
        }

        // for len >= 3 substrings, use the dp rule
        // need to start from len = 3 to larger, otherwise the order is wrong
        for (int len = 3; len <= n; len++) {
        	for (int i = 0,j = i + len - 1; j < n; j++, i++) {
        		dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
        		count += (dp[i][j] ? 1 : 0);
        	}
        }

        return count;

    }

}



// another way: expand from all possible centers
// two types of center: single char(to odd-len) and two consecutive chars(to even-len)

class Solution {
    public int countSubstrings(String s) {
    	int count = 0;
        for (int i = 0; i < s.length(); i++) {
        	count += expandCenter(s, i, i);
        	count += expandCenter(s, i, i+1);
        }
        return count;
    }

    private int expandCenter(String s, int start, int end) {
    	int count = 0;
    	while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
    		start -= 1;
    		end += 1;
    		count += 1;
    	}
    	return count;
    }
}






















