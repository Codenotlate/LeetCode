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