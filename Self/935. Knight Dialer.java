/*Initial thought
first notice that left side 147 is symmetric to right side 369, so we only need to care one side as starting point then twice the number.
dp[1][n] = dp[8][n-1] + dp[6][n-1]
dp[5][...] = 1
convert 0 to 11.

from solution, we noticed the neighbors for each position is actually limited and can be listed out. Thus we can simply list them out. Then do DP.

time O(n)
naive way: space O(n), improved way space O(1)
*/
// naive way O(n) space
class Solution {
    public int knightDialer(int n) {
        int[][] neighbors = new int[][]{{4,6},{6,8},{7,9},{4,8},{0,3,9},{},{0,1,7},{2,6},{1,3},{2,4}};
        int[][] dp = new int[n][10];
        Arrays.fill(dp[0],1);
        int res = 0;
        for (int i = 1; i < n; i++) {
            for (int num = 0; num <= 9; num++) {
                for (int next: neighbors[num]) {
                    dp[i][num] =  (dp[i][num] + dp[i-1][next]) % 1000000007;
                }
                if(i == n-1) {res = (res + dp[i][num]) % 1000000007;}                
            }
        }
        return res == 0? 10 : res;
        
    }
}
// O(1) way
class Solution {
    public int knightDialer(int n) {
        int[][] neighbors = new int[][]{{4,6},{6,8},{7,9},{4,8},{0,3,9},{},{0,1,7},{2,6},{1,3},{2,4}};
        int[] dp = new int[10];
        Arrays.fill(dp,1);
        int res = 0;
        for (int i = 1; i < n; i++) {
            int[] next = new int[10];
            for (int num = 0; num <= 9; num++) {               
                for (int nxt: neighbors[num]) {
                    next[num] =  (next[num] + dp[nxt]) % 1000000007;
                }
                if(i == n-1) {res = (res + next[num]) % 1000000007;}                
            }
            dp = next;
        }
        return res == 0? 10 : res;
        
    }
}

/*Discussion worth mention
https://leetcode.com/problems/knight-dialer/discuss/189287/O(n)-time-O(1)-space-DP-solution-%2B-Google-interview-question-writeup
this post includes an interviewer's walk through, what to be expected in the interview
https://medium.com/hackernoon/google-interview-questions-deconstructed-the-knights-dialer-f780d516f029

O(logN) way. should be beyond interview req
https://leetcode.com/problems/knight-dialer/discuss/189252/O(logN)

*/



















