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




// review
/*Initial thought
naive way is to list out all substrings and traverse each of them to check if it's palindromic. This will take n + 2(n-1) + 3(n-2) + ---+ n(n-(n-1)) = O(n^3)
improved way1: use regular dp. Since there's repetitive sub problems. dp[i][j] = dp[i+1][j-1] && s[i] == s[j]. There are two base cases: dp[i][i] = true; dp[i][i+1] = s[i] == s[i+1].And we assume i <= j, thus we loop for i from n-1 to 0, loop j from n-1 to i. And we can optimize the space to O(n).
improved way2: two types of center - one letter or two dup letters. we can expand from the center. In total O(n) centers and the expansion took O(n) time for each center, thus the time is O(n^2). For each expandsion, we stop when the two sides don't match. And we only need to keep O(1) space.
*/
// dp way1: O(n^2) time O(n) space
class Solution {
    public int countSubstrings(String s) {
        int res = 0;
        int n = s.length();
        boolean[] dp = new boolean[n];
        // first base case: single char
        dp[n-1] = true;
        res++;
        for (int i = n-2; i >= 0; i--) {
            for (int j = n-1; j >= i+2; j--) {
                dp[j] = dp[j-1] && s.charAt(i) == s.charAt(j);
                if (dp[j]) {res++;}
            }
            dp[i] = true;
            res++;
            dp[i+1] = s.charAt(i) == s.charAt(i+1);
            if (dp[i+1]){res++;}
            
        }
        return res;
    }
}
// dp way2: O(n^2) time O(1) space
class Solution {
    public int countSubstrings(String s) {
        int res = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            res += count(s, i, i);
            if (i < n - 1 && s.charAt(i) == s.charAt(i+1)) {res += count(s, i, i+1);}
        }
        return res;
    }
    
    private int count(String s, int i, int j) {
        int res = 0;
        while(i >= 0 && j <= s.length() - 1 && s.charAt(i) == s.charAt(j)) {
            res++;
            i--;
            j++;
        }
        return res;
    }
}




// Review - 23/1/25 (same two methods as above)
/*Thoughts
Way1: for each char in s, use it as the center of palindromic string and expand to the two sides, similarly use it along with its right neighbor as the center and count as well. 
time O(n^2) space O(1)

Way2: assume dp(i,j) represents whether s[i:j] is palindromic.  dp[i][j] = dp[i+1][j-1] && s[i] == s[j]. In the end, we can count the number of true value in dp[][]. The space can be optimized to O(n). Time O(n^2).
Again two base cases are single char and double chars.

 */
// M1
class Solution {
    public int countSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += singleCount(s, i, i) + singleCount(s, i, i+1);
        }
        return count;
    }

    private int singleCount(String s, int i, int j) {
        int count = 0;
        while ( i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)){
            count++;
            i--;
            j++;
        }
        return count;

    }
}


// M2 O(n) space way
class Solution {
    public int countSubstrings(String s) {
        int count = 0;
        boolean[] dp = new boolean[s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = s.length() - 1; j >= i; j--) {
                // base case
                if (i == j || (i+1 == j && s.charAt(i) == s.charAt(j))) {
                    dp[j] = true;
                    count++;
                } else {
                    dp[j] = dp[j-1] && (s.charAt(i) == s.charAt(j));
                    if (dp[j]) {count++;}
                }
            }
        }
        return count;
    }    
}














