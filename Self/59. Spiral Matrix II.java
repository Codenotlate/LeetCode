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


class Solution {
    /* Initial thought
    Use the iterative way: (x1, y1) for upper left corner and (x2, y2) for bottom right corner. Deal with the 4 borders first then move both corner cells to the square inside
    */
    public int[][] generateMatrix(int n) {
        int x1 = 0;
        int y1 = 0;
        int x2 = n-1;
        int y2 = n-1;
        int[][] res = new int[n][n];
        int num = 1;
        while(x1 <= x2) {
            for (int j = y1; j <= y2; j++) {res[x1][j] = num++;}
            for (int i = x1 +1; i <= x2 -1; i++ ) {res[i][y2] = num++;}
            if (x1 == x2) {break;}
            for (int j = y2; j >= y1; j--) {res[x2][j] = num++;}
            if (y1 == y2) {break;}
            for (int i = x2-1; i >= x1 +1; i--) {res[i][y1] = num++;}
            x1++;
            x2--;
            y1++;
            y2--;
        }
        return res;
    }
}