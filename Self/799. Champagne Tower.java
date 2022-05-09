/*Initial thought
Simply use the simulation way, which is O(r^2), and can optimize space to O(r).
[ , , ,4]
[ , ,1.5,1.5]
[ ,0.25, 0.5, 0.25]
dp[i][j] = max(0,dp[i-1][j+1] - 1) / 2 + max(0,dp[i-1][j]) / 2
If we init an array double[query_row + 1],and fill it forwards but with number fill till right end for each row.Then this is the only space we need. Thus space optimized to O(r)
*/
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        int r = query_row;
        double[] res = new double[r + 1];
        res[r] = poured;
        for (int i = 1; i <= r; i++) {
            for (int j  = r - i; j <= r; j++) {
                double prevl = res[j];
                double prevr = j == r ? 0 : res[j+1];
                res[j] = (Math.max(prevl-1, 0) + Math.max(prevr-1, 0)) / 2.0;
                if (i == r && j == query_glass) {return Math.min(res[j],1);}                
            }
        } 
        return Math.min(res[query_glass],1);
    }
    
} 






// Review
/*Thought
Mimic the process, using dp.
dp[i][j] = 0.5 * (dp[i-1][j-1] - 1) + 0.5 * (dp[i-1][j] - 1) for j in [1, i-1]
for j = 0 dp[i][0] = 0.5 * (dp[i-1][0] - 1)
for j = i-1, dp[i][i] = 0.5 * (dp[i-1][i-1] - 1)

init an array with size query_row+1. And for each round, move backwards.
for the required pos, the res = min(1, origin res).

time O(r ^ 2)
space O(r)
*/
class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[] dp = new double[query_row + 1];
        dp[0] = poured;
        for (int r = 1; r <= query_row; r++){
            dp[r] = 0.5 * Math.max(0,dp[r-1]-1);
            for (int c = r-1; c >= 1; c--) {
                dp[c] = 0.5 * Math.max(dp[c-1]-1,0) + 0.5 * Math.max(dp[c]-1,0);
                if (r == query_row && c == query_glass) {return Math.min(1,dp[c]);}
            }
            dp[0] = 0.5 * Math.max(dp[0] - 1,0);
        }
        return Math.min(1,dp[query_glass]); // should not reach this line
        
    }
}