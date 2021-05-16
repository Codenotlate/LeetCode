// Phase 3 self: Time O(m * n), space O(1)
/* use labels other than 0 or 1 to indicate the previous value before update
	0 for 0 -> 0
	1 for 1 -> 1
	2 for 0 -> 1
	3 for 1 -> 0


see solution for follow-up questions related to scalability.
*/

class Solution {
    public void gameOfLife(int[][] board) {
    	int m = board.length;
    	int n = board[0].length;
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		int neighborCount = getNeighborCount(board, m, n, i, j);

        		if ((neighborCount < 2 || neighborCount > 3) && board[i][j] == 1) {
        			board[i][j] = 3; // 1 -> 0
        		} else if (neighborCount == 3 && board[i][j] == 0) {
        			board[i][j] = 2; // 0 -> 1
        		}
        	}
        }

        // adjust 2 to 1 and 3 to 0
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		// don't forget this line!!!
        		if (board[i][j] != 1 && board[i][j] != 0) {
        			board[i][j] = 1 - board[i][j] % 2;
        		}    		
        	}
        }
    }


    private int getNeighborCount(int[][] graph, int m, int n, int r, int c) {
    	int count = 0;
    	int[][] dirs = new int[][]{{-1, 1},{-1, 0},{-1, -1},{0, 1},{0, -1},{1, 1},{1, 0},{1, -1}};
    	for (int[] d: dirs) {
    		if (r + d[0] < m && r + d[0] >= 0 && c + d[1] < n && c + d[1] >= 0) {
    			if (graph[r + d[0]][c + d[1]] % 2 != 0) {
    				count++;
    			}
    		}
    	}
    	return count;
    }
}












