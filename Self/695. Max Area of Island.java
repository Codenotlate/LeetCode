class Solution {
	// method1: dfs iterative way using stack and visited matrix
	// time O(R * C) space O(R * C)
    public int maxAreaOfIsland(int[][] grid) {
        int maxRes = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        Stack<int[]> stack = new Stack<>();
        int[][] directions = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == 1 && visited[i][j] != 1) {
        			stack.push(new int[]{i, j});
        			visited[i][j] = 1;
        			int curRes = 0;
        			while (!stack.isEmpty()) {
        				int[] cur = stack.pop();
        				curRes++;
        				for (int[] d: directions) {
        					int nextX = cur[0] + d[0];
        					int nextY = cur[1] + d[1];
        					if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && grid[nextX][nextY] == 1 && visited[nextX][nextY] != 1) {
        						stack.push(new int[]{nextX, nextY});
        						visited[nextX][nextY] = 1;
        					}
        				}
        			}
        			maxRes = Math.max(maxRes, curRes);
        		}
        	}
        }
        return maxRes;
    }
}



// M2: implement DFS is recursive way
// also try modify input grid to avoid using visited
// seems like modify grid can be a bit quicker than using visited
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int maxRes = 0;
        int m = grid.length;
        int n = grid[0].length;
        //int[][] visited = new int[m][n];

        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == 1) {
	        		maxRes = Math.max(maxRes, maxAreaHelper(grid, i, j));
        		}
        	}
        }
        return maxRes;
 
    }


    private int maxAreaHelper(int[][] grid, int r, int c) {
    	if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == 0) {
    		return 0;
    	}
        int[][] directions = new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}};
    	grid[r][c] = 0;
    	int res = 1;
    	for (int[] d: directions) {
    		res += maxAreaHelper(grid, r + d[0], c + d[1]);
    	}
    	return res;
    }
}








// M3: can also use union-find
// https://leetcode.com/problems/max-area-of-island/discuss/186891/Java.-BFSDFSUnion-Find.




// M4: Phase2 self: BFS way
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        int maxArea = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j] == 0 && grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, bfs(grid, i, j, visited));
                }
            }
        }
        return maxArea;
    }
    
    private int bfs(int[][] grid, int i, int j, int[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        int[][] dir = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        visited[i][j] = 1;
        queue.add(new int[]{i, j});
        int area = 0;
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            area++;
            for (int[] d: dir) {
                int newi = cur[0] + d[0];
                int newj = cur[1] + d[1];
                if (newi < 0 || newi >= grid.length || newj < 0 || newj >= grid[0].length || grid[newi][newj] == 0 || visited[newi][newj] == 1) {
                    continue;
                }
                queue.add(new int[] {newi, newj});
                visited[newi][newj] = 1;
            }
        }
        return area;
        
    }
}


// phase 2 self unionFind
class Solution {
    private class UnionFind {
        private int maxSize;
        private int[] parent, size;
        
        public UnionFind(int n) {
            maxSize = -1;
            parent = new int[n];
            size = new int[n];
            
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        
        public int find(int p) {
            while (parent[p] != p) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        
        public void union(int p, int q) {
            int rootp = find(p);
            int rootq = find(q);
            if (rootp == rootq) {return;}
            // this should be put ahead of size[rootp] or size[rootq] are updated
            maxSize = Math.max(maxSize, size[rootp] + size[rootq]);
            if (size[rootp] > size[rootq]) {
                parent[rootq] = rootp;
                size[rootp] += size[rootq];
            } else {
                parent[rootp] = rootq;
                size[rootq] += size[rootp];
            }           
        }
        
        public int maxSize() {
            return maxSize;
        }
    }
    
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        UnionFind uf = new UnionFind(m * n);
        // res is used for no union happened
        int res = 0;
        int[][] dir = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // if no union happened at all, then res = 1 if we have grid[i][j] == 1, otherwise 0 for all zeros
                    res = 1;
                    for (int[] d: dir) {
                        int ni = i + d[0];
                        int nj = j + d[1];
                        if (ni >= 0 && ni < m && nj >= 0 && nj < n && grid[ni][nj] == 1) {
                            uf.union(n * i + j, n * ni + nj);
                        }
                    }
                }
            }
        }
        if (uf.maxSize() == -1) {return res;}
        return uf.maxSize();
    }
}



