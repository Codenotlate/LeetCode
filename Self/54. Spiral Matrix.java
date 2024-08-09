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









// Reviews
/*Thought
M1: solve recursively. Each level only take care of the 4 edges. Base case is when m == 0 or n == 0, directly return.  At current level, add mat[r1][c1] till mat[r1][c2], then mat[r1+1][c2] till mat[r2-1][c2]. If (r1 != r2), add mat[r2][c2] till mat[r2][c1]. If(c1 != c2), add mat[r2-1][c1] till mat[r1+1][c2]. next level has (r1+1, c1+1, r2-1, c2-1).
time O(m*n) space O(min(m,n)) for the number of recursive calls
M2: solve iteratively. similar idea using a while loop. time O(mn) space O(1)
*/
// M1 recursive way
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>();
        getList(matrix, 0, 0, matrix.length-1, matrix[0].length-1, res);
        return res;
    }
    
    private void getList(int[][] mat, int r1, int c1, int r2, int c2, List<Integer> res) {
        if (r1 > r2 || c1 > c2) {return;}
        for(int j = c1; j <= c2; j++) {res.add(mat[r1][j]);}
        for(int i = r1+1; i <= r2-1; i++) {res.add(mat[i][c2]);}
        if (r1 != r2) {
            for(int j = c2; j >= c1; j--) {res.add(mat[r2][j]);}
        }
        if(c1 != c2) {
            for (int i = r2-1; i >= r1 +1; i--) {res.add(mat[i][c1]);}
        }
        getList(mat, r1+1, c1+1, r2-1, c2-1, res);
    }
}
// M2: iterative way
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>();
        int r1 = 0; int c1 = 0;
        int r2 = matrix.length - 1; int c2 = matrix[0].length - 1;
        while(r1 <= r2 && c1 <= c2) {
            for(int j = c1; j <= c2; j++) {res.add(matrix[r1][j]);}
            for(int i = r1+1; i <= r2-1; i++) {res.add(matrix[i][c2]);}
            if (r1 != r2) {
                for(int j = c2; j >= c1; j--) {res.add(matrix[r2][j]);}
            }
            if(c1 != c2) {
                for (int i = r2-1; i >= r1 +1; i--) {res.add(matrix[i][c1]);}
            }
            r1 += 1;
            c1 += 1;
            r2 -= 1;
            c2 -= 1;
        }
        return res;
    }
}



// 2024/08/08
// can solve either recursively or iteratively using a loop
// This ending condition is better than above ones.
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int up = 0;
        int left = 0;
        int down = matrix.length-1;
        int right = matrix[0].length-1;
        List<Integer> res = new ArrayList<>();
        int totalCount = matrix.length * matrix[0].length;
        while(res.size() < totalCount) {
            for (int k = left; k <= right && res.size() < totalCount; k++) {res.add(matrix[up][k]);}
            for (int k = up+1; k <= down-1 && res.size() < totalCount; k++) {res.add(matrix[k][right]);}
            for (int k = right; k >= left && res.size() < totalCount; k--) {res.add(matrix[down][k]);}
            for (int k = down-1; k >= up+1 && res.size() < totalCount; k--) {res.add(matrix[k][left]);}
            left++; up++; down--; right--;
        }
        return res;
    }
}

