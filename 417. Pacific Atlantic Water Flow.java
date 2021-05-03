class Solution {
	// method1: similar as 130, using DFS with borders
	// diff with 130, here borders are in two types
	// use two boolean to label whether can reach Pacific or Atlantic
	// can also be used as visited matrix
	// Time O(m * n) space O(m * n)
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new LinkedList<>();
        if (matrix == null || matrix.length == 0) {
        	return res;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] toP = new boolean[m][n];
        boolean[][] toA = new boolean[m][n];

        for (int i = 0; i < m; i++) {
        	dfs(matrix, i, 0, toP);
        	dfs(matrix, i, n - 1, toA);
        }
        for (int j = 0; j < n; j++) {
        	dfs(matrix, 0, j,  toP);
        	dfs(matrix, m - 1, j, toA);
        }

        for (int i = 0; i < m; i++) {
	        for (int j = 0; j < n; j++) {
	            if (toP[i][j] && toA[i][j]) {
	                res.add(Arrays.asList(i, j));
	            }
	        }
	    }

	    return res;
    }

    private void dfs(int[][] matrix, int r, int c, boolean[][] flag) {
    	if (flag[r][c]) {
    		return;
    	}
    	flag[r][c] = true;
    	int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; 
    	for (int[] d: directions) {
    		int nextr = r + d[0];
    		int nextc = c + d[1];
    		if (nextr >= 0 && nextr < matrix.length && nextc >= 0 
    			&& nextc < matrix[0].length && matrix[r][c] <= matrix[nextr][nextc]) {
    			dfs(matrix, nextr, nextc, flag);
    		}
    	}
    }
}




class Solution {
	// method2: BFS. similar structure as above method, using 2 queue for BFS
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new LinkedList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {return res;}
        int m = matrix.length;
        int n = matrix[0].length;
        Queue<int[]> qP = new LinkedList<>();
        Queue<int[]> qA = new LinkedList<>();
        boolean[][] toP = new boolean[m][n];
        boolean[][] toA = new boolean[m][n];

        // bfs with 4 borders
        for (int i = 0; i < m; i++) {
        	bfs(matrix, i, 0, qP, toP);
        	bfs(matrix, i, n - 1, qA, toA);
        }
        for (int j = 0; j < n; j++) {
        	bfs(matrix, 0, j, qP, toP);
        	bfs(matrix, m - 1, j, qA, toA);
        }

        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
	        	if (toP[i][j] && toA[i][j]) {
	        		res.add(Arrays.asList(i, j));
	        	}
	        }
        }
        return res;
    }


    private void bfs(int[][] matrix, int r, int c, Queue<int[]> q, boolean[][] visited) {

    	if (visited[r][c]) {return;}
    	q.add(new int[]{r, c});
    	visited[r][c] = true;
    	int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; 


    	while (!q.isEmpty()) {
    		int[] cur = q.poll();
    		for (int[] d: directions) {
    			int nextr = cur[0] + d[0];
    			int nextc = cur[1] + d[1];
    			if (nextr >= 0 && nextr < matrix.length && nextc >= 0 
	    			&& nextc < matrix[0].length && matrix[cur[0]][cur[1]] <= matrix[nextr][nextc]
	    			&& !visited[nextr][nextc]) {
	    			q.add(new int[]{nextr, nextc});
	    			visited[nextr][nextc] = true;
	    		}

    		}
    	}
    }
}































