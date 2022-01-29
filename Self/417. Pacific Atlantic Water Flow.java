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




// Phase3 self
class Solution {
    /* initial thought
    two BFS/DFS starts with 4 edges, one label for Pacific and another label for anlantic, add to res if one pos is labeled both
    // time o(m*n) space O(m*n)    
    */    
    // try 2 BFS way first
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> res = new LinkedList<>();
        int m = heights.length;
        int n = heights[0].length;
        int[][] visited = new int[m][n]; // 0 for unvisited, 1 for p, 2 for a
        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i<m; i++) {
            queue.add(new int[]{i,0});
            visited[i][0] = 1;
        }
        for (int j = 1; j < n; j++) {
            queue.add(new int[]{0, j});
            visited[0][j] = 1;
        }
        
        int[][] dirs = new int[][]{{0, -1}, {0,1},{1,0},{-1,0}};
        while(!queue.isEmpty()) {
            int[] curpos = queue.poll();
            for (int[] d: dirs) {
                int newi = curpos[0] + d[0];
                int newj = curpos[1] + d[1];
                if (newi < 0 || newj < 0 || newi >=m || newj >= n || visited[newi][newj] == 1 || heights[newi][newj] < heights[curpos[0]][curpos[1]]) { continue;}
                queue.add(new int[]{newi, newj});
                visited[newi][newj] = 1;
            }
        }
        
        // another BFS for Altantic cells
        for (int i = 0; i<m; i++) {
            queue.add(new int[]{i,n-1});
            if(visited[i][n-1] != 0) {res.add(Arrays.asList(i, n-1));}
            visited[i][n-1] = 2;
        }
        for (int j = 0; j < n-1; j++) {
            queue.add(new int[]{m-1, j});
            if(visited[m-1][j] != 0) {res.add(Arrays.asList(m-1, j));}
            visited[m-1][j] = 2;
        }
        
        while (!queue.isEmpty()) {
            int[] curpos = queue.poll();
            for (int[] d: dirs) {
                int newi = curpos[0] + d[0];
                int newj = curpos[1] + d[1];
                if (newi < 0 || newj < 0 || newi >=m || newj >= n || visited[newi][newj] == 2 || heights[newi][newj] < heights[curpos[0]][curpos[1]]) { continue;}
                queue.add(new int[]{newi, newj});
                if(visited[newi][newj] == 1) {res.add(Arrays.asList(newi, newj));}
                visited[newi][newj] = 2;
            }
        }
        
        return res;
    }
}

class Solution {  
    // try dfs way 
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> res = new LinkedList<>();
        int m = heights.length;
        int n = heights[0].length;
        int[][] visited = new int[m][n]; // 0 for unvisited, 1 for p, 2 for a
        for (int i = 0; i<m; i++) {
            dfs(heights, i, 0, 1, visited, res);
        }
        for (int j = 1; j < n; j++) {
            dfs(heights, 0, j, 1, visited, res);
        }
        for (int i = 0; i<m; i++) {
            dfs(heights, i, n-1, 2, visited, res);
        }
        for (int j = 0; j < n-1; j++) {
            dfs(heights, m-1, j, 2, visited, res);
        }
        
        return res;
    }
    
    
    private void dfs(int[][] heights, int i, int j, int label, int[][] visited, List<List<Integer>> res) {
        if (visited[i][j] == label) {return;} 
        if (visited[i][j] != 0) {res.add(Arrays.asList(i, j));}
        visited[i][j] = label;
        int m = heights.length;
        int n = heights[0].length;
        int[][] dirs = new int[][]{{0, -1}, {0,1},{1,0},{-1,0}};
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi < 0 || newj < 0 || newi >=m || newj >= n || heights[newi][newj] < heights[i][j]) { continue;}
            dfs(heights, newi, newj, label, visited, res);
            
        }
    }
        
        
}











// https://stackoverflow.com/questions/7488098/storing-arrays-in-set-and-avoiding-duplicates
// don't use array as the key for hashset or hashmap
















