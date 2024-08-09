class Solution {
    public void rotate(int[][] matrix) {
        // phase 3 self
        // do two steps: step1: reverse top and bottom. step2: reverse on diagonal
        // time O(n^2) space O(1)
        reverseUpDown(matrix);
        reverseDiag(matrix);
    }
    
    private void reverseUpDown(int[][] m) {
        int n = m.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = m[i][j];
                m[i][j] = m[n-1-i][j];
                m[n-1-i][j] = temp;
            }
        }
    }
    
    private void reverseDiag(int[][] m) {
        int n = m.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = temp;
            }
        }
    }
}

// another way: rotate 4 cells each time

// Phase3 self
class Solution {
    /* initial thought
    It's basically reverse rows first, the reverse via diagonal.   
    */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                swap(matrix, i, j, n-1-i,j);
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                swap(matrix, i, j, j,i);
            }
        }
    }
    
    private void swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        int temp = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = temp;
    }
}



// 20240808 - diff from above ways
// a 4 position rotation: (i,j)-(j, n-i)-(n-i, n-j)-(n-j,i)
class Solution {
    public void rotate(int[][] matrix) {
        int i1 = 0;
        int i2 = matrix.length-1;
        while(i1 < i2) {
            for (int k = i1; k < i2; k++) {
                rotate4(matrix, i1, k);
            }
            i1++;
            i2--;
        }
    }

    private void rotate4(int[][] mat, int a, int b) {
        int n = mat.length-1;
        int temp = mat[n-b][a];
        mat[n-b][a] = mat[n-a][n-b];
        mat[n-a][n-b]=mat[b][n-a];
        mat[b][n-a]=mat[a][b];
        mat[a][b] = temp;
    }
}