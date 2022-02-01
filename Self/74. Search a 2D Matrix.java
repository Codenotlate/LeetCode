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


class Solution {
    /* initial thought
    Since the matrix is sorted, we can use last column of each row to first find the correct row(first larger than or equal to), and then search that row to see if we have that number. Use BS for both seaches, so the time is O(logm + logn), space O(1)
    
    */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int start = 0;
        int end = m-1;
        while (start < end) {
            int mid = start + (end - start) /2;
            if(matrix[mid][n-1] < target) {
                start = mid + 1;
            } else if (matrix[mid][n-1] > target) {
                end = mid;
            } else {return true;}
        }
        if(matrix[start][n-1] < target) {return false;}
        int row = start;
        start = 0;
        end = n-1;
        while(start <= end) {
            int mid = start + (end - start) /2;
            if(matrix[row][mid] < target) {
                start = mid + 1;
            } else if (matrix[row][mid] > target) {
                end = mid-1;
            } else {return true;}
        }
        return false;
        
    }

}

// https://leetcode.com/problems/search-a-2d-matrix/solution/
// This can also be done using single BS. We can view the 2d array as one sorted 1d array given it's already sorted.