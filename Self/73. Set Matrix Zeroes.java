class Solution {
    public void setZeroes(int[][] matrix) {
        // space O(m + n) way is:use m+n space to store rows and columns will be zeros along the m * n scan, then turn those rows and cols into zeros in the second scan.
        // Optimized way: label those rows and cols first cell as 0, and revisit first row to change all cols to zeros, and revisit first col to change all rows to zeros.
        // time O(m * n) space O(1)
        int m = matrix.length;
        int n = matrix[0].length;
        // use first row and first col to track
        // also since m[0][0] is used by both row and col, we can't tell who is the cause, thus we set m[0][0] to be the label for first row, and use firstCol = 0 to label for first col;
        int firstCol = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    if (j == 0) {firstCol = 0;} 
                    else {matrix[0][j] = 0;}             
                }
            }
        }
        // first row scan(start with 1 to avoid changing the label in first col)
        for (int j = 1; j < n; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        // first column scan(start with 1)
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        // check m[0][0] to change first row and  firstCol == 0 for first col if necessary
        if(matrix[0][0] == 0) {
            for (int j = 1; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstCol == 0) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}