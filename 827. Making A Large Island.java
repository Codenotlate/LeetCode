// M1: dfs -> time O(n^2)
class Solution {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int[][] visited = new int[n][n];
        int maxSize = 0;
        int index = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == 1 && visited[i][j] == 0) {
        			int size = dfs(grid, i, j, visited, index);
        			map.put(index, size);
        			index++;
        			maxSize = Math.max(maxSize, size);
        		}
        	}
        }

        // loop visited to get the maxSize after change at most one 0 to 1
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < n; j++) {
        		if (visited[i][j] == 0) {
        			maxSize = Math.max(maxSize, 1 + neighborSize(i, j, visited, map));
        		}
        	}
        }

        return maxSize;
    }


    private int dfs(int[][] grid, int i, int j, int[][] visited, int index) {
    	if (i < 0 || j < 0 || i >= grid.length || j >= grid.length || visited[i][j] != 0 || grid[i][j] != 1) {
    		return 0;
    	}

    	int size = 1;
    	visited[i][j] = index;

    	int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    	for (int[] d: dir) {
    		size += dfs(grid, i + d[0], j + d[1], visited, index);
    	}
    	return size;
    }


    private int neighborSize(int i, int j, int[][] visited, Map<Integer, Integer> map) {
    	int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    	int size = 0;
    	Set<Integer> neighborIndex = new HashSet<>();
    	for (int[] d: dir) {
    		int newi = i + d[0];
    		int newj = j + d[1];
    		if (newi >= 0 && newi < visited.length && newj >= 0 && newj < visited.length && visited[newi][newj] != 0) {
    			neighborIndex.add(visited[newi][newj]);
    		}
    	}

    	for (int idx: neighborIndex) {
    		size += map.get(idx);
    	}
    	return size;
    }
}














