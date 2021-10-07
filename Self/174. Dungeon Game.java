// Phase3 self
// M1: recursion + memo
// time O(mn) space O(mn)
// here memo[r][c] represents the result we want [Max of {harm of each path (negative sum result)}] from cell [r, c] to cell [m-1, n-1].

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] memo = new int[m][n];
        for (int[] r: memo) {
            Arrays.fill(r, Integer.MAX_VALUE);
        }
        int res = dfs(0, 0, memo, dungeon);
        // if the harm is positive meaning we only need 1 to start, and the rest cell will have the sufficient health by themselves
        return res > 0 ? 1 : -res + 1;
    }
    
    
    private int dfs(int r, int c, int[][] memo, int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        if (r == m - 1 && c == n - 1) {
            return dungeon[r][c];
        }
        if (memo[r][c] != Integer.MAX_VALUE) {return memo[r][c];}
        int downRes = r == m - 1 ? Integer.MIN_VALUE : dfs(r + 1, c, memo, dungeon);
        int rightRes = c == n - 1 ? Integer.MIN_VALUE : dfs(r, c + 1, memo, dungeon);
        downRes = Math.min(0, downRes);
        rightRes = Math.min(0, rightRes);
        memo[r][c] = Math.max(downRes, rightRes) + dungeon[r][c];
        return memo[r][c];
        
    }
}


// can also go with the solution in this post: directly use negative along the way.  ALso the discussion about why backwards instead of forwards from [0,0] tyo [r, c] That's because the result will change once we add one more row or column if go forwards, the result is not known for sure and subject to change in that case.
// https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start



// can use bottom up dp and space optimization