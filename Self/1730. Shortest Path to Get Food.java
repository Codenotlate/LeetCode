/*Thought
shortest path -> BFS
Not good to modify the grid, use an extra O(mn) space for visited.
time O(m*n) space O(m * n)
*/
class Solution {
    public int getFood(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '*') {
                    return bfs(grid, visited, i, j);
                }
            }
        }
        return -1; // should not reach this line
    }
    
    private int bfs(char[][] grid, int[][] visited, int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i,j});
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                if (grid[cur[0]][cur[1]] == '#') {return level;}
                for (int[] d: dirs) {
                    int newi = cur[0]+d[0];
                    int newj = cur[1]+d[1];
                    if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && (grid[newi][newj] == 'O'|| grid[newi][newj] == '#') && visited[newi][newj] != 1) {
                        queue.add(new int[]{newi, newj});
                        visited[newi][newj] = 1;
                    }
                }
            }
            level++;
        }
        return -1;
    }
}