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






class Solution {
    // initial thought: first traverse(using BFS or DFS on 1's) to get two sets with positions for the two islands(can use diff labels for two sets in visited matrix directly). Then add all 1's from one set to queue to do BFS, poll out and check if the curpos has neighbor 1 in another set, if yes return the level. If it's 1 in its own set, skip. And if it's 0, add into the queue. This idea has time O(n) space O(n)
    // improvement: no need to label two island, label only one is enough
    public int shortestBridge(int[][] grid) {
        int n = grid.length;
        int[][] visited = new int[n][n];
        int label = 1;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    traverse(grid, i, j, visited, label, queue);// traverse can be either dfs or bfs
                    break;
                } 
            }
            if (!queue.isEmpty()) {break;}
        }
        
        // now one islands is labeled as 1 in the visited matrix, and queue is added with that island's 1 positions. We start bfs
        int level = 0;
        int[][] dirs = new int[][]{{-1, 0},{1, 0},{0, -1},{0,1}};
        while(!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] curpos = queue.poll();
                for (int[] d: dirs) {
                    int newi = curpos[0] + d[0];
                    int newj = curpos[1] + d[1];
                    if (newi < 0 || newi >= n || newj < 0 || newj >= n || visited[newi][newj] == 1) {continue;}// label 2 is for those visited 0s
                    if (grid[newi][newj] == 1) {return level;}
                    queue.add(new int[]{newi, newj});
                    visited[newi][newj] = 1;
                }
            }
            level++;
        }
        return -1;
    }
    
    
    private void traverse(int[][] grid, int i, int j, int[][] visited, int label, Queue<int[]> queue) {
        // can use either dfs or bfs, use bfs here
        int[][] dirs = new int[][]{{-1, 0},{1, 0},{0, -1},{0,1}};
        Queue<int[]> curQueue = new LinkedList<>();
        curQueue.add(new int[]{i, j});
        visited[i][j] = label;
        while(!curQueue.isEmpty()) {
            int[] curpos = curQueue.poll();
            if(label == 1) {queue.add(curpos);}
            for (int[] d: dirs) {
                int newi = curpos[0] + d[0];
                int newj = curpos[1] + d[1];
                if (newi < 0 || newj < 0 || newi >= grid.length || newj >= grid.length || grid[newi][newj] == 0 || visited[newi][newj] = label) {continue;}
                curQueue.add(new int[]{newi, newj});
                visited[newi][newj] = label;
            }
        }
    }
    
}






// Review - similar way as above M1, but use BFS to search instead of dfs
/*Initial thought
Asking for smallest number -> using BFS.
traverse the grid, if encounter 1, search all connected 1 cells. And put into the queue to prepare for BFS.Also label them as visited. Then do BFS, if we encounter unvisited 1 cell. Then that is the cell from the other 1 island, and the current level is the number of 0's need to be flipped.
time O(n^2)
space O(n^2)
*/
class Solution {
    public int shortestBridge(int[][] grid) {
        int n = grid.length;
        int[][] visited = new int[n][n];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    search(grid, i, j, visited, queue);
                    break;
                }
            }
            if (queue.size() > 0) {break;}
        }
        
        // start BFS
        int level = 0;
        int[][] dirs = new int[][]{{0,-1},{0,1},{1,0},{-1,0}};
        while (!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                int[] cur = queue.poll();
                for (int[] d: dirs) {
                    int newi = cur[0] + d[0];
                    int newj = cur[1] + d[1];
                    if(newi < 0 || newi >= n || newj < 0 || newj >= n) {continue;}
                    if (grid[newi][newj] == 1 && visited[newi][newj] != 1) {return level;}
                    if (grid[newi][newj] == 0 && visited[newi][newj] != 1) {
                        queue.add(new int[]{newi,newj});
                        visited[newi][newj] = 1;
                    }                   
                }
            }
            level++;            
        }
        return level; // should not reach this line if there are two islands guaranteed.
    }
    
    
    private void search(int[][] grid, int i, int j, int[][] visited, Queue<int[]> originalQueue) {
        int[][] dirs = new int[][]{{0,-1},{0,1},{1,0},{-1,0}};
        int n = grid.length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = 1;
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            originalQueue.add(cur);
            for (int[] d: dirs) {
                int newi = cur[0] + d[0];
                int newj = cur[1] + d[1];
                if(newi < 0 || newi >= n || newj < 0 || newj >= n) {continue;}
                if (grid[newi][newj] == 1 && visited[newi][newj] != 1) {
                    queue.add(new int[]{newi,newj});
                    visited[newi][newj] = 1;
                }                   
            }
        }
        
    }
}



















































