class Solution {
	// regular dfs time O(n * 2^n) space O(n)
    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        List<String> curPartition = new LinkedList<>();
        dfs(s, res, curPartition);
        return res;
    }

    private void dfs(String s, List<List<String>> res, List<String> curPartition) {
    	// base case
    	if (s.length() == 0) {
    		res.add(new LinkedList<>(curPartition));
    		return;
    	}
    	for (int i = 0; i < s.length(); i++) {
    		if (isPalindrome(s, 0, i)) {
    			curPartition.add(s.substring(0, i + 1));
    			dfs(s.substring(i + 1), res, curPartition);
    			curPartition.remove(curPartition.size() - 1);
    		}
    	}
    }

    private boolean isPalindrome(String s, int start, int end) {
    	if (start > end) {return false;}
    	while (start <= end) {
    		if (s.charAt(start) != s.charAt(end)) {return false;}
    		start++;
    		end--;
    	}
    	return true;
    }
}


// space tradeoff with time using dp, time still O(n * 2^n), space O(n^2)
// but eliminate the O(n) for checking is palin, only left with O(n) for substring
// using dp[start][end] to represent where start to end in s is palin
// the dp[start][end] = dp[start + 1][end - 1] && s[start] == s[end]
// also need to add a position into dfs and pass original s without substring, cause we only pass substring in dfs, we need to find the original position in 2d matrix
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        List<String> curPartition = new LinkedList<>();
        boolean[][] isPalindrome = new boolean[s.length()][s.length()];
        // build the 2d dp 
        for (int j = 0; j < s.length(); j++) {
        	for (int i = 0; i <= j; i++) {
        		if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || isPalindrome[i + 1][j - 1])) {
        			isPalindrome[i][j] = true;
        		}
        	}
        }
        dfs(s, res, curPartition, isPalindrome, 0);
        return res;
    }

    private void dfs(String s, List<List<String>> res, List<String> curPartition, boolean[][] isPalindrome, int startIdx) {
    	// base case
    	if (s.length() == startIdx) {
    		res.add(new LinkedList<>(curPartition));
    		return;
    	}
    	for (int i = startIdx; i < s.length(); i++) {
    		if (isPalindrome[startIdx][i]) {
    			curPartition.add(s.substring(startIdx, i + 1));
    			dfs(s, res, curPartition, isPalindrome, i + 1);
    			curPartition.remove(curPartition.size() - 1);
    		}
    	}
    }
}


// a backtracking template walk through
// https://leetcode.com/problems/palindrome-partitioning/discuss/182307/Java%3A-Backtracking-Template-General-Approach


// phase2 self: simialr to M1 above
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        List<String> curPartition = new LinkedList<>();
        dfs(s, 0, res, curPartition);
        return res;
    }

    private void dfs(String s, int curPos, List<List<String>> res, List<String> curPartition) {
        // base case
        if (curPos == s.length()) {
            res.add(new LinkedList<>(curPartition));
            return;
        }
        for (int i = 1; i <= s.length() - curPos; i++) {
            String next = s.substring(curPos, curPos + i);
            if (isPalindrome(next)) {
                curPartition.add(next);
                dfs(s, curPos + i, res, curPartition);
                curPartition.remove(curPartition.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        while (start <= end) {
            if (s.charAt(start) != s.charAt(end)) {return false;}
            start++;
            end--;
        }
        return true;
    }
}


// phase2 simialr to dp way M2 above, different way to build dp matrix
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        List<String> curPartition = new LinkedList<>();
        boolean[][] isPalindrome = new boolean[s.length()][s.length()];
        // build the 2d dp 
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j <= s.length() - 1; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || isPalindrome[i + 1][j - 1])) {
                    isPalindrome[i][j] = true;
                }
            }
        }
        dfs(s, res, curPartition, isPalindrome, 0);
        return res;
    }

    private void dfs(String s, List<List<String>> res, List<String> curPartition, boolean[][] isPalindrome, int startIdx) {
        // base case
        if (s.length() == startIdx) {
            res.add(new LinkedList<>(curPartition));
            return;
        }
        for (int i = startIdx; i < s.length(); i++) {
            if (isPalindrome[startIdx][i]) {
                curPartition.add(s.substring(startIdx, i + 1));
                dfs(s, res, curPartition, isPalindrome, i + 1);
                curPartition.remove(curPartition.size() - 1);
            }
        }
    }
}















