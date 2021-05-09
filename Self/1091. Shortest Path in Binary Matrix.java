class Solution {
	// method1: BFS
	// Time O(n^2), space O(n^2) for visited matrix
	// key of BFS: queue, visited, size = q.size() & size-- > 0 to separate each level
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] neighborDir = new int[][]{{0,1},{0,-1},{1,0},{-1,0},{1,-1},{-1,1},{-1,-1},{1,1}};

        int N = grid.length;
        if (grid[0][0] == 1 || grid[N - 1][N - 1] == 1) {
        	return -1;
        }

        // declare queue and visited matrix
        Queue<int[]> queue = new LinkedList<>();
        int[][] visited = new int[N][N];
        queue.add(new int[]{0,0});
        visited[0][0] = 1;

        int resLen = 0;

        while (!queue.isEmpty()) {
        	int size = queue.size(); // use to control level
        	while (size > 0) {
        		int[] cur = queue.poll();
        		// if already find, return 
        		if (cur[0] == N - 1 && cur[1] == N - 1) {
        			return resLen + 1;
        		}
        		// otherwise adding cur's valid neighbors into queue
        		for (int[] d: neighborDir) {
        			int nextX = cur[0] + d[0];
        			int nextY = cur[1] + d[1];

        			// only add valid neighbors into the queue
        			if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N && visited[nextX][nextY] == 0 && grid[nextX][nextY] == 0) {
        				queue.add(new int[]{nextX, nextY});
        				visited[nextX][nextY] = 1;
        			}
        		}
        		size--;
        	}
        	// to next level
        	resLen++;
        }

        return -1;
    }
}




class Solution {
	// method2: similar BFS
	// Time O(n^2), space O(1) 
	// The space is optimized by using input grid as visited matrix as well
	// label visited as 1, since no matter it's visited or it's blocked, we are not going to add the node into queue.
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] neighborDir = new int[][]{{0,1},{0,-1},{1,0},{-1,0},{1,-1},{-1,1},{-1,-1},{1,1}};

        int N = grid.length;
        if (grid[0][0] == 1 || grid[N - 1][N - 1] == 1) {
        	return -1;
        }

        // declare queue and visited matrix
        Queue<int[]> queue = new LinkedList<>();
        //nt[][] visited = new int[N][N];
        queue.add(new int[]{0,0});
        //visited[0][0] = 1;
        grid[0][0] = 1;

        int resLen = 0;

        while (!queue.isEmpty()) {
        	int size = queue.size(); // use to control level
        	while (size > 0) {
        		int[] cur = queue.poll();
        		// if already find, return 
        		if (cur[0] == N - 1 && cur[1] == N - 1) {
        			return resLen + 1;
        		}
        		// otherwise adding cur's valid neighbors into queue
        		for (int[] d: neighborDir) {
        			int nextX = cur[0] + d[0];
        			int nextY = cur[1] + d[1];

        			// only add valid neighbors into the queue
        			if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N && grid[nextX][nextY] == 0) {
        				queue.add(new int[]{nextX, nextY});
        				grid[nextX][nextY] = 1; // use grid to label visited as well
        			}
        		}
        		size--;
        	}
        	// to next level
        	resLen++;
        }

        return -1;
    }
}


// can also use A* algo to optimize
// https://leetcode.com/problems/shortest-path-in-binary-matrix/discuss/313347/A*-search-in-Python

