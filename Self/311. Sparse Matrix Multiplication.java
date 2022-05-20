/*Thought
M0: straight forward way, init a int[m][n] res, then for each cell (i,j) in res, calculate result using row[i] in mat1 and col[j] in mat2.  Time O(m*n*k) space O(1)
M1: given it's sparse matrix, we can first process mat1 and mat2 into two list of maps. for mat1, each map in the list represents one row, and key value pair is <colnum, nonzero num>. For mat2, each map represents one col, and key value pair is <rownum, nonzero num>.
time O(m*n+n*k + m*n*ave # of nonzero num)
*/
class Solution {
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int m = mat1.length;
        int n = mat2[0].length;
        int[][] res = new int[m][n];
        List<Map<Integer, Integer>> mat11= convertMat1(mat1);
        List<Map<Integer, Integer>> mat22 = convertMat2(mat2);
        for (int i = 0; i < m; i++) {
            Map<Integer,Integer> rowmap = mat11.get(i);
            for (int j = 0; j < n; j++) {
                Map<Integer, Integer> colmap = mat22.get(j);
                for (Map.Entry<Integer,Integer> entry: rowmap.entrySet()) {
                    int idx = entry.getKey();
                    int val = entry.getValue();
                    if (colmap.containsKey(idx)) {
                        res[i][j] += val * colmap.get(idx);
                    }
                }
            }
            
        }
        return res;
    }
    
    private List<Map<Integer, Integer>> convertMat1(int[][] mat1) {
        List<Map<Integer, Integer>> resmap = new ArrayList<>();
        for (int i = 0; i < mat1.length; i++) {
            resmap.add(new HashMap<Integer,Integer>());
            for (int j = 0; j < mat1[0].length; j++) {
                if(mat1[i][j] != 0) {resmap.get(i).put(j, mat1[i][j]);}
            }
        }
        return resmap;
    }
    
    
    private List<Map<Integer, Integer>> convertMat2(int[][] mat2) {
        List<Map<Integer, Integer>> resmap = new ArrayList<>();
        for (int j = 0; j < mat2[0].length; j++) {
            resmap.add(new HashMap<Integer,Integer>());
            for (int i = 0; i < mat2.length; i++) {
                if(mat2[i][j] != 0) {resmap.get(j).put(i, mat2[i][j]);}
            }
        }
        return resmap;
    }
    
}


// check solution page with other ways 'Yale Format' with same time and also followup discussion.























