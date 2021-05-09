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