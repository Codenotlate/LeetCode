class Solution {
    // time O(mn) space O(mn)
    public int minTotalDistance(int[][] grid) {
        // phase 3 self
        // using math: basically find the cell has min in X-dim and Y-dim. 
        // loop twice to get all Xs and Ys for cell = 1 in sorted order
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> cols = new LinkedList<>();
        List<Integer> rows = new LinkedList<>();
        // first loop to get rows ascending
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rows.add(i);
                }
            }
        }
        // second loop to get cols ascending
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (grid[i][j] == 1) {
                    cols.add(j);
                }
                
            }
        }
        
        // two pointers move towards center to get x dist and y dist
        int start = 0;
        int end = cols.size() - 1;
        int dist_col = 0, dist_row = 0;
        while (start < end) {
            dist_col += cols.get(end) - cols.get(start);
            start++;
            end--;
        }
        start = 0;
        end = rows.size() - 1;
        while (start < end) {
            dist_row += rows.get(end) - rows.get(start);
            start++;
            end--;
        }
        return dist_col + dist_row;
    }
}