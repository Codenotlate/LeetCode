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



// Phase3 self
class Solution {
    // the next stage is decided by simultaneously change, thus pretty straight forward, check each cell in the board and check its 8 neighbors to decide its status in the next stage.
    // Since the changes are simultaneously, meaning the next stage of neighbor cells should not impact the decision of the next stage of current cell. 
    // Thus there's two ways. One naive way is to use external space to store the next stage result for each cell and then copy back to board in the end. This needs O(n) space.
    // another way is to use other labels in the board directly. But that label should tell what's the original stage of that cell, and what's the next stage. Thus we can use 0 for 0 to 0, 1 for 1 to 1, 2 for 0 to 1,3 for 1 to 0
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = nextStage(board, i, j);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2) {board[i][j] = 1;}
                if (board[i][j] == 3) {board[i][j] = 0;}
            }
        }       
    }
    
    
    private int nextStage(int[][] board, int i, int j) {
        int[][] dirs = new int[][]{{-1, 1},{-1, 0},{-1, -1},{0, 1},{0, -1},{1, 1},{1, 0},{1, -1}};
        int m = board.length;
        int n = board[0].length;
        int count1 = 0;
        int count0 = 0;
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi < 0 || newi >= m || newj < 0 || newj >= n) {continue;}
            if (board[newi][newj] % 2 == 1) {// label 1 or 3
                count1++;
            } else {
                count0++;
            }           
        }
        
        // apply 4 rules
        if (board[i][j] == 1) {
            if (count1 < 2 || count1 >3) {return 3;}
            return 1;
        } else {
            if (count1 == 3) {return 2;}
            return 0;
        }
        
    }
}

// see followup2 (infinite board)
// https://leetcode.com/problems/game-of-life/discuss/73217/Infinite-board-solution
 public void gameOfLife(int[][] board) {
        Set<Pair<Integer, Integer>> liveSet = new HashSet<>();
        int rows = board.length, cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 1) liveSet.add(new Pair<>(i, j));
            }
        }
        liveSet = gameOfLife(liveSet);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = liveSet.contains(new Pair<>(i, j)) ? 1 : 0;
            }
        }
    }

    private Set<Pair<Integer, Integer>> gameOfLife(Set<Pair<Integer, Integer>> live) {
        Map<Pair<Integer, Integer>, Integer> neighbours = new HashMap<>();
        for (Pair<Integer, Integer> cell : live) {
            int r = cell.getKey(), c = cell.getValue();
            for (int i = r - 1; i < r + 2; i++) {
                for (int j = c - 1; j < c + 2; j++) {
                    if (i == r && j == c) continue;
                    Pair<Integer, Integer> pair = new Pair<>(i, j);
                    if (neighbours.containsKey(pair)) neighbours.put(pair, neighbours.get(pair) + 1);
                    else neighbours.put(pair, 1);
                }
            }
        }
        Set<Pair<Integer, Integer>> newLiveSet = new HashSet<>();
        neighbours.forEach((pair, val) -> {
            if (val == 3 || val == 2 && live.contains(pair)) newLiveSet.add(pair);
        });
        return newLiveSet;
    }









