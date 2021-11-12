// same idea as 54: visit matrix in spiral order then add in the num

class Solution {
    public int[][] generateMatrix(int n) {
        int[][] mat = new int[n][n];
        int x1 = 0, y1 = 0; 
        int x2 = n - 1, y2 = n - 1;
        int num = 1;
        while (num <= n * n) {
            for (int j = y1; j <= y2; j++) {mat[x1][j] = num; num++;}
            for (int i = x1 + 1; i <= x2 - 1; i++) {mat[i][y2] = num; num++;}
            // don't forget avoiding dup
            if (x1 == x2) {break;}
            for (int j = y2; j >= y1; j--) {mat[x2][j] = num; num++;}
            if (y1 == y2) {break;}
            for (int i = x2 - 1; i>= x1 + 1; i--) {mat[i][y1] = num; num++;}
            x1 += 1;
            x2 -= 1;
            y1 += 1;
            y2 -= 1;
        }
        return mat;
    }
}

// can simplify the x1, x2, y1, y2 with one pair x, y