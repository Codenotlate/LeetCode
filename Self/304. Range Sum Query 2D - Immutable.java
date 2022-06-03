/*Thought
M0: naive way, simply sum up each cell in the range. time O(mn)
M1: store row as accumlated sum on each row. For each row in the range, rowsum = cumsum[c2]-cumsum[c1-1], then add each rowsum together. time O(m)
M2: can use (0,0) as the common leftupper corner, then each cell store the sum for range with (0,0) as leftupper and (i,j) as rightlower corners, use S(r,c) to represent. Then when initialize, directly store S(r,c) = S(r-1,c)+S(r,c-1)-S(r-1,c-1) + mat[r][c]
Then when call sumRegion, sum = S(r2,c2)-S(r2,c1-1)-S(r1-1, c2)+S(r1-1, c1-1) which is O(1) time.
*/
class NumMatrix {
    int[][] sum;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        sum = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j== 0) {sum[i][j] = matrix[i][j];}
                else if (i== 0) {sum[i][j] = sum[i][j-1] + matrix[i][j];}
                else if (j == 0) {sum[i][j] = sum[i-1][j] + matrix[i][j];}
                else {sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + matrix[i][j];}
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // be careful with edge case
        int total = sum[row2][col2];
        int left = col1 >= 1? sum[row2][col1-1] : 0;
        int up = row1 >= 1? sum[row1-1][col2] : 0;
        int overlap = col1 >= 1 && row1 >= 1? sum[row1-1][col1-1] : 0;
        return total -left - up + overlap;
    }
}
