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



// Phase3 self: similar to above method, using BFS, and put map size record in the BFS
class Solution {
    // 1st loop: BFS/DFS, label each 1 group and record the group size in a map. Below BFS, can be replaced by dfs like above
    // 2nd loop: for each 0, check its 1 neighbors, add the size from diff group
    // return max + 1;
    // time O(n^2) space O(n^2)
    // pay attention to special case when there's no zero or no one, another to deal with it is like above method: init maxSize as the max of all 1 group size. This way deals with all 1 case.
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int[][] group = new int[n][n];
        Map<Integer, Integer> sizeMap = new HashMap<>();
        
        int groupNum = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && group[i][j] == 0) { // group also used as visited
                    bfs(grid, i, j, group, groupNum++, sizeMap);
                }
            }
        }
        
        
        int[][] dirs = new int[][]{{-1, 0},{1,0}, {0,-1}, {0,1}};
        int maxSize = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {continue;}
                Set<Integer> visitedGroup = new HashSet<>();
                int curSize = 0;
                for (int[] d: dirs) {
                    int newi = i + d[0];
                    int newj = j + d[1];
                    if (newi >= 0 && newi < n && newj >= 0 && newj < n && grid[newi][newj] == 1 && !visitedGroup.contains(group[newi][newj])) {
                        curSize += sizeMap.get(group[newi][newj]);
                        visitedGroup.add(group[newi][newj]);
                    }
                }
                maxSize = Math.max(curSize, maxSize);
            }
        }
        
        // consider all 1's or all 0's cases
        return (maxSize == 0 && !sizeMap.isEmpty())? n*n : maxSize + 1;
    }
    
    
    
    private void bfs(int[][] grid, int i, int j, int[][] group, int groupNum, Map<Integer, Integer> map) {
        Queue<int[]> queue = new LinkedList<>();
        int n = grid.length;
        queue.add(new int[]{i, j});
        group[i][j] = groupNum;
        int[][] dirs = new int[][]{{-1, 0},{1,0}, {0,-1}, {0,1}};
        int size = 0;
        
        while (!queue.isEmpty()) {
            int[] curpos = queue.poll();
            size++;
            for (int[] d: dirs) {
                int newi = curpos[0] + d[0];
                int newj = curpos[1] + d[1];
                if (newi >= 0 && newi < n && newj >= 0 && newj < n && grid[newi][newj] == 1 && group[newi][newj] == 0) {
                    queue.add(new int[]{newi, newj});
                    group[newi][newj] = groupNum;
                }
            }
            
        }
        map.put(groupNum, size);
    }
}



// may also consider UnionFind way








