// Phase3 self
// DFS recursive way: similar as above M2
class Solution {
    // loop each cell in the grid, if it's 1 and not visited, start a dfs/bfs on this cell, return the areasize. update the maxAreasize along the way
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j<n; j++) {
                if(grid[i][j] == 1 && visited[i][j] == 0) {
                    res = Math.max(res, exploreDFS(grid, i, j, visited));
                }
            }
        }
        return res;
    }
    
    
    private int exploreDFS(int[][] grid, int i, int j, int[][] visited) {
        if (visited[i][j] == 1) {return 0;}
        visited[i][j] = 1;
        int[][] dirs = new int[][]{{-1, 0},{1,0},{0,-1},{0,1}};
        int res = 1;
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && grid[newi][newj] == 1 && visited[newi][newj] == 0) {
                res += exploreDFS(grid, newi, newj, visited);
            }
        }
        return res;
    }
}



// BFS way: similar as above M4
class Solution {
    // loop each cell in the grid, if it's 1 and not visited, start a dfs/bfs on this cell, return the areasize. update the maxAreasize along the way
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j<n; j++) {
                if(grid[i][j] == 1 && visited[i][j] == 0) {
                    res = Math.max(res, exploreBFS(grid, i, j, visited));
                }
            }
        }
        return res;
    }
    
    
    private int exploreBFS(int[][] grid, int i, int j, int[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = 1;
        int[][] dirs = new int[][]{{-1, 0},{1,0},{0,-1},{0,1}};
        
        int res = 0;
        while (!queue.isEmpty()) {
            int[] curpos = queue.poll();
            res++;
            for (int[] d: dirs) {
                int newi = curpos[0] + d[0];
                int newj = curpos[1] + d[1];
                if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && grid[newi][newj] == 1 && visited[newi][newj] == 0) {
                    queue.add(new int[]{newi, newj});
                    visited[newi][newj] = 1;
                }
            }
            
        }
        return res;
    }
}


// Union find way, similar to above M3
class Solution {
    // try UF way. replace rank as the size of the group. return the one node with largest size in the end
    // for simplicity, convert [i]j] to i*n + j
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] parent = new int[m*n];        
        int[] size = new int[m*n];
        for (int i = 0; i < m*n;i++) {
            parent[i] = i;
            size[i] = grid[i / n][i%n]==1? 1: 0;
        }
        int[][] dirs = new int[][]{{-1, 0},{1,0},{0,-1},{0,1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j<n; j++) {
                if(grid[i][j] == 0) {continue;}
                for (int[] d: dirs) {
                    int newi = i + d[0];
                    int newj = j + d[1];
                    if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && grid[newi][newj] == 1) {
                        union(parent, size, i*n + j, newi*n + newj);
                    }
                }
              
            }
        }
        int res = 0;
        for (int s: size) {res = Math.max(res,s);}
        return res;
    }
    
    private void union(int[] parent, int[] size, int p1, int p2) {
        int p1Parent = find(parent, p1);
        int p2Parent = find(parent, p2);
        if (p1Parent == p2Parent) {return;}
        if (size[p1Parent] > size[p2Parent]) {
            parent[p2Parent] = p1Parent;
            size[p1Parent] += size[p2Parent];
        } else {
            parent[p1Parent] = p2Parent;
            size[p2Parent] += size[p1Parent];
        }
    }
    
    private int find(int[] parent, int p) {
        while (parent[p] != p) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }
    
    
    
}









