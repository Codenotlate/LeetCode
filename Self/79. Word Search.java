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




// Phase3 self time O(m*n * 3^L)
class Solution {
    public int[][] dirs = new int[][]{{-1,0},{1,0},{0,-1},{0, 1}};
    
    public boolean exist(char[][] board, String word) {
        // Phase3 self
        int m = board.length;
        int n = board[0].length;
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i , j, visited, 0)) {return true;}
            }
        }
        return false;
    }
    
    
    private boolean dfs(char[][] board, String word, int i, int j, int[][] visited, int w) {
        int m = board.length;
        int n = board[0].length;
        // base case
        if (w == word.length() || i < 0 || i >= m || j < 0 || j >= n || visited[i][j] == 1) {
            return w == word.length();
        }
        
        if (board[i][j] == word.charAt(w)) {
            visited[i][j] = 1;
            for (int[] dir : dirs) {
                if (dfs(board, word, i + dir[0], j + dir[1], visited, w + 1)) {
                    visited[i][j] = 0;
                    return true;
                }
            }
            visited[i][j] = 0;
        }
        return false;
    }   
}



// Review self
// time should be O(m * N * 3^L)
class Solution {
    // pure dfs
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, i, j, word, 0, new int[m][n])) {return true;}
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, int i, int j, String word, int idx, int[][] visited) {
        if (idx == word.length() - 1) {return board[i][j] == word.charAt(idx);}
        if (board[i][j] != word.charAt(idx)) {return false;}
        visited[i][j] = 1;
        int[][] dirs = new int[][]{{-1, 0},{1, 0},{0,1}, {0, -1}};
        for (int[] d: dirs) {
            int newr = i + d[0];
            int newc = j + d[1];
            if (newr >= 0 && newr < board.length && newc >= 0 && newc < board[0].length && visited[newr][newc] == 0) {
                if (dfs(board, newr, newc, word, idx+1, visited)) {return true;}
            }
        }
        // backtracking !!!
        visited[i][j] = 0;
        return false;
    }
}



// Review
/*Initial thought
starting with each cell in the board, do dfs and since each cell can only be used once, we need a visited set for each dfs for each cell and we need to do backtracking. 
For dfs, the we compare the cell char with the current index in word, if they match, we move on to the next layer of dfs with the cell's neighbors and index+1 in word.
time O(mn * 3^min(L,mn)) 
Considering using the board itself to label as visited and use backtrack to change the label back. So the space is the length of the recursion, which is O(L)

Note:
[Wrong idea: We can also keep a map recording results for a cell and the index in the string,  so that we don't do repetitive dfs work.]
// https://leetcode.com/problems/word-search/discuss/459937/Why-memorization-dees-not-work-here
To put it in simple words, (x, y, index) is not exactly the state of the system.(x, y, index, visited) is the correct state.
*/
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, word, 0)) {return true;}
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, int i, int j, String word, int si) {
        // base case
        if (si >= word.length()) {return true;}
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(si)){return false;}
        boolean res = false;
        if (word.charAt(si) == board[i][j]) {
            int[][] dirs = new int[][]{{-1, 0},{0,1},{0,-1},{1, 0}};
            board[i][j] = '#';
            for (int[] dir: dirs) {
                res = dfs(board, i + dir[0], j + dir[1], word, si+1);
                if (res) {break;}
            }
            board[i][j] = word.charAt(si);
        }
        return res;
    }
}




// Review 23/1/20
/*Thoughts
DFS + backtracking with visited label
time O(m * n * 3^L)

space is O(L) len of recursion calls if we use board itself as a visited label.
 */
class Solution {
    public boolean exist(char[][] board, String word) {
      int m = board.length;
      int n = board[0].length;
      if (m * n < word.length()) {return false;}
      int[][] visited = new int[m][n];
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (existHelper(board, i, j, word, 0, visited)) {return true;}
        }
      }
      return false;
    }

    private boolean existHelper(char[][] board, int i , int j, String word, int idxWord, int[][] visited) {
      if (idxWord >= word.length()) {return true;}
      if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j] == 1 ||board[i][j] != word.charAt(idxWord)) {return false;}
      visited[i][j] = 1;
      int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
      for (int[] d: dirs) {
        int newi = i + d[0];
        int newj = j + d[1];
        if (existHelper(board, newi, newj, word, idxWord + 1, visited)) {
          return true;
        }
      }
      visited[i][j] = 0;
      return false;
    }
}


// For the pruning mentioned in followup
/* From discussion:
In pruning you just have to stop the search when you have reached an index whose value does not matches with the original word. For example you have the word "HELLO" and in the search you have got "HELP_" so there is no point on generating the last word because you have already got a index where there is a mismatch. I guess most of the people have implemented this in the back of their mind without even noticing it.

*/




