class Solution {
    public int shortestBridge(int[][] A) {
        Queue<int[]> queue = new LinkedList<>();
        int m = A.length;
        int n = A[0].length;
        int[][] visited = new int[m][n];
        int[][] dir = new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        
        boolean found = false;
        // use dfs to find one island and put all island pos into queue and labeled as visited
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] == 1) {
                    dfs(A, m, n, i, j, queue, visited, dir);
                    found = true;
                    break;
                }
            }
            if (found) {break;}
        }
        
        // use BFS and level 0 nodes in the queue to find shortest path to next unvisited 1
        int level = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                for (int[] d: dir) {
                    int r = cur[0] + d[0];
                    int c = cur[1] + d[1];
                    if (r >= 0 && r < m && c >= 0 && c < n && visited[r][c] != 1) {
                        if (A[r][c] == 1) {return level;}
                        visited[r][c] = 1;
                        queue.add(new int[]{r, c});
                    }
                }
            }
            level++;            
        }
        return -1;
    }
    
    
    private void dfs(int[][] A, int m, int n, int i, int j, Queue<int[]> queue, int[][] visited, int[][] dir) {
        if (i < 0 || i >= m || j < 0 || j >= n || A[i][j] != 1 || visited[i][j] == 1) {return;}
        visited[i][j] = 1;
        queue.add(new int[] {i, j});
        for (int[] d: dir) {
            dfs(A, m, n, i + d[0], j + d[1], queue, visited, dir);
        }
    }
}











