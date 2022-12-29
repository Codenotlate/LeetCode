// Phase 2 M1: slow: repetitve work - BFS for each position, more like a DFS way
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] res = new int[m][n];

        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (matrix[i][j] == 0) {
        			res[i][j] = 0;
        		} else {
        			bfs(matrix, m, n, i, j, res);
        		}
        	}
        }
        return res;
    }

    private void bfs(int[][] matrix, int m, int n, int i, int j, int[][] res) {
    	int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    	Queue<Integer> queue = new LinkedList<>();
    	Set<Integer> visited = new HashSet<>();
    	queue.add(i * n + j);
    	visited.add(i * n + j);
    	int level = 0;
    	while (!queue.isEmpty()) {
    		int size = queue.size();
    		level += 1;
    		while (size-- > 0) {
    			int cur = queue.poll();
    			int r = cur / n;
    			int c = cur % n;
    			for (int[] d: dir) {
    				int newr = r + d[0];
    				int newc = c + d[1];
    				if (newr >= 0 && newr < m && newc >= 0 && newc < n) {
    					if (matrix[newr][newc] == 0) {res[i][j] = level; return;}
    					if (!visited.contains(newr * n + newc)) {
    						queue.add(newr * n + newc);
    						visited.add(newr * n + newc);
    					}
    				}
    			}
    		}
    	}
    }
}



// M2: BFS in total
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] res = new int[m][n];
        for (int[] r: res) {
            Arrays.fill(r, -1);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    res[i][j] = 0;
                    queue.add(new Pair(i, j));
                }
            }
        }
        int level = 0;
        int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int size = queue.size();
            level += 1;
            while (size-- > 0) {
                Pair<Integer, Integer> cur = queue.poll();
                for (int[] d: dir) {
                    int r = cur.getKey() + d[0];
                    int c = cur.getValue() + d[1];
                    if (r >= 0 && r < m && c >= 0 && c < n && res[r][c] == -1) {
                        res[r][c] = level;
                        queue.add(new Pair(r, c));
                    }
                }
            }
        }
        return res;
    }
}

// M3: dp way: two directions
// first loop from left to right and top to bottom, guarantee the left and upper neighbor are the guaranteed optimal
// second loop from right to left and bottom to top, guarantee the right and bottom neighbor are also the optimal
// thus the distance value of each cell is now based on the optimal values of its all 4 neighbors

/*
For those who are asking why DP is done in two passes, in DP we can only use the values which are previously calculated. When we are parsing from top left and coming down to bottom right, we can only use the values of above and left because only those two values are precomputed, if we take right and down, those values are not yet computed, if we work with those values we will get suboptimal answer.
*/


class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] res = new int[m][n];
        // use this value when no neighbor cells
        int init = m * n;
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (matrix[i][j] == 0) {
        			res[i][j] = 0;
        		} else {
        			int left = j > 0 ? res[i][j - 1] : init;
        			int up = i > 0 ? res[i - 1][j] : init;
        			res[i][j] = Math.min(left, up) + 1;
        		}
        	}
        }

        for (int i = m - 1; i >= 0; i--) {
        	for (int j = n - 1; j >= 0; j--) {
        		if (matrix[i][j] != 0) {
        			int right = j < n - 1 ? res[i][j + 1] : init;
        			int bottom = i < m - 1 ? res[i + 1][j] : init;
        			res[i][j] = Math.min(Math.min(right, bottom) + 1, res[i][j]);
        		}
        	}
        }

        return res;
    }
}




// Phase3 self
// similar to above M2
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m;i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {queue.add(new int[]{i, j});}
            }
        }
                
        // start BFS
        int[][] res = new int[m][n];
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                res[cur[0]][cur[1]] = level;
                
                for (int[] dir: dirs) {
                    int newr = cur[0] + dir[0];
                    int newc = cur[1] + dir[1];
                    if (newr >= 0 && newr < m && newc >= 0 && newc < n && mat[newr][newc] == 1 && res[newr][newc] == 0) {
                        queue.add(new int[]{newr, newc});
                        res[newr][newc] = -1;
                    }
                }
                
            }
            level++;
        }
        return res;
    }
}


