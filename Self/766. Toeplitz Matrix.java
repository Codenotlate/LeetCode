class Solution {
	// time O(m * n) space O(1)
    public boolean isToeplitzMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
        	for (int j = 0; j < matrix[0].length - 1; j++) {
        		if (matrix[i][j] != matrix[i + 1][j + 1]) {return false;}
        	}
        }
        return true;
    }
}

// for follow ups
//https://leetcode.com/problems/toeplitz-matrix/discuss/271388/Java-Solution-for-Follow-Up-1-and-2
