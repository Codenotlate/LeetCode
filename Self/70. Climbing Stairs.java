class Solution {
	// use DP
    public int climbStairs(int n) {
        if (n <= 2) {return n;}
        int pre1 = 1;
        int pre2 = 2;
        int i = 3;
        while (i <= n) {
        	int cur = pre1 + pre2;
        	pre1 = pre2;
        	pre2 = cur;
        	i++;
        }
        return pre2;
    }
}

// other solutions
// https://leetcode.com/problems/climbing-stairs/solution/


// phase3 self
class Solution {
    public int climbStairs(int n) {
        int[] res = new int[n + 1];
        if (n <= 2) {return n;}
        res[1] = 1;
        res[2] = 2;
        for (int i = 3 ; i <= n; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }
        return res[n];
    }
}   