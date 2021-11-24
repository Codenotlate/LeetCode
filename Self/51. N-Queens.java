class Solution {
	// time O(n * n!) space O(n)
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<>();
        List<String> curList = new LinkedList<>();
        int[] usedCol = new int[n];
        int[] usedDiag = new int[2 * n - 1];
        int[] usedAntiDiag = new int[2 * n - 1];
        dfs(res, curList, 0, n, usedCol, usedDiag, usedAntiDiag);
        return res;
    }

    private void dfs(List<List<String>> res, List<String> curList, int row, int n, int[] usedCol, int[] usedDiag, int[] usedAntiDiag) {
    	// end case
    	if (row == n) {
    		res.add(new LinkedList<>(curList));
    		return;
    	}

    	// try each column in this row, if valid, dfs next row
    	for (int col = 0; col < n; col++) {
    		if (isValid(usedCol, usedDiag, usedAntiDiag, row, col, n)) {
    			// build current row's char Array, then add as String into curList
    			char[] curRow = new char[n];
    			Arrays.fill(curRow, '.');
    			curRow[col] = 'Q';
    			curList.add(new String(curRow));

    			// update used arrays
    			usedCol[col] = 1;
    			// for pos in same diagonal, r - c = constant, range from -(n-1) to (n - 1)
    			// thus we add (n - 1) to make is range from 0 to 2n - 2
    			usedDiag[n - 1 + row - col] = 1;
    			// for usedAntiDiag, r + c = constant, range from 0 to 2n - 2
    			usedAntiDiag[row + col] = 1;

    			// dfs to next row
    			dfs(res, curList, row + 1, n, usedCol, usedDiag, usedAntiDiag);

    			// backtracking
    			curList.remove(curList.size() - 1);
    			usedCol[col] = 0;
    			usedDiag[n - 1 + row - col] = 0;
    			usedAntiDiag[row + col] = 0;

    		}
    	}
    }

    private boolean isValid(int[] usedCol, int[] usedDiag, int[] usedAntiDiag, int row, int col, int n) {
    	if (usedCol[col] == 0 && usedDiag[n - 1 + row - col] == 0 && usedAntiDiag[row + col] == 0) {
    		return true;
    	}
    	return false;
    }



}

// some good explanations
//https://leetcode.com/problems/n-queens/discuss/19932/Clean-back-tracking-Java-solution-with-simple-explaination




// phase 2: slower version with N^2 space
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<>();
        int[][] used = new int[n][n];
        char[][] board = new char[n][n];
        for (char[] r: board) {
            Arrays.fill(r, '.');
        }
        dfs(board, 0, n, used, res);
        return res;
    }
    
    private void dfs(char[][] board, int r, int n, int[][] used, List<List<String>> res) {
        if (r == n) {
            List<String> cur = new LinkedList<>();
            for (char[] row: board) {
                cur.add(new String(row)); 
            }
            res.add(cur);
            return;
        }
        
        for (int c = 0; c < n; c++) {
            if (used[r][c] != 1) {
                board[r][c] = 'Q';
                int[][] temp = new int[used.length][];
                for (int k = 0; k < used.length; k++) {
                    temp[k] = used[k].clone();
                }
                labelUsed(used, n, r, c, 1);
                dfs(board, r + 1, n, used, res);
                board[r][c] = '.';
                used = temp;
            }
        }
    }
    
    private void labelUsed(int[][] used, int n, int r, int c, int label) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == r || j == c || i + j == r + c || i - j == r - c) {
                    used[i][j] = label;
                }
            }
        }
    }
}


// phase2: similar to method1 version, with O(n) space

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<>();
        int[] usedCol = new int[n];
        int[] usedDiag = new int[2 * n - 1];
        int[] usedAntiDiag = new int[2 * n - 1];
        char[][] board = new char[n][n];
        for (char[] r: board) {
            Arrays.fill(r, '.');
        }
        dfs(board, 0, n, usedCol, usedDiag, usedAntiDiag, res);
        return res;
    }
    
    private void dfs(char[][] board, int r, int n, int[] usedCol, int[] usedDiag, int[] usedAntiDiag, List<List<String>> res) {
        if (r == n) {
            List<String> cur = new LinkedList<>();
            for (char[] row: board) {
                cur.add(new String(row)); 
            }
            res.add(cur);
            return;
        }
        
        for (int c = 0; c < n; c++) {
            if (isValid(r, c, usedCol, usedDiag, usedAntiDiag)) {
                board[r][c] = 'Q';                
                labelUsed(usedCol, usedDiag, usedAntiDiag, r, c, 1);
                dfs(board, r + 1, n, usedCol, usedDiag, usedAntiDiag, res);
                board[r][c] = '.';
                labelUsed(usedCol, usedDiag, usedAntiDiag, r, c, 0);
            }
        }
    }
    
    private void labelUsed(int[] usedCol, int[] usedDiag, int[] usedAntiDiag, int r, int c, int label) {
        int n = usedCol.length;
        usedCol[c] = label;
        usedDiag[n - 1 + r - c] = label;
        usedAntiDiag[r + c] = label;
    }
    
    private boolean isValid(int r, int c, int[] usedCol, int[] usedDiag, int[] usedAntiDiag) {
        int n = usedCol.length;
        if (usedCol[c] != 1 && usedDiag[n - 1 + r - c] != 1 && usedAntiDiag[r + c] != 1) {return true;}
        return false;
    }
}



// Phase3 self
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<>();
        List<String> curList = new LinkedList<>();
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diags = new HashSet<>();
        Set<Integer> revdiags = new HashSet<>();
        dfs(n, 0, cols, diags, revdiags, curList, res);
        return res;
    }
    
    private void dfs(int n, int i, Set<Integer> cols, Set<Integer> diags, Set<Integer> revdiags, List<String> curList, List<List<String>> res) {
        if (i == n) {res.add(new LinkedList(curList)); return;}
        for (int j = 0; j < n; j++) {
            if (cols.contains(j) || diags.contains(i + j) || revdiags.contains(i - j)) {continue;}
            cols.add(j);
            diags.add(i+ j);
            revdiags.add(i - j);
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[j] = 'Q';
            curList.add(new String(row));
            dfs(n, i+ 1, cols, diags, revdiags, curList, res);
            curList.remove(curList.size() - 1);
            cols.remove(j);
            diags.remove(i + j);
            revdiags.remove(i- j);
        }
    }
}