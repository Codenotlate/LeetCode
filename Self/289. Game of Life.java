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




// Review
/*Initial thought
require inplace and change simultaneously, think about using specific label to represent the old status as well as the new status after change.
There are in total 4 cases: 0 to 0 using 0, 1 to 0 using 3, 0 to 1 using 2, 1 to 1 using 1.
time O(mn) space O(1)
*/
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m;i++) {
            for (int j = 0; j < n; j++) {
                int liveNum = getLiveNum(board, i, j);
                if (board[i][j] % 2 == 0) {
                    if(liveNum == 3) {board[i][j] = 2;}
                } else {
                    if(liveNum < 2 || liveNum > 3) {board[i][j] = 3;}
                }               
            }
        }
        //second loop: change 2 to 1 , 3 to 0.
        for (int i = 0; i < m;i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j] > 1) {
                    board[i][j] = board[i][j] == 2 ? 1 : 0;
                }               
            }
        }
    }
    
    
    private int getLiveNum(int[][] mat, int i, int j) {
        int[][] dirs = new int[][]{{-1, 0},{1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        int count = 0;
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi >= 0 && newi < mat.length && newj >= 0 && newj < mat[0].length && mat[newi][newj] % 2 != 0) {count++;}
        }
        return count;
    }
}



/*
Follow up 2 : Infinite Board
So far we've only addressed one of the follow-up questions for this problem statement. We saw how to perform the simulation according to the four rules in-place i.e. without using any additional memory. The problem statement also mentions another follow-up statement which is a bit open ended. We will look at two possible solutions to address it. Essentially, the second follow-up asks us to address the scalability aspect of the problem. What would happen if the board is infinitely large? Can we still use the same solution that we saw earlier or is there something else we will have to do different? If the board becomes infinitely large, there are multiple problems our current solution would run into:

It would be computationally impossible to iterate a matrix that large.
It would not be possible to store that big a matrix entirely in memory. We have huge memory capacities these days i.e. of the order of hundreds of GBs. However, it still wouldn't be enough to store such a large matrix in memory.
We would be wasting a lot of space if such a huge board only has a few live cells and the rest of them are all dead. In such a case, we have an extremely sparse matrix and it wouldn't make sense to save the board as a "matrix".
Such open ended problems are better suited to design discussions during programming interviews and it's a good habit to take into consideration the scalability aspect of the problem since your interviewer might be interested in talking about such problems. The discussion section already does a great job at addressing this specific portion of the problem. We will briefly go over two different solutions that have been provided in the discussion sections, as they broadly cover two main scenarios of this problem.

One aspect of the problem is addressed by a great solution provided by Stefan Pochmann. So as mentioned before, it's quite possible that we have a gigantic matrix with a very few live cells. In that case it would be stupidity to save the entire board as is.

If we have an extremely sparse matrix, it would make much more sense to actually save the location of only the live cells and then apply the 4 rules accordingly using only these live cells.

Let's look at the sample code provided by Stefan for handling this aspect of the problem.

(Java sample code in above row 108)


Essentially, we obtain only the live cells from the entire board and then apply the different rules using only the live cells and finally we update the board in-place. The only problem with this solution would be when the entire board cannot fit into memory. If that is indeed the case, then we would have to approach this problem in a different way. For that scenario, we assume that the contents of the matrix are stored in a file, one row at a time.

In order for us to update a particular cell, we only have to look at its 8 neighbors which essentially lie in the row above and below it. So, for updating the cells of a row, we just need the row above and the row below. Thus, we read one row at a time from the file and at max we will have 3 rows in memory. We will keep discarding rows that are processed and then we will keep reading new rows from the file, one at a time.

@beagle's solution revolves around this idea and you can refer to the code in the discussion section for the same. It's important to note that there is no single solution for solving this problem. Everybody might have a different viewpoint for addressing the scalability aspect of the problem and these two solutions just address the most basic problems with handling matrix based problems at scale.
*/




// 20240808
class Solution {
    public void gameOfLife(int[][] board) {
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int count = getLiveNum(board, i, j);
                if (board[i][j]%2==1 && count != 2 && count != 3) {board[i][j] = 3;}
                else if (board[i][j]%2== 0 && count==3) {board[i][j] =2;}
            }
        }
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2 || board[i][j] == 3) {
                    board[i][j] = 1-board[i][j] % 2;
                }           
            }
        }
    }

    private int getLiveNum(int[][] mat, int i, int j) {
        int[][] dirs = new int[][]{{-1, 0},{1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        int count = 0;
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi >= 0 && newi < mat.length && newj >= 0 && newj < mat[0].length && mat[newi][newj] % 2 != 0) {count++;}
        }
        return count;
    }
}

