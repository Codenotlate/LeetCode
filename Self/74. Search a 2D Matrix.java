class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // Phase3 self: time O(logn + logm) = O(log(mn)) space O(1)
        // use the last column to binary search find the first one larger than target
        // Then BS in that row
        int m = matrix.length;
        int n = matrix[0].length;
        int start = 0;
        int end = m - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (matrix[mid][n - 1] == target) {return true;}
            else if (matrix[mid][n - 1] < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (matrix[start][n-1] < target) {return false;}
        // BS the row
        int row = start;
        start = 0;
        end = n - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (matrix[row][mid] == target) {return true;}
            else if (matrix[row][mid] < target) {start = mid + 1;}
            else {end = mid - 1;}
        }
        return false;
    }
}