class Solution {
	// method1: DFS with backtracking recursively

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        if (word == null || word.length() == 0) {return true;}
        if (m * n < word.length()) {return false;}
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (dfs(board, i, j, word, visited, 0)) {
        			return true;
        		}
        	}
        }
        return false;
    }


    private boolean dfs(char[][] board, int r, int c, String word, int[][] visited, int curLen) {
    	// base case
    	if (curLen == word.length()) {return true;}
    	if (r < 0 || c < 0 || r >= board.length || c >= board[0].length || word.charAt(curLen) != board[r][c] || visited[r][c] == 1) {
    		return false;
    	}
    	visited[r][c] = 1;

    	int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    	for (int[] d: directions) {
    		int nextr = r + d[0];
    		int nextc = c + d[1];
    		if (dfs(board, nextr, nextc, word, visited, curLen + 1)) {
    			return true;
    		}
    	}

    	visited[r][c] = 0;
    	return false;
    }
}




//phase2 self
class Solution {
    public boolean exist(char[][] board, String word) {
        if (board.length == 0 || board[0].length == 0) {return false;}
        if (board.length * board[0].length < word.length()) {return false;}
        int[][] visited = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, 0, i, j, visited)) {return true;}
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int wordPos, int i, int j, int[][] visited) {
        if (wordPos >= word.length()) {return true;}
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] == 1) {return false;}
        if (board[i][j] == word.charAt(wordPos)) {
            visited[i][j] = 1;
            int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] d: dir) {
                if (dfs(board, word, wordPos + 1, i + d[0], j + d[1], visited)) {
                    return true;
                }
            }
            visited[i][j] = 0;
        }
        return false;
    }
}