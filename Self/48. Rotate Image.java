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