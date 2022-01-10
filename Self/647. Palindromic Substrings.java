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



// M2: another way: expand from all possible centers
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



// Phase 3 improve M1 to space O(n)
class Solution {
    public int countSubstrings(String s) {
        boolean[] dp = new boolean[s.length()];
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = s.length() - 1; j >= i; j--) {
                // don't forget two base cases
                if (j == i || (j == i + 1) && s.charAt(j) == s.charAt(i)) {
                    dp[j] = true;}
                else {
                    dp[j] = dp[j - 1] && (s.charAt(i) == s.charAt(j));
                }               
                if (dp[j]) {count++;}
            }           
        }
        return count;
    }
}


// Phase3 M2
class Solution {
    // extend from center to outside
    public int countSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += extend(s, i, i);
            count += extend(s, i, i+1);
        }
        return count;
    }
    
    private int extend(String s, int start, int end) {
        int count = 0;
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
            count++;
        }
        return count;
    }
}



// Review self: similar to above M2: expand from all possible centers
// two types of center: single char(to odd-len) and two consecutive chars(to even-len)
// time O(n^2) space O(1)
class Solution {
    public int countSubstrings(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += countPalin(s, i, i);
            if (i < s.length() - 1 && s.charAt(i) == s.charAt(i+1)) {
                res += countPalin(s,i,i+1);
            }
        }
        return res;
    }
    
    private int countPalin(String s, int p1, int p2) {
        int res = 1;
        p1--;
        p2++;
        while (p1 >= 0 && p2 < s.length()) {
            if (s.charAt(p1) == s.charAt(p2)) {res++; p1--; p2++;}
            else {break;}
        }
        return res;
    }
}

// try dp way
// time O(n^2) space O(n)
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        int[] dp = new int[n];
        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n-1; j >= i; j--) {
                if (i == j || (i == j - 1 && s.charAt(i) == s.charAt(j))) {
                    dp[j] = 1;
                } else {
                    int count = s.charAt(i) == s.charAt(j) ? 0 : -1;
                    dp[j] = dp[j - 1] + count;
                }
                if (dp[j] == 1) {res++;}

            }
        }
        return res;
    }
}























