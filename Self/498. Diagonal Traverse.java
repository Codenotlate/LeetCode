class Solution {
    /* Initial thought:
    find rules in index.
    (0,0)  -- sum = 0, i starts with 0
    (0,1) (1,0) -- sum = 1, i starts with 0
    (2,0) (1,1) (0,2) -- sum = 2, i starts with 2
    (1,2) (2,1) -- sum = 3, i starts with 0 (j > len), starts with 1
    (2,2) -- sum = 4, i starts with 3 (i < 3), starts with 2
    
    for sum = 0 to m + n - 2. when sum is odd, i starts. from the smallest i with valid j. i.e. sum - i  < n => i starts at max(0,sum - n + 1), ends at min(m-1, sum).
    when sum is even, i starts with the largets i with valid j. i.e. i < m && i < sum => i starts at min(m, sum) - 1, ends at  sum - n + 1
    time O(m * n) since each element is processed once.
    space O(1)
    */
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] res = new int[m * n];
        int respos = 0;
        for (int sum = 0; sum <= m + n -2; sum++) {
            if (sum % 2 == 0) {
                for (int i = Math.min(sum, m-1); i >= Math.max(0, sum - n + 1); i--) {
                    res[respos++] = mat[i][sum - i];
                }
            } else {
                for (int i = Math.max(0, sum - n+1); i <= Math.min(m-1, sum); i++) {
                    res[respos++] = mat[i][sum - i];
                }
            }
        }
        return res;
    }
}

// Another similar idea way but use extra space(in java can use an arraylist). But implementation is easier.
// https://leetcode.com/problems/diagonal-traverse/discuss/581868/Easy-Python-NO-DIRECTION-CHECKING






// Review
/*Thought
Try to find the pattern.
(0,0) sum = 0
(0,1) (1,0) sum = 1
(2,0) (1,1) (0,2) sum = 2(n-1)
(1,2) (2,1) sum = 3
(2,2) sum = 4
even sum diag x starts from min(sum,n)
odd sum diag x starts from max(0, sum - n)
Naive way is to leave the judgement on valid r,c for each r, c pair.
Improved way is to constrain the smallest range manually in advance
time O(m*n) space O(1)
*/
// naive way - slow
class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] res = new int[m*n];
        int resIdx = 0;
        for (int sum = 0; sum <= m + n - 2; sum++) {
            if(sum % 2 == 0) {
                for (int r = sum; r >= 0; r--) {
                    if (isValid(m,n,r,sum-r)) {res[resIdx++] = mat[r][sum-r];}
                }
            } else {
                for (int r = 0; r <= sum; r++) {
                    if (isValid(m,n,r,sum-r)) {res[resIdx++] = mat[r][sum-r];}
                }
            }           
        }
        return res;
    }
    
    private boolean isValid(int m, int n, int r, int c) {
        return 0 <= r && r <= m-1 && c >= 0 && c <= n-1;
    }
}
// improved way: range converted from above isValid condition on r and c.
class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] res = new int[m*n];
        int resIdx = 0;
        for (int sum = 0; sum <= m + n - 2; sum++) {
            if(sum % 2 == 0) {
                for (int r = Math.min(sum, m-1); r >=Math.max(0, sum - n + 1); r--) {
                    res[resIdx++] = mat[r][sum-r];
                }
            } else {
                for (int r = Math.max(0, sum - n + 1); r <= Math.min(sum, m-1); r++) {
                    res[resIdx++] = mat[r][sum-r];
                }
            }           
        }
        return res;
    }    
}