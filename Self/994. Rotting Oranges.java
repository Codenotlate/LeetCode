/*Initial Thought
use BFS starting with putting all rotted cell into the queue at the beginning. We will have a visited cell set with all fresh cells first, and remove it along the way of BFS. If in the end there's cell left in visited set, return -1.

time O(mn) space O(mn)
*/
class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // convert [i][j] to [i * n + j]
        Set<Integer> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        // first pass to put all 2 into queue, and all 1 into visited set
        for (int i =0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i,j});
                } else if (grid[i][j] == 1) {
                    visited.add(i * n + j);
                }
            }
        }
        // special case when there's no 2 and no 1
        if(visited.size() == 0) {return 0;}
        
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        // start BFS
        int level = 0;
        while (!queue.isEmpty()) {
            if(visited.size() == 0) {return level;}
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                for (int[] d: dirs) {
                    int newi = cur[0] + d[0];
                    int newj = cur[1] + d[1];
                    if (newi >= 0 && newi < m && newj >= 0 && newj < n && visited.contains(newi * n + newj)) {
                        queue.add(new int[]{newi, newj});
                        visited.remove(newi*n+newj);
                    }
                }
            }
            level++;
        }
        
        return -1;
    }
}


//https://leetcode.com/problems/rotting-oranges/discuss/238681/Java-Clean-BFS-Solution-with-comments
// this solution changed all 1 to 2 after it's visited, thus no need for our visited set. But similar idea