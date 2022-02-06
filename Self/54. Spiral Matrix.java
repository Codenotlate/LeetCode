class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>();
        int x1 = 0, y1 = 0; 
        int x2 = matrix.length - 1, y2 = matrix[0].length - 1;
        while (x1 <= x2 && y1 <= y2) {
            for (int j = y1; j <= y2; j++) {res.add(matrix[x1][j]);}
            for (int i = x1 + 1; i <= x2 - 1; i++) {res.add(matrix[i][y2]);}
            // don't forget avoiding dup
            if (x1 == x2) {break;}
            for (int j = y2; j >= y1; j--) {res.add(matrix[x2][j]);}
            if (y1 == y2) {break;}
            for (int i = x2 - 1; i>= x1 + 1; i--) {res.add(matrix[i][y1]);}
            x1 += 1;
            x2 -= 1;
            y1 += 1;
            y2 -= 1;
        }
        return res;
    }
}


// Phase3 self
// above iterative way has better space time complexity, but recursive way is easier to understand. Main idea is the same
class Solution {
    /* initial thought:
    Use recursion. recur(matrix, lefti, leftj, rowlen, collen, resList). Each layer only need to take care of the numbers in 4 borders
    time O(m*n)  space O(max(m, n))
    */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> resList = new LinkedList<>();
        spiralHelper(matrix, 0, 0, matrix.length, matrix[0].length, resList);
        return resList;
    }
    
    private void spiralHelper(int[][] matrix, int i, int j, int m, int n, List<Integer> resList) {
        // base case
        if (m == 0 || n == 0) {return;}
        if (m == 1 || n == 1) {
            if (m == 1) {
                for (int k = 0; k < n; k++) {resList.add(matrix[i][j+k]);}
            } else {
                for (int k = 0; k < m; k++) {resList.add(matrix[i+k][j]);}
            }
            return;
        }
        // deal with 4 borders
        for (int k = 0; k < n; k++) {resList.add(matrix[i][j+k]);}
        for (int k = 1; k < m-1; k++) {resList.add(matrix[i+k][j+n-1]);}
        for (int k = n-1; k>= 0; k--) {resList.add(matrix[i+m-1][j+k]);}
        for (int k = m-2; k>=1; k--) {resList.add(matrix[i+k][j]);}
        spiralHelper(matrix, i+1, j+1, m-2, n-2, resList);
    }
}