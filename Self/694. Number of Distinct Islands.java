/* Initial thought
The idea is to identify whether two island is the same. One way can think of , is to always view the most up left cell as (0,0) and label all other cells with relative position to this cell. The same islands should have same set of cell position pairs.

improvement from solution: can simplify the unique island representation as a string of the visited directions in orders. Then can simply compare the string.

note in both ways, since we visited each island using same method, the relative visited order should be the same.
time O(mn)
space O(mn)

*/
// M1: set of pair way
class Solution {
    public int numDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        Set<Set<Pair<Integer, Integer>>> pathSets = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] != 1) {
                    search(grid, i, j, visited, pathSets);
                }
            }
        }
        return pathSets.size();
    }
    
    // BFS
    private void search(int[][] grid, int i, int j, int[][] visited, Set<Set<Pair<Integer, Integer>>> pathSets) {
        Set<Pair<Integer, Integer>> pathSet = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i,j});
        visited[i][j] = 1;
        
        int[][] dirs = new int[][]{{0,-1},{0,1},{1,0},{-1,0}};
        
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            pathSet.add(new Pair(cur[0]-i, cur[1]-j));
            for (int[] d: dirs) {
                int newi = cur[0] + d[0];
                int newj = cur[1]+d[1];
                if (newi >= 0 && newi < grid.length && newj >= 0 && newj < grid[0].length && visited[newi][newj] == 0 && grid[newi][newj] == 1) {
                    queue.add(new int[]{newi, newj});
                    visited[newi][newj] = 1;
                }
            }
        }
        pathSets.add(pathSet);
    }
}


// M2: set of strings (much slower than M1, but time should also be O(mn))
class Solution {
    public int numDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        Set<String> pathSets = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] != 1) {
                    pathSets.add(search(grid, i, j, visited,'O'));
                }
            }
        }
        return pathSets.size();
    }
    
    // DFS
    private String search(int[][] grid, int i, int j, int[][] visited, char label) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] == 1 || grid[i][j] != 1) {return "";}
        StringBuilder path = new StringBuilder();
        path.append(label);
        visited[i][j] = 1;
        
        int[][] dirs = new int[][]{{0,-1},{0,1},{1,0},{-1,0}};
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            
            path.append(search(grid, newi, newj, visited, getLabel(d)));
            path.append('O');             
        }
        
        return path.toString();
    }
    
    private char getLabel(int[] d) {
        if (d[0] < 0) {return 'U';}
        else if (d[0] > 0) {return 'D';}
        else if (d[1] < 0) {return 'L';}
        else {return 'R';}
    }
}
















