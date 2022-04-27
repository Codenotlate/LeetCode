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




// Review from solution, similar to above M2, but use visited matrix instead of inplace change the original grid matrix
class Solution {
    /* From solution
    Do BFS for each 1 cell. Record the accumulated distance sum for each 0 cell after all BFS rounds. Label visited with round number to avoid visiting 0 cells that are unreachable in earlier rounds of BFS from other 1 cells.
    find the smallest distance among 0 cells that are reachable by all 1 cells.
    time O(m*n * m*n)
    space O(m*n)    
    */
    public int shortestDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        int[][] distance = new int[m][n];
        int roundNum = 1;
        
        for (int i = 0; i < m; i++) {
            for(int j = 0; j <n; j++) {
                if (grid[i][j] == 1) {
                    bfs(grid, i, j, visited, distance, roundNum);
                    roundNum++;
                }
            }
        }
        
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for(int j = 0; j <n; j++) {
                if (visited[i][j] == -roundNum + 1) {
                    minDist = Math.min(minDist, distance[i][j]);
                }
            }
        }
        return minDist == Integer.MAX_VALUE? -1 : minDist;
    }
    
    
    private void bfs(int[][] grid, int i, int j, int[][] visited, int[][] distance, int roundNum) {
        Queue<int[]> queue = new LinkedList<>();
        int[][] dirs = new int[][]{{0, -1},{0,1},{1,0},{-1,0}};
        queue.add(new int[]{i,j});
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int curi = cur[0];
                int curj = cur[1];
                if (grid[curi][curj] == 0) {distance[curi][curj] += level;}
                for (int[] d: dirs) {
                    int newi = curi + d[0];
                    int newj = curj + d[1];
                    if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && grid[newi][newj] == 0 && visited[newi][newj] == -roundNum+1) {
                        queue.add(new int[]{newi, newj});
                        visited[newi][newj] = -roundNum;
                    }
                }
            }
            level++;
        }
    }
}







// Review
/*
BFS starting at cell 1. For each cell 0, adding up the distance from all 1 cells. And count the number of 1 cell visiting this 0 cell. If the count doesn't match the # of BFS done, meaning this 0 cell is unreachable for one building, then we will skip it in this round as well. Return the min dist among all 0 cells having count == # of 1 cells.
time O(#of1 * #of0) = O(mn * mn)
space O(mn)


*/
class Solution {
    public int shortestDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][] visited = new int[m][n][2];
        
        int bfsCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    bfs(grid, i, j, visited,bfsCount);
                    bfsCount++;
                }
            }
        }
        
        int mindist = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && visited[i][j][1]==bfsCount) {
                    mindist = Math.min(mindist, visited[i][j][0]);
                }
            }
        }
        return mindist == Integer.MAX_VALUE? -1: mindist;
    }
    
    
    private void bfs(int[][] grid, int i, int j, int[][][] visited, int bfsCount) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int[][] dirs = new int[][]{{-1,0},{0,1},{0,-1},{1,0}};
        queue.add(new int[]{i,j});
        
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                for (int[] d: dirs) {
                    int newi = cur[0] + d[0];
                    int newj = cur[1] + d[1];
                    
                    if (newi >= 0 && newi < m && newj >= 0 && newj < n && grid[newi][newj] == 0 && visited[newi][newj][1] == bfsCount) {
                        queue.add(new int[]{newi, newj});
                        visited[newi][newj][0] += level + 1;
                        visited[newi][newj][1]++;
                    }
                }
            }
            level++;
        }
    }
}








































































