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





















