//phase2: exactly same as 51
class Solution {
    public int totalNQueens(int n) {
        int[] res = new int[1];
        int[] usedCol = new int[n];
        int[] usedDiag = new int[2 * n - 1];
        int[] usedAntiDiag = new int[2 * n - 1];
        dfs(0, n, usedCol, usedDiag, usedAntiDiag, res);
        return res[0];
    }
    
    private void dfs(int r, int n, int[] usedCol, int[] usedDiag, int[] usedAntiDiag, int[] res) {
        if (r == n) {
            res[0] += 1;
            return;
        }
        
        for (int c = 0; c < n; c++) {
            if (isValid(r, c, usedCol, usedDiag, usedAntiDiag)) {               
                labelUsed(usedCol, usedDiag, usedAntiDiag, r, c, 1);
                dfs(r + 1, n, usedCol, usedDiag, usedAntiDiag, res);
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
