/*Thought
DFS. at each move, will need to check its row or its col to find the next stop cell.  we will label visited cells.
time O(mn) since each cell will be visited at most once.  space O(mn) for the visited matrix.
*/
class Solution {
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        int m = maze.length;
        int n = maze[0].length;
        int[][] visited = new int[m][n];
        return dfs(maze, visited, start[0], start[1], destination);
    }
    
    private boolean dfs(int[][] maze, int[][] visited, int r, int c, int[] dest) {
        if (r == dest[0] && c == dest[1]) {return true;}
        visited[r][c] = 1;
        // 4 dirs
        // left
        int left = c-1;
        while (left >= 0 && maze[r][left] == 0) {left--;}
        left++;
        if(maze[r][left] != 1 && visited[r][left] != 1 && dfs(maze, visited, r, left, dest)) {return true;}
        // right
        int right = c + 1;
        while (right < maze[0].length && maze[r][right] == 0) {right++;}
        right--;
        if (maze[r][right] != 1 && visited[r][right] != 1 && dfs(maze, visited, r, right, dest)) {return true;}
        // up
        int up = r - 1;
        while (up >= 0 && maze[up][c] == 0) {up--;}
        up++;
        if (maze[up][c] != 1 && visited[up][c] != 1 && dfs(maze, visited, up, c, dest)) {return true;}
        // down
        int down = r + 1;
        while(down < maze.length && maze[down][c] == 0) {down++;}
        down--;
        if (maze[down][c] != 1 && visited[down][c] != 1 && dfs(maze, visited, down, c, dest)) {return true;}               return false;
    }
}


// can also do BFS next time (code more elegant)

