class Solution {
    public void wallsAndGates(int[][] rooms) {
        // phase3 solution
        // Do bfs reversely, for each 0 cell, bfs to inf cells. And do all BFS at the same time by adding all 0 cells into the queue at the begining at once. This guarantees inf cells are updated with the shorted dist.
        // time O(mn) space O(mn)
        Queue<int[]> queue = new LinkedList<>();
        int m = rooms.length;
        int n = rooms[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.add(new int[]{i, j});
                }
            }
        }
        
        // now all 0s are added into queue, can start BFS
        int[][] dirs = {{-1, 0},{1,0},{0, -1}, {0,1}};
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int r = cur[0]; int c = cur[1];
                if (rooms[r][c] == Integer.MAX_VALUE) {
                    rooms[r][c] = level;
                }
                for (int[] dir: dirs) {
                    int newr = r + dir[0];
                    int newc = c + dir[1];
                    if (newr >= 0 && newr < m && newc >= 0 && newc < n && rooms[newr][newc] == Integer.MAX_VALUE) {
                        queue.add(new int[]{newr, newc});
                    }
                }
                
            }
            level++;
        }
    }
}

// The bfs part can be change to not use level: Update the dist when the cell is added to the queue, and rooms[newr][newc] = rooms[r][c] + 1
class Solution {
    public void wallsAndGates(int[][] rooms) {
        // phase3 solution
        // Do bfs reversely, for each 0 cell, bfs to inf cells. And do all BFS at the same time by adding all 0 cells into the queue at the begining at once. This guarantees inf cells are updated with the shorted dist.
        // time O(mn) space O(mn)
        Queue<int[]> queue = new LinkedList<>();
        int m = rooms.length;
        int n = rooms[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.add(new int[]{i, j});
                }
            }
        }
        
        // now all 0s are added into queue, can start BFS
        int[][] dirs = {{-1, 0},{1,0},{0, -1}, {0,1}};
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0]; int c = cur[1];

            for (int[] dir: dirs) {
                int newr = r + dir[0];
                int newc = c + dir[1];
                if (newr >= 0 && newr < m && newc >= 0 && newc < n && rooms[newr][newc] == Integer.MAX_VALUE) {
                    rooms[newr][newc] = rooms[r][c] + 1;
                    queue.add(new int[]{newr, newc});
                }
            }
                
           
        }
    }
}


// Review self : similar idea as above M2. update the distance when the cell is added to the queue. This way we can avoid same cell being added by same prev level neighbors multiple times (comparing to M1)
class Solution {
    /*initial thought
    multi source BFS. put all gates into the queue at first, then start the BFS, label each visited cell with level number as shorted path. Thus unvisited until queue is empty are the ones impossible to reach from a gate. So their value will stay INF.
    time O(mn) space O(mn)
    */
    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        // first pass to add all gates into the queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {queue.add(new int[]{i, j});}
            }
        }
        
        // start the BFS
        int[][] dirs = new int[][]{{-1, 0},{1,0},{0,1},{0,-1}};
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                for (int[] d: dirs) {
                    int newi = cur[0] + d[0];
                    int newj = cur[1] + d[1];
                    if (newi >= 0 && newi < m && newj >= 0 && newj < n && rooms[newi][newj] == Integer.MAX_VALUE) {
                        queue.add(new int[]{newi, newj});
                        rooms[newi][newj] = level + 1;
                    }
                }                
            }
            level++;
        }
    }
}