class Solution {
    // phase3 solution
    // M1: normal BFS for each 1 cell. need to track sum dist from 1 cells and # of 1cells visited this zero cell. Also need space for visited cells, need to clean it up for each BFS.
    // time: O(m^2 * n^2) space O(mn)
    int[][] dirs = {{-1,0},{1,0},{0, -1},{0, 1}};
    
    public int shortestDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        // used to track # and dist for each 0 cell
        int[][] nums = new int[m][n]; 
        int[][] dist = new int[m][n];
        int countOnes = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    countOnes++;
                    bfs(grid, i, j, visited, nums, dist);
                    // clear up visited for next BFS
                    for (int k = 0; k < m; k++) {
                        Arrays.fill(visited[k], 0);
                    }
                }
            }
        }
        
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (nums[i][j] == countOnes) {
                    minDist = Math.min(minDist, dist[i][j]);
                }
            }
        }
        
        return minDist == Integer.MAX_VALUE? -1 : minDist;
        
    }
    
    
    
    private void bfs(int[][] grid, int i, int j, int[][] visited, int[][] nums, int[][] dist) {
        int m = grid.length;
        int n = grid[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = 1;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int r = cur[0];
                int c = cur[1];
                if (grid[r][c] == 0) {
                    nums[r][c]++;
                    dist[r][c] += level;
                }
                
                for (int[] dir : dirs) {
                    int newr = r + dir[0];
                    int newc = c + dir[1];
                    if (newr >= 0 && newr < m && newc>=0 && newc <n && visited[newr][newc] != 1 && grid[newr][newc] == 0) {
                        visited[newr][newc] =1;
                        queue.add(new int[]{newr, newc});
                    }
                }
                
            }
            level++;            
        }        
    }
}






class Solution {
    // phase3 solution
    // M2: optimized based on M1. Instead of a separate visited matrix, we can label those visited as (-# of 1s visited), and next BFS will avoid visiting those are not visited by some 1s(i.e. not labeled as -# of bfs done).
    // time: O(m^2 * n^2) space O(mn)
    int[][] dirs = new int[][]{{-1,0},{1,0},{0, -1},{0, 1}};
    
    public int shortestDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // used to track dist for each 0 cell 
        int[][] dist = new int[m][n];
        int countOnes = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    countOnes++;
                    bfs(grid, i, j, dist, countOnes);
                    
                }
            }
        }
        
        int minDist = Integer.MAX_VALUE;
    
        for (int i=0; i < m; i++) { 
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == -countOnes) {
                    minDist = Math.min(minDist, dist[i][j]);
                }
            }
        }
        
        return minDist == Integer.MAX_VALUE? -1 : minDist;
        
    }
    
    
    
    private void bfs(int[][] grid, int i, int j, int[][] dist, int visitedLabel) {
        int m = grid.length;
        int n = grid[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int r = cur[0];
                int c = cur[1];
                if (grid[r][c] == -visitedLabel) {
                    dist[r][c] += level;
                }
                
                for (int[] dir : dirs) {
                    int newr = r + dir[0];
                    int newc = c + dir[1];
                    if (newr >= 0 && newr < m && newc>=0 && newc <n && grid[newr][newc] == -visitedLabel+1) {
                        grid[newr][newc] = -visitedLabel;
                        queue.add(new int[]{newr, newc});
                    }
                }
                
            }
            level++;            
        }        
    }
}
























































