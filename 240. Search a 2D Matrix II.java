// divide and conquar way, similar to BST idea
// time O(r * c * log3)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {return false;}
        return helper(matrix, target, 0, matrix[0].length - 1, 0, matrix.length - 1);
    }



    // rs re represents the start and end index of each row(left to right), 
    // cs ce represents for each column (top to bottom)
	private boolean helper(int[][] m, int target, int rs, int re, int cs, int ce) {
		// base case
		if (rs > re || cs > ce) {return false;}
		if (rs == re && cs == ce) {return m[cs][rs] == target;}

		int rm = (re - rs) / 2 + rs;
		int cm = (ce - cs) / 2 + cs;
		if (target == m[cm][rm]) {return true;}
		else if (target < m[cm][rm]) {
			return helper(m, target, rs, re, cs, cm - 1) || helper(m, target, rs, rm - 1, cm, ce);
		} else {
			return helper(m, target, rm + 1, re, cs, ce) || helper(m, target, rs, rm, cm + 1, ce);
		}
	}
}



// another way, BST
// Time O(r + c)
// If we stand on the top-right corner of the matrix and look diagonally, it's kind of like a BST, 
// we can go through this matrix to find the target like how we traverse the BST.
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {return false;}
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
        	if (matrix[row][col] == target) {return true;}
        	else if (matrix[row][col] < target) {row++;}
        	else col--;
        }
        return false;
    }
}

