class Solution {
    // DP way
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] res = new int[m][n];
        int max = m + n - 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    int up = max;
                    int left = max;
                    if (i >= 1) {up = Math.min(up, res[i-1][j]);}
                    if (j >= 1) {left = Math.min(left, res[i][j-1]);}
                    res[i][j] = Math.min(left, up) + 1;
                }
            }
        }
        
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (mat[i][j] == 1) {
                    int down = max;
                    int right = max;
                    if (i < m-1) {down = Math.min(down, res[i+1][j]);}
                    if (j < n-1) {right = Math.min(right, res[i][j+1]);}
                    res[i][j] = Math.min(res[i][j],Math.min(down, right) + 1);
                }
            }
        }
        
        return res;
        
    }
}



// Review
// BFS way starting with all 0 cells. And we can use the result matrix as the visited matrix, all O cells have val = 0 and all 1 cells have init val = -1.
// time O(mn) space O(mn)
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] res = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.add(new int[]{i,j});
                } else {
                    res[i][j] = -1;
                }
            }
        }
        int level = 0;
        int[][] dirs = new int[][]{{-1, 0},{1, 0},{0, -1},{0,1}};
        while(!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                for (int[] d: dirs) {
                    int newi = cur[0] + d[0];
                    int newj = cur[1] + d[1];
                    if (newi >= 0 && newi < m && newj >= 0 && newj < n && mat[newi][newj] == 1 && res[newi][newj] == -1) {
                        res[newi][newj] = level + 1;
                        queue.add(new int[]{newi, newj});
                    }
                }
            }
            level++;
        }
        return res;
    }
}


// DP way
// for each cell the dist is determined by its four neighbors, for mat[c] == 1: res[c] = min(res[x]) + 1 where x is the 4 neighbors of c. That's why we can do two pass, with the first one starting from top left, and the second one starting from bottom right.
// time O(mn) space O(1)
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] res = new int[m][n];
        // first pass
        for (int i = 0; i < m ; i ++) {
            for (int j = 0; j < n; j++) {
                if(mat[i][j] == 0) {continue;}
                int upper = i - 1 >= 0 ? res[i-1][j] : m * n;
                int left =  j - 1 >= 0 ? res[i][j-1] : m * n;
                res[i][j] = Math.min(upper, left) + 1;               
            }
        }
        // second pass
        for (int i = m-1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (mat[i][j] == 0) {continue;}
                int lower = i + 1 < m ? res[i + 1][j] : m * n;
                int right = j + 1 < n ? res[i][j+1] : m * n;
                res[i][j] = Math.min(res[i][j], 1 + Math.min(right, lower));
            }
        }
        return res;
    }
}




// Review 22/12/29
// M1: BFS way space O(mn)
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] res = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        // put all 0 cells in
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {queue.add(new int[]{i,j});}
            }
        }
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int curi = cur[0];
                int curj = cur[1];
                if (mat[curi][curj] == 1 && res[curi][curj] <= 0) {
                    res[curi][curj] = level;
                }
                // check neighbors
                for (int[] d: dirs) {
                    int newi = curi + d[0];
                    int newj = curj + d[1];
                    if (newi >= 0 && newi < m && newj >= 0 && newj < n && mat[newi][newj] == 1 && res[newi][newj] == 0) {
                        queue.add(new int[]{newi, newj});
                        res[newi][newj] = -1;
                    }
                }
            }
            level++;
        }
        return res;
    }
}


// M2: two DP way space O(1)
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] res = new int[m][n];

        // first traverse from upperleft to bottomright
        int infmax = m * n;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {continue;} // don't forget this line
                int topres = i == 0 ? infmax : res[i-1][j];
                int leftres = j == 0? infmax : res[i][j-1];
                res[i][j] = Math.min(leftres, topres) + 1;
            }
        }
        // second traverse from bottomright to upperleft
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (mat[i][j] == 0) {continue;}
                int bottomres = i == m-1? infmax : res[i+1][j];
                int rightres = j == n-1? infmax : res[i][j+1];
                res[i][j] = Math.min(res[i][j], Math.min(rightres, bottomres) + 1);
            }
        }
        return res;
    }
}











