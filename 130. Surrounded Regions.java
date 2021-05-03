class Solution {
	// method1: Union Find
	// time O(n^2), space O(n^2)
	// convert 2d into 1d and union those on the borders to special num -1
    public void solve(char[][] board) {
    	// special case
    	if (board.length == 0) {return;}
        int m = board.length;
        int n = board[0].length;
        int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; 
        UnionFind uf = new UnionFind(m * n + 1); // +1 for special node

        // first pass of  board to union neighbors and union border 'O' to special node
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (board[i][j] == 'O') {
        			// if on border, connect to special one
        			if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
        				uf.union(i * n + j, m * n);
        			}

        			// connect with valid neighbor 'O'
        			for (int[] d: directions) {
        				int nextX = i + d[0];
        				int nextY = j + d[1];
        				if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && board[nextX][nextY] == 'O') {
        					uf.union(i * n + j, nextX * n + nextY);
        				}
        			}
        		}
        	}
        }


        // second pass of board, flip 'O's that are not connected to border(i.e. -1)
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (board[i][j] == 'O' && !uf.connected(i * n + j, m * n)) {
        			board[i][j] = 'X';
        		}
        	}
        }


    }




    private class UnionFind {
		private int count = 0;
		private int[] rank, parent;
		
		public UnionFind(int n) {
			count = n;
			rank = new int[n];
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
		}

		public int find(int p) {
			while (parent[p] != p) { // path compression, making find O(1)
				parent[p] = parent[parent[p]];
				p = parent[p];
			}
			return p;
		}

		public void union(int p, int q) {
			int rootP = find(p);
			int rootQ = find(q);
			if (rootQ == rootP) return;

			if (rank[rootP] > rank[rootQ]) {
				parent[rootQ] = rootP;
			} else if (rank[rootP] < rank[rootQ]) {
				parent[rootP] = rootQ;
			} else {
				parent[rootP] = rootQ;
				rank[rootQ]++;
			}
			count--;
		}

		public int count() {
			return count;
		}

		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}
	}
}


















// method2: DFS only on borders
// time O(n^2), space O(n^2)
// for those 'O's that can be dfs visited from border, label them with a special char
// indicating they are connected to borders.
// in the second scan, change special char to 'O' and change 'O' to 'X'
class Solution {
    public void solve(char[][] board) {
        // special case
        if (board.length == 0) {return;}
        int m = board.length;
        int n = board[0].length;
        // DFS with 4 borders
        for (int i = 0; i < m; i++) {
        	dfs(board, i, 0);
        	dfs(board, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
        	dfs(board, 0, j);
        	dfs(board, m - 1, j);
        }
        // after above 4 dfs, all the "O"s that connected to border are now labeled as 'B'
        // second pass and change the value based on its original value as 'B' or as 'O'
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (board[i][j] == 'O') {
        			board[i][j] = 'X';
        		} else if (board[i][j] == 'B') {
        			board[i][j] = 'O';
        		}
        	}
        }
    }

    private void dfs(char[][] board, int r, int c) {
    	if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != 'O') {
    		return;
    	}
    	board[r][c] = 'B';
    	int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; 
    	for (int[] d: directions) {
    		dfs(board, r + d[0], c + d[1]);
    	}
    }
}






















