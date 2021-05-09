// Typical BFS, starting with adding all 1 into queue.
class Solution {
    public int maxDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == 1) {
        			queue.add(new int[]{i,j});
        		}
        	}
        }
        int[][] visited = new int[m][n];
        int level = -1;
        int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0,1}};
        while (!queue.isEmpty()) {
        	int size = queue.size();
        	while (size-- > 0) {
        		int[] curPos = queue.poll();
        		for (int[] d: dir) {
        			int newi = curPos[0] + d[0];
        			int newj = curPos[1] + d[1];
        			if (newi < m && newi >= 0 && newj >= 0 && newj < n && grid[newi][newj] == 0 && visited[newi][newj] == 0) {
        				queue.add(new int[]{newi, newj});
        				visited[newi][newj] = 1;
        			}
        		}
        	}
        	level++;
        }
        return level == 0 ? -1 : level;
    }
}


// two direction dp: first round from upper left, update with upper & left neighbors
// second round from bottom right, update with lower & right neighbors

class Solution {
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        int maxVal = 2 * n + 1;
        int[][] dp = new int[n][n];
        for (int[] r: dp) {
        	Arrays.fill(r, maxVal);
        }
        int res = -1;
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < n; j++) {
        		if (grid[i][j] == 1) {
        			dp[i][j] = 0;
        		} else {
        			if (i > 0) {dp[i][j] = Math.min(dp[i - 1][j] + 1, dp[i][j]);}
        			if (j > 0) {dp[i][j] = Math.min(dp[i][j - 1] + 1, dp[i][j]);}
        		}
        	}
        }

        for (int i = n - 1; i >= 0; i--) {
        	for (int j = n - 1; j >= 0; j--) {
        		if (grid[i][j] == 1) {continue;}
    			if (i < n - 1) {dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j]);}
    			if (j < n - 1) {dp[i][j] = Math.min(dp[i][j + 1] + 1, dp[i][j]);}
    			res = Math.max(res, dp[i][j]);
    		}
        }

        return res >= maxVal ? -1 : res;
    }
}


























