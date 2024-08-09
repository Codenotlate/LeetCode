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



// self review
class Solution {
    /*initial thought
    obvious way is use a O(mn) extra space to label those cells need to become 0. And change the original matrix based on thie memo.
    slightly improvement is to use O(m+n) extra space, one for label which rows need to become 0 and another one for columns.
    best way is to use O(1) space, cases need to pay attention to: one cell is originally 0, and also label by another 0 cell as 0. Because matrix[i][j] in [integer.min, integer.max], thus can't use negative value to label inplace.
    Use similar idea as O(m+n) way. instead of using extra O(m+n) space, we can directly use the first column and first row of the original matrix. But before that, we need to note down whether first row/col has zero. So we can adjust them later when they are done with being the label memo for all other cells in the matrix. 
    */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstColZero = false;
        boolean firstRowZero = false;
        for (int c: matrix[0]) {
            if (c== 0) {firstRowZero = true;}
        }
        for (int r = 0; r < m; r++) {
            if (matrix[r][0] == 0) {firstColZero = true;}
        }
        
        // label
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }
        
        // convert rest cells to zero
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // convert first col and first cell based on firstRowZero and firstColZero
        if (firstRowZero) {Arrays.fill(matrix[0], 0);}
        if (firstColZero) {
            for (int r = 0; r < m; r++) {matrix[r][0] = 0;}
        }
    }
}








// Review - same as above M2, clearer than above M1
class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int firstrow = 1;
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {firstrow = 0; break;}
        }
        int firstcol = 1;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {firstcol = 0; break;}
        }
        
        // then use first row and first col for labeling of remaining cells
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        //changing remaining cells to zero according to label
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // changing first row and first col based on firstrow and firstcol
        if (firstrow == 0) {Arrays.fill(matrix[0],0);}
        if (firstcol == 0) {
            for (int i = 0; i < m; i++) {matrix[i][0]=0;}
        }
    }
}




// 20240808
// use first row and first col to label the rest
class Solution {
    public void setZeroes(int[][] matrix) {
        boolean isrow1zero = false;
        boolean iscol1zero = false;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0]==0) {iscol1zero=true;}
        }
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j]==0) {isrow1zero=true;}
        }
        for (int i=1; i < matrix.length; i++) {
            for (int j=1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0]==0) {Arrays.fill(matrix[i],0);}
        }
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j]==0) {
                for (int i =1; i<matrix.length;i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (isrow1zero) {Arrays.fill(matrix[0],0);}
        if (iscol1zero) {
            for (int i=0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}