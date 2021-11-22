// similar to 695

class Solution {
	// m1: dfs recursive way
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        int res = 0;

        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == '1' && visited[i][j] != 1) {
        			islandHelper(grid, visited, i, j);
        			res++;
        		}
        	}
        }
        return res;
    }


    private void islandHelper(char[][] grid, int[][] visited, int r, int c) {
    	if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0' || visited[r][c] == 1) {
    		return;
    	} 
    	visited[r][c] = 1;
    	int[][] directions = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};
    	for (int[] d: directions) {
    		islandHelper(grid, visited, r + d[0], c + d[1]);
    	}
    }	
}



class Solution {
	// method1: dfs iterative way using stack and visited matrix
	// time O(R * C) space O(R * C)
    public int numIslands(char[][] grid) {
        int maxRes = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        Stack<int[]> stack = new Stack<>();
        int[][] directions = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == '1' && visited[i][j] != 1) {
        			stack.push(new int[]{i, j});
        			visited[i][j] = 1;
        			while (!stack.isEmpty()) {
        				int[] cur = stack.pop();
        				for (int[] d: directions) {
        					int nextX = cur[0] + d[0];
        					int nextY = cur[1] + d[1];
        					if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && grid[nextX][nextY] == '1' && visited[nextX][nextY] != 1) {
        						stack.push(new int[]{nextX, nextY});
        						visited[nextX][nextY] = 1;
        					}
        				}
        			}
        			maxRes++;
        		}
        	}
        }
        return maxRes;
    }
}




// Phase 2 self BFS way
class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(visited[i][j] != 1 && grid[i][j] == '1') {
                    bfs(grid, i, j, visited);
                    res++;
                }               
            }
        }
        return res;
    }
    
    private void bfs(char[][] grid, int i, int j, int[][] visited) {
        int[][] dir = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] d: dir) {
                int newi = cur[0] + d[0];
                int newj = cur[1] + d[1];
                if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && visited[newi][newj] != 1 && grid[newi][newj] != '0') {
                    queue.add(new int[]{newi, newj});
                    visited[newi][newj] = 1;
                }
            }
            
        }
        
    }
    
        
}



// Phase3 self
// time o(mn) space O(mn)
// bfs and dfs can be used interchangably here
class Solution {
    public int numIslands(char[][] grid) {
        int count = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && visited[i][j] != 1) {
                    count++;
                    //dfs(grid, i, j, visited); 
                    // can also change to bfs(grid, i, j, visited)
                    bfs(grid, i, j, visited);
                }
            }
        }
        return count;
    }
    
    private int[][] dirs = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};
    
    // private void dfs(char[][] grid, int i, int j, int[][] visited) {
    //     int m = grid.length;
    //     int n = grid[0].length;
    //     if (i < 0 || i >= m|| j < 0 || j >= n || visited[i][j] == 1 || grid[i][j] != '1') {return;}
    //     visited[i][j] = 1;
    //     for (int[] dir: dirs) {
    //         int newi = i + dir[0];
    //         int newj = j + dir[1];
    //         dfs(grid, newi, newj, visited);
    //     }
    // }
    
    private void bfs(char[][] grid, int i, int j, int[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            for (int[] dir: dirs) {
                int newr = r + dir[0];
                int newc = c + dir[1];
                if (newr >= 0 && newr < grid.length && newc >= 0 && newc < grid[0].length && visited[newr][newc] != 1 && grid[newr][newc] == '1') {
                    visited[newr][newc] = 1;
                    queue.add(new int[]{newr, newc});
                }
            }
        }
    }
}

// phase3: improvement from solution
// no need to use visited[][], can simply label those visited 1s as 0s in the grid itself.
// This way, space for DFS is still O(mn), but for BFS the space is the max size of the queue, which is 


class Solution {
    public int numIslands(char[][] grid) {
        int count = 0;
        int m = grid.length;
        int n = grid[0].length;
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    //dfs(grid, i, j); 
                    // can also change to bfs(grid, i, j, visited)
                    bfs(grid, i, j);
                }
            }
        }
        return count;
    }
    
    private int[][] dirs = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};
    
    // private void dfs(char[][] grid, int i, int j) {
    //     int m = grid.length;
    //     int n = grid[0].length;
    //     if (i < 0 || i >= m|| j < 0 || j >= n || grid[i][j] != '1') {return;}
    //     grid[i][j] = '0';
    //     for (int[] dir: dirs) {
    //         int newi = i + dir[0];
    //         int newj = j + dir[1];
    //         dfs(grid, newi, newj);
    //     }
    // }
    
    private void bfs(char[][] grid, int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        grid[i][j] = '0';
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            for (int[] dir: dirs) {
                int newr = r + dir[0];
                int newc = c + dir[1];
                if (newr >= 0 && newr < grid.length && newc >= 0 && newc < grid[0].length && grid[newr][newc] == '1') {
                    grid[newr][newc] = '0';
                    queue.add(new int[]{newr, newc});
                }
            }
        }
    }
}


// can also do unionFind, omit here for now

















